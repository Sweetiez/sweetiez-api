package fr.sweetiez.api.infrastructure.repository.events.space;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "space")
public class SpaceEntity {

    @Id
    private final UUID id;

    private final String address;

    private final String city;

    private final String zipCode;

    private final Integer places;

    public SpaceEntity() {
        this.id = null;
        this.address = null;
        this.city = null;
        this.zipCode = null;
        this.places = null;
    }

    public SpaceEntity(UUID id, String address, String city, String zipCode, Integer places) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.places = places;
    }

    public java.util.UUID id() {
        return id;
    }

    public String address() {
        return address;
    }

    public String city() {
        return city;
    }

    public String zipCode() {
        return zipCode;
    }
    public Integer places() {
        return places;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceEntity that = (SpaceEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(zipCode, that.zipCode) && Objects.equals(places, that.places);
    }

    public int hashCode() {
        return Objects.hash(id, address, city, zipCode, places);
    }

    public String toString() {
        return "SpaceEntity{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", places=" + places +
                '}';
    }
}
