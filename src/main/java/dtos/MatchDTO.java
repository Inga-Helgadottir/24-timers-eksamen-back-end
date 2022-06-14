package dtos;

import entities.Match;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchDTO {
    private int id;
    private String opponentTeam;
    private String judge;
    private String type;
    private boolean inDoors;
    private List<PlayerDTO> playerDTOS = new ArrayList<>();

    public MatchDTO(String opponentTeam, String judge, String type, boolean inDoors) {
        this.opponentTeam = opponentTeam;
        this.judge = judge;
        this.type = type;
        this.inDoors = inDoors;
    }

    public static List<MatchDTO> getDtos(List<Match> matches){
        List<MatchDTO> mdtos = new ArrayList<>();
        matches.forEach(m->mdtos.add(new MatchDTO(m)));
        return mdtos;
    }

    public MatchDTO(Match m){
        if(m != null){
            this.id = m.getId();
            this.opponentTeam = m.getOpponentTeam();
            this.judge = m.getJudge();
            this.inDoors = m.getInDoors();
            this.type = m.getType();
            this.playerDTOS = PlayerDTO.getDtos(m.getPlayers());
        }
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

    public boolean isInDoors() {
        return inDoors;
    }

    public void setInDoors(boolean inDoors) {
        this.inDoors = inDoors;
    }

    public List<PlayerDTO> getPlayerDTOS() {
        return playerDTOS;
    }

    public void setPlayerDTOS(List<PlayerDTO> playerDTOS) {
        this.playerDTOS = playerDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchDTO matchDTO = (MatchDTO) o;
        return id == matchDTO.id && inDoors == matchDTO.inDoors && opponentTeam.equals(matchDTO.opponentTeam) && judge.equals(matchDTO.judge) && type.equals(matchDTO.type) && Objects.equals(playerDTOS, matchDTO.playerDTOS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opponentTeam, judge, type, inDoors, playerDTOS);
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "id=" + id +
                ", opponentTeam='" + opponentTeam + '\'' +
                ", judge='" + judge + '\'' +
                ", type='" + type + '\'' +
                ", inDoors=" + inDoors +
                ", playerDTOS=" + playerDTOS +
                '}';
    }
}
