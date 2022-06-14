package facades;

import dtos.MatchDTO;
import entities.Match;
import entities.Player;
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
            em.createNativeQuery("DROP TABLE IF EXISTS Player").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS `Match`").executeUpdate();
            em.getTransaction().commit();

            Player p1 = new Player("Karen Miller", 12345678, "km@email.com", "status");
            Player p2 = new Player("Molly Hansen", 45612389, "mh@email.com", "status");
            Player p3 = new Player("Missy Parker", 78945612, "mp@email.com", "status");
            Match m = new Match("team hihi", "Annie Clark", "Chess", true);
            Match m2 = new Match("team hoho", "Bonnie Mitchel", "Basketball", true);

            em.getTransaction().begin();

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
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @Test
    void seeAllMatches() {
        System.out.println("seeAllMatches test");
        List<MatchDTO> matches = facade.seeAllMatches();
        int actual = matches.size();
        int expected = 2;
        assertEquals(expected, actual);
    }
}