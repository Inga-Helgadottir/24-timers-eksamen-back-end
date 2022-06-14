package rest;

import com.google.gson.*;
import dtos.PlayerDTO;
import entities.Location;
import entities.Match;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import facades.MatchFacade;
import facades.PlayerFacade;
import facades.Populator;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final MatchFacade mf = MatchFacade.getMatchFacade(EMF);
    private static final PlayerFacade pf = PlayerFacade.getPlayerFacade(EMF);
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("seeAllMatches")
    public Response seeAllMatchesEndpoint(){
        return Response.ok().entity(GSON.toJson(mf.seeAllMatches())).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("deletePlayer/{id}")
    @RolesAllowed("admin")
    public Response deletePlayer(@PathParam("id") int id)throws EntityNotFoundException {
        PlayerDTO deleted = pf.deletePlayer(id);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("changeMatch")
    @RolesAllowed("admin")
    public Response changeMatch(String jsonContext) {
        System.out.println(jsonContext);
        Match c = GSON.fromJson(jsonContext, Match.class);
        return Response.ok()
                .entity(GSON.toJson(mf.updateMatches(c)))
                .build();

    }

    @GET
    @Path("populateMyDBWithUsersAndACocktail")
    @Produces(MediaType.APPLICATION_JSON)
    public void populate() {
        String[] args = new String[0];
        Populator.main(args);
    }
}