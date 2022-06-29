package fr.sweetiez.api.infrastructure.repository.orders;

import fr.sweetiez.api.core.orders.models.orders.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderEntity> findByPaymentIntent(String paymentIntent);

    List<OrderEntity> findByEmail(String email);

    List<OrderEntity> findAllByCreatedAtAfter(LocalDate date);

    List<OrderEntity> findAllByCreatedAtAfterAndCreatedAtBefore(LocalDate after, LocalDate before);

    List<OrderEntity> findAllByStatusOrderByPickupDateAsc(OrderStatus status);

}
