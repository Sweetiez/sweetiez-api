package fr.sweetiez.api.infrastructure.repository.customers;

import fr.sweetiez.api.infrastructure.repository.accounts.AccountEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(name = "first_name", nullable = false)
    private final String firstName;

    @Column(name = "last_name", nullable = false)
    private final String lastName;

    @Column(nullable = false)
    private final String email;

    @OneToOne(fetch = FetchType.EAGER)
    private final AccountEntity account;

    public CustomerEntity() {
        id = null;
        firstName = null;
        lastName = null;
        email = null;
        account = null;
    }

    public CustomerEntity(UUID id, String firstName, String lastName, String email, AccountEntity account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.account = account;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public AccountEntity getAccount() {
        return account;
    }
}
