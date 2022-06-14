package facades;

import dtos.MatchDTO;
import entities.Match;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class MatchFacade {

    private static EntityManagerFactory emf;
    private static MatchFacade instance;

    public MatchFacade() {
    }

    public static MatchFacade getMatchFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MatchFacade();
        }
        return instance;
    }

    public List<MatchDTO> seeAllMatches(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m", Match.class);
            List<Match> matches = query.getResultList();
            List<MatchDTO> mdtos = MatchDTO.getDtos(matches);
            return mdtos;
        }finally {
            em.close();
        }
    }
}
