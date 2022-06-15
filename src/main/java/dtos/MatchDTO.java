package dtos;

import entities.Location;
import entities.Match;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchDTO {
    private int id;
    private String opponentTeam;
    private String judge;
    private String type;
    private String inDoors;
    private List<PlayerDTO> playerDTOS = new ArrayList<>();
//    private Location location;

    public MatchDTO(String opponentTeam, String judge, String type, String inDoors) {
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
//            this.location = m.getLocation();
        }
    }

    public String getInDoors() {
        return inDoors;
    }

//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }

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

    public String isInDoors() {
        return inDoors;
    }

    public void setInDoors(String inDoors) {
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
        return id == matchDTO.id && opponentTeam.equals(matchDTO.opponentTeam) && judge.equals(matchDTO.judge) && type.equals(matchDTO.type) && inDoors.equals(matchDTO.inDoors) && playerDTOS.equals(matchDTO.playerDTOS);// && location.equals(matchDTO.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opponentTeam, judge, type, inDoors, playerDTOS);//, location);
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "id=" + id +
                ", opponentTeam='" + opponentTeam + '\'' +
                ", judge='" + judge + '\'' +
                ", type='" + type + '\'' +
                ", inDoors='" + inDoors + '\'' +
                ", playerDTOS=" + playerDTOS +
//                ", location=" + location +
                '}';
    }
}
