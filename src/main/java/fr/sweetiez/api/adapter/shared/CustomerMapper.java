package fr.sweetiez.api.adapter.shared;

import fr.sweetiez.api.core.customers.models.Customer;
import fr.sweetiez.api.core.customers.models.CustomerId;
import fr.sweetiez.api.core.loyalty.points.models.loyatypoints.LoyaltyPoints;
import fr.sweetiez.api.core.loyalty.points.models.loyatypoints.Points;
import fr.sweetiez.api.infrastructure.repository.customers.CustomerEntity;

import java.util.Optional;
import java.util.UUID;

public class CustomerMapper {

    private final AccountMapper accountMapper;

    public CustomerMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public CustomerEntity toEntity(Customer customer) {
        var account =
                customer.account().isPresent() ? accountMapper.toEntity(customer.account().get()) : null;
        var id = customer.id() != null ? UUID.fromString(customer.id().value()) : null;
        return new CustomerEntity(
                id,
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                customer.phone(),
                account,
                customer.loyaltyPoints()
        );
    }

    public Customer toDto(CustomerEntity entity) {
        return new Customer(
                new CustomerId(entity.getId().toString()),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                Optional.of(accountMapper.toDto(entity.getAccount())),
                entity.getLoyaltyPoints()
        );
    }

    public LoyaltyPoints toLoyaltyPoints(CustomerEntity entity) {
        return new LoyaltyPoints(
          entity.getId().toString(),
          new Points(entity.getLoyaltyPoints())
        );
    }

    public CustomerEntity toCustomerEntity(CustomerEntity customer, LoyaltyPoints loyaltyPoints) {
        return new CustomerEntity(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAccount(),
                loyaltyPoints.points().points()
        );
    }
}
