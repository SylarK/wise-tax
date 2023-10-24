package pt.amado.wisetax.model.enitities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
public class BillingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String phoneNumber;

    private long bucket1;

    private long bucket2;

    private long bucket3;

    private long counterA;

    private long counterB;

    private long counterC;

    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastRequestUpdate;

    //TODO: implement the "tarifário"

}
