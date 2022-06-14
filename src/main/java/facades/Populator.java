/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.Match;
import entities.Player;
import entities.Role;
import entities.User;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populateUser(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");

        if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);

        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);

        em.getTransaction().commit();
    }

    public static void populatePlayersMatches() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Player p1 = new Player("Karen Miller", 12345678, "km@email.com", "status");
        Player p2 = new Player("Molly Hansen", 45612389, "mh@email.com", "status");
        Player p3 = new Player("Missy Parker", 78945612, "mp@email.com", "status");
        Match m = new Match("team1", "Annie Clark", "Chess", "true");
        Match m2 = new Match("team2", "Bonnie Mitchel", "Basketball", "true");

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
    }

    public static void main(String[] args) {
//        populateUser();
        populatePlayersMatches();
    }
}
