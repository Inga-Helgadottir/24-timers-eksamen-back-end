package facades;

import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFacadeTest {

    private static EntityManagerFactory emf;
    private static PlayerFacade facade;
    private int playerSize = 3;

    public PlayerFacadeTest() {
    }

    @BeforeAll
    static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PlayerFacade.getPlayerFacade(emf);
    }

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


            Player p1 = new Player("Karen Miller", 12345678, "km@email.com", "status");//, "password1");
            Player p2 = new Player("Molly Hansen", 45612389, "mh@email.com", "status");//, "password2");
            Player p3 = new Player("Missy Parker", 78945612, "mp@email.com", "status");//, "password3");Match m = new Match("team hihi", "Annie Clark", "Chess", "true");
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

    @Test
    void deletePlayer() {
        System.out.println("delete Player test");
        facade.deletePlayer(1);
        int actual = playerSize - 1;
        int expected = 2;
        assertEquals(actual, expected);
    }
}