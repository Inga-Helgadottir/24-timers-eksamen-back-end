package dtos;

import entities.Location;
import entities.Match;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocationDTO {
    private int id;
    private String address;
    private String city;
    private String condition;

    public LocationDTO(String address, String city, String condition) {
        this.address = address;
        this.city = city;
        this.condition = condition;
    }

    public static List<LocationDTO> getDtos(List<Location> location){
        List<LocationDTO> ldtos = new ArrayList<>();
        location.forEach(l->ldtos.add(new LocationDTO(l)));
        return ldtos;
    }

    public LocationDTO(Location l){
        if(l != null){
            this.id = l.getId();
            this.address = l.getAddress();
            this.city = l.getCity();
            this.condition = l.getCondition();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationDTO that = (LocationDTO) o;
        return id == that.id && address.equals(that.address) && city.equals(that.city) && condition.equals(that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, city, condition);
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
