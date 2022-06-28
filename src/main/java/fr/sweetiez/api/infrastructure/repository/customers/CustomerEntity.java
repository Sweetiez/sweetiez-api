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

    @Column()
    private final String phone;

    @OneToOne(fetch = FetchType.EAGER)
    private final AccountEntity account;

    @Column()
    private final Integer loyaltyPoints;

    public CustomerEntity() {
        id = null;
        firstName = null;
        lastName = null;
        email = null;
        phone = null;
        account = null;
        loyaltyPoints = null;
    }

    public CustomerEntity(UUID id, String firstName, String lastName,
                          String email, String phone, AccountEntity account,
                          Integer loyaltyPoints) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.account = account;
        this.loyaltyPoints = loyaltyPoints;
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
    public String getPhone() {
        return phone;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }
}
