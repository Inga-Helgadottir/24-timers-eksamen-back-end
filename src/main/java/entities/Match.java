package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Matches.deleteAllRows", query = "DELETE from Match m")
@Table(name = "Matches")
public class Match {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "opponent_team")
    private String opponentTeam;
    @NotNull
    @Column(name = "judge")
    private String judge;
    @NotNull
    @Column(name = "type")
    private String type;
    @NotNull
    @Column(name = "in_doors")
    private String inDoors;
    @ManyToMany(mappedBy = "matches", cascade = {CascadeType.PERSIST})
    private List<Player> players = new ArrayList<>();

    public Match() {
    }

    public Match(String opponentTeam, String judge, String type, String inDoors) {
        this.opponentTeam = opponentTeam;
        this.judge = judge;
        this.type = type;
        this.inDoors = inDoors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(String opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInDoors() {
        return inDoors;
    }

    public void setInDoors(String inDoors) {
        this.inDoors = inDoors;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addToPlayers(Player player) {
        this.players.add(player);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", opponentTeam='" + opponentTeam + '\'' +
                ", judge='" + judge + '\'' +
                ", type='" + type + '\'' +
                ", inDoors=" + inDoors +
                '}';
    }
}
