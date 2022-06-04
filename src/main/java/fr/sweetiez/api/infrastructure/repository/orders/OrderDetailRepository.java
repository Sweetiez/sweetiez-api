package fr.sweetiez.api.infrastructure.repository.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, UUID> {

    List<OrderDetailEntity> findAllByOrderId(UUID orderId);

}
