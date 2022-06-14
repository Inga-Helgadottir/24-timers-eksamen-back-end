package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Locations.deleteAllRows", query = "DELETE from Location l")
@Table(name = "Locations")
public class Location {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "address")
    private String address;
    @NotNull
    @Column(name = "city")
    private String city;
    @NotNull
    @Column(name = "theCondition")
    private String condition;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Match> matches = new ArrayList<>();

    public Location() {
    }

    public Location(String address, String city, String condition) {
        this.address = address;
        this.city = city;
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void addToMatches(Match match) {
        this.matches.add(match);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
