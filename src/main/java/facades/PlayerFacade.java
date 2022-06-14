package facades;

import dtos.PlayerDTO;
import entities.Player;
import entities.User;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PlayerFacade {

    private static EntityManagerFactory emf;
    private static PlayerFacade instance;

    private PlayerFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static PlayerFacade getPlayerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PlayerFacade();
        }
        return instance;
    }

    public PlayerDTO deletePlayer(int id){
        EntityManager em = emf.createEntityManager();
        try {
            Player p = em.find(Player.class, id);
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            return new PlayerDTO(p);
        }finally {
            em.close();
        }
    }
//
//    public Player getVeryfiedUser(String name, long phone, String email, String status, String password) throws AuthenticationException {
//        EntityManager em = emf.createEntityManager();
//        Player player;
//        try {
//            player = em.find(Player.class, email);
//            if (player == null || !player.verifyPassword(password)) {
//                throw new AuthenticationException("Invalid user name or password");
//            }
//        } finally {
//            em.close();
//        }
//        return player;
//    }

}
