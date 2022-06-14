package entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Player.deleteAllRows", query = "DELETE from Player p")
@Table(name = "Player")
public class Player {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;
//    @NotNull
//    @Column(name = "password")
//    private String password;
    @NotNull
    @Column(name = "phone")
    private long phone;
    @NotNull
    @Column(name = "email")
    private String email;
    @NotNull
    @Column(name = "status")
    private String status;
    @JoinTable(name = "players_matches", joinColumns = {
            @JoinColumn(name = "players", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "matches", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Match> matches = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "user_name")
    private User user;

    public Player() {
    }

    public Player(String name, long phone, String email, String status){//, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
//        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
//    public boolean verifyPassword(String pw){
//        return BCrypt.checkpw(pw, password);
//    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void addToMatches(Match match) {
        this.matches.add(match);
        match.addToPlayers(this);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", matches=" + matches +
                '}';
    }
}
