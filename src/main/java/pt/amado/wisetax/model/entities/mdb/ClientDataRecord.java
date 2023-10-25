package pt.amado.wisetax.model.entities.mdb;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.amado.wisetax.model.Buckets;
import pt.amado.wisetax.model.Counters;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

import java.time.Instant;

@Document(collection = "clientDataRecords")
@Data
@Builder
public class ClientDataRecord {

    @Id
    private String id;

    private Instant createdAt;
    private String phoneNumber;
    private Tariff tariff;
    private ChargingRequest chargingRequest;
    private Buckets buckets;
    private Counters counters;

}
