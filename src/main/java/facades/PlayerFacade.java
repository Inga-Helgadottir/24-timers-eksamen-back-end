//package facades;
//
//import entities.Player;
//import entities.User;
//import security.errorhandling.AuthenticationException;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//
//public class PlayerFacade {
//
//    private static EntityManagerFactory emf;
//    private static PlayerFacade instance;
//
//    private PlayerFacade() {
//    }
//
//    /**
//     *
//     * @param _emf
//     * @return the instance of this facade.
//     */
//    public static PlayerFacade getPlayerFacade(EntityManagerFactory _emf) {
//        if (instance == null) {
//            emf = _emf;
//            instance = new PlayerFacade();
//        }
//        return instance;
//    }
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
//
//}
