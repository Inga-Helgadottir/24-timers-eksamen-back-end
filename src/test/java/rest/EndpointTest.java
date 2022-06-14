package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MatchDTO;
import entities.*;
import facades.MatchFacade;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class EndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Player p1, p2, p3;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }@AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            em.createNamedQuery("Player.deleteAllRows").executeUpdate();
            em.createNamedQuery("Matches.deleteAllRows").executeUpdate();
            em.createNamedQuery("Locations.deleteAllRows").executeUpdate();

            em.getTransaction().commit();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            User tester = new User("hihihi", "test");
            tester.addRole(userRole);

            p1 = new Player("Karen Miller", 12345678, "km@email.com", "status");//, "password1");
            p2 = new Player("Molly Hansen", 45612389, "mh@email.com", "status");//, "password2");
            p3 = new Player("Missy Parker", 78945612, "mp@email.com", "status");//, "password3");
            Match m = new Match("team hihi", "Annie Clark", "Chess", "true");
            Match m2 = new Match("team hoho", "Bonnie Mitchel", "Basketball", "true");
            Location l1 = new Location("some address", "some city", "condition");
            Location l2 = new Location("some other address", "some other city", "some other condition");

            em.getTransaction().begin();
            User u1 = em.find(User.class, "user");
            User u2 = em.find(User.class, "user_admin");
            User u3 = em.find(User.class, "admin");

            m.setLocation(l1);
            m2.setLocation(l2);

            p1.addToMatches(m);
            p2.addToMatches(m);
            p1.addToMatches(m2);
            p2.addToMatches(m2);
            p3.addToMatches(m2);

            p1.setUser(u1);
            p2.setUser(u3);
            p3.setUser(u2);

            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(m);
            em.persist(m2);
            em.persist(l1);
            em.persist(l2);

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(tester);
            em.persist(user);
            em.persist(admin);
            em.persist(both);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static String securityToken;

    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    public void testRestNoAuthenticationRequired() {
        given()
                .contentType("application/json")
                .when()
                .get("/info/").then()
                .statusCode(200)
                .body("msg", equalTo("Hello anonymous"));
    }

    @Test
    public void testRestForAdmin() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: admin"));
    }

    @Test
    public void testRestForUser() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: user"));
    }

    @Test
    public void testAutorizedUserCannotAccesAdminPage() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then() //Call Admin endpoint as user
                .statusCode(401);
    }

    @Test
    public void testAutorizedAdminCannotAccesUserPage() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then() //Call User endpoint as Admin
                .statusCode(401);
    }

    @Test
    public void testRestForMultiRole1() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: user_admin"));
    }

    @Test
    public void testRestForMultiRole2() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: user_admin"));
    }

    @Test
    public void userNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/info/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

    @Test
    public void adminNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/info/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

    //--------------------------------------------------------------------------
    @Test
    void seeAllMatchesEndpoint() {
        login("user_admin", "test");
        given()
                .contentType("application/json")
                .when()
                .get("info/seeAllMatches").then()
                .statusCode(200);
    }

    @Test
    void changeMatchEndpoint() {
        System.out.println("updateMatchesEndpoint test");
        login("admin", "test");
        String json = String.format("{id: 1, opponentTeam: team lala, judge: Annie Clark, type: Chess, inDoors: true playerDTOS: [id: 1, name: Missy Parker, phone: 78945612, email: mp@email.com, status: status},{ id: 2, name: Molly Hansen, phone: 45612389, email: mh@email.com, status: status},{ id: 3, name: Karen Miller, phone: 12345678, email: km@email.com, status: status}]}");
//        String json = String.format("{id: \"1\", opponentTeam: \"team lala\", judge: \"Annie Clark\", type: \"Chess\", inDoors: \"true\" playerDTOS: [id: \"1\", name: \"Missy Parker\", phone: \"78945612\", email: \"mp@email.com\", status\": \"status\"},{ id: \"2\", name: \"Molly Hansen\", phone: \"45612389\", email: \"mh@email.com\", status: \"status\"},{ id: \"3\", name: \"Karen Miller\", phone: \"12345678\", email: \"km@email.com\", status: \"status\"}]}");

        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .headers("x-access-token", securityToken)
                .and()
                .body(json)
                .when()
                .post("info/changeMatch")
                .then()
                .statusCode(200);
    }

    @Test
    void deletePlayerEndpoint(){
        System.out.println("delete player endpoint test");
        login("admin", "test");
        given()
                .contentType(ContentType.JSON)
                .headers("x-access-token", securityToken)
                .pathParam("id", p1.getId())
                .delete("/deletePlayer/{id}")
                .then()
                .statusCode(200)
                .body("id",equalTo(p1.getId()));

    }
}
