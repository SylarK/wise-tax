package pt.amado.wisetax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.amado.wisetax.model.entities.BillingAccount;

import java.util.UUID;

@Repository
public interface BillingAccountRepository extends JpaRepository<BillingAccount, UUID> {

    BillingAccount findByPhoneNumber(String phoneNumber);

}
