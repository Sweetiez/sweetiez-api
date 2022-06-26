package fr.sweetiez.api.infrastructure.repository.events.space;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "space")
public class SpaceEntity {

    @Id
    private final UUID id;

    private final String address;

    private final String city;

    private final String zipCode;

    public SpaceEntity() {
        this.id = null;
        this.address = null;
        this.city = null;
        this.zipCode = null;
    }

    public SpaceEntity(UUID id, String address, String city, String zipCode) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
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
}
