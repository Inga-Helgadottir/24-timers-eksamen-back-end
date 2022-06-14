package dtos;

import entities.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerDTO {
    private int id;
    private String name;
    private long phone;
    private String email;
    private String status;

    public PlayerDTO(String name, long phone, String email, String status) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public static List<PlayerDTO> getDtos(List<Player> players){
        List<PlayerDTO> pdtos = new ArrayList<>();
        players.forEach(p->pdtos.add(new PlayerDTO(p)));
        return pdtos;
    }

    public PlayerDTO(Player p){
        if(p != null){
            this.id = p.getId();
            this.name = p.getName();
            this.phone = p.getPhone();
            this.email = p.getEmail();
            this.status = p.getStatus();
        }
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO playerDTO = (PlayerDTO) o;
        return id == playerDTO.id && phone == playerDTO.phone && name.equals(playerDTO.name) && email.equals(playerDTO.email) && status.equals(playerDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, email, status);
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
