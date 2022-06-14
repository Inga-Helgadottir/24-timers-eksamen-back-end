//package security;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.JWSSigner;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import entities.Player;
//import entities.User;
//import errorhandling.API_Exception;
//import errorhandling.GenericExceptionMapper;
//import security.errorhandling.AuthenticationException;
//import utils.EMF_Creator;
//
//import javax.persistence.EntityManagerFactory;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Path("loginAsPlayer")
//public class LoginAsPLayerEndpoint {
//
//    public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
//    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
//    public static final PlayerFacade PLAYER_FACADE = PlayerFacade.getPlayerFacade(EMF);
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response login(String jsonString) throws AuthenticationException, API_Exception {
//        String name;
//        String password;
//        long phone;
//        String email;
//        String status;
//        try {
//            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
//            name = json.get("name").getAsString();
//            password = json.get("password").getAsString();
//            phone = json.get("phone").getAsLong();
//            email = json.get("email").getAsString();
//            status = json.get("status").getAsString();
//        } catch (Exception e) {
//           throw new API_Exception("Malformed JSON Suplied",400,e);
//        }
//
//        try {
////            Player player = PLAYER_FACADE.getVeryfiedUser(name, phone, email, status, password);
//            String token = createToken(name, email, status);
//            JsonObject responseJson = new JsonObject();
//            responseJson.addProperty("name", name);
//            responseJson.addProperty("token", token);
//            return Response.ok(new Gson().toJson(responseJson)).build();
//
//        } catch (JOSEException ex) {
//            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        throw new AuthenticationException("Invalid username or password! Please try again");
//    }
//
//    private String createToken(String name, String email, String status) throws JOSEException {
//
//        StringBuilder res = new StringBuilder();
//
//        String issuer = "semesterstartcode-dat3";
//
//        JWSSigner signer = new MACSigner(SharedSecret.getSharedKey());
//        Date date = new Date();
//        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                .subject(name)
//                .claim("name", name)
//                .claim("email", email)
//                .claim("status", status)
//                .claim("issuer", issuer)
//                .issueTime(date)
//                .expirationTime(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
//                .build();
//        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
//        signedJWT.sign(signer);
//        return signedJWT.serialize();
//
//    }
//}
