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


    public MatchDTO updateMatches(Match match){
        EntityManager em = emf.createEntityManager();
        try {
            Match m = em.find(Match.class, match.getId());
            m.setOpponentTeam(match.getOpponentTeam());
            m.setInDoors(match.getInDoors());
            m.setType(match.getType());
            m.setJudge(match.getJudge());
            m.setLocation(match.getLocation());
            m.setPlayers(match.getPlayers());
            em.persist(m);
            return new MatchDTO(m);
        }finally {
            em.close();
        }
    }
}
