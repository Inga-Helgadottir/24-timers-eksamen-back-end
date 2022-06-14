package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Player", schema="24Eksamen")
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;
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

    public Player() {
    }

    public Player(String name, long phone, String email, String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

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
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", matches=" + matches +
                '}';
    }
}
