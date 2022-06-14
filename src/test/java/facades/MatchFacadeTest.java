package facades;

import dtos.MatchDTO;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchFacadeTest {

    private static EntityManagerFactory emf;
    private static MatchFacade facade;

    public MatchFacadeTest() {
    }

    @BeforeAll
    static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = MatchFacade.getMatchFacade(emf);
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

            Player p1 = new Player("Karen Miller", 12345678, "km@email.com", "status");
            Player p2 = new Player("Molly Hansen", 45612389, "mh@email.com", "status");
            Player p3 = new Player("Missy Parker", 78945612, "mp@email.com", "status");
            Match m = new Match("team hihi", "Annie Clark", "Chess", "true");
            Match m2 = new Match("team hoho", "Bonnie Mitchel", "Basketball", "true");
            Location l1 = new Location("some address", "some city", "condition");
            Location l2 = new Location("some other address", "some other city", "some other condition");

            em.getTransaction().begin();

            m.setLocation(l1);
            m2.setLocation(l2);

            p1.addToMatches(m);
            p2.addToMatches(m);
            p1.addToMatches(m2);
            p2.addToMatches(m2);
            p3.addToMatches(m2);

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
    void seeAllMatches() {
        System.out.println("seeAllMatches test");
        List<MatchDTO> matches = facade.seeAllMatches();
        for (MatchDTO m: matches) {
            System.out.println(m);
        }
        int actual = matches.size();
        int expected = 2;
        assertEquals(expected, actual);
    }
}