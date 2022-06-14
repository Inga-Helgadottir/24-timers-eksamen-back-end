/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.*;
import utils.EMF_Creator;

import java.util.Locale;

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

        Player p1 = new Player("Karen Miller", 12345678, "km@email.com", "status");//, "password1");
        Player p2 = new Player("Molly Hansen", 45612389, "mh@email.com", "status");//, "password2");
        Player p3 = new Player("Missy Parker", 78945612, "mp@email.com", "status");//, "password3");
        Match m = new Match("team1", "Annie Clark", "Chess", "true");
        Match m2 = new Match("team2", "Bonnie Mitchel", "Basketball", "true");
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

        em.getTransaction().commit();
    }

    public static void main(String[] args) {
        populateUser();
        populatePlayersMatches();
    }
}
