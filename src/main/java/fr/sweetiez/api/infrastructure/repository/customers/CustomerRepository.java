package fr.sweetiez.api.infrastructure.repository.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.DoubleStream;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByAccountId(UUID accountId);

    Optional<CustomerEntity> findByEmail(String email);
}
