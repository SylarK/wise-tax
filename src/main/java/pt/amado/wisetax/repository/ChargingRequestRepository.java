package pt.amado.wisetax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.amado.wisetax.model.enitities.ChargingRequest;

import java.util.UUID;

@Repository
public interface ChargingRequestRepository extends JpaRepository<ChargingRequest, UUID> {
}
