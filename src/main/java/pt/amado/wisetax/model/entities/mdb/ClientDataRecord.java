package pt.amado.wisetax.model.entities.mdb;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
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
    @Field(targetType = FieldType.INT64)
    private Long bucket1;

    @Field(targetType = FieldType.INT64)
    private Long bucket2;

    @Field(targetType = FieldType.INT64)
    private Long bucket3;

    @Field(targetType = FieldType.INT64)
    private Long counterA;

    @Field(targetType = FieldType.INT64)
    private Long counterB;

    @Field(targetType = FieldType.INT64)
    private Long counterC;

    public ClientDataRecord(
            String id,
            Instant createdAt,
            String phoneNumber,
            Tariff tariff,
            ChargingRequest chargingRequest,
            Long bucket1,
            Long bucket2,
            Long bucket3,
            Long counterA,
            Long counterB,
            Long counterC
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        this.tariff = tariff;
        this.chargingRequest = chargingRequest;
        this.bucket1 = (bucket1 != null) ? bucket1 : 0L;
        this.bucket2 = (bucket2 != null) ? bucket2 : 0L;
        this.bucket3 = (bucket3 != null) ? bucket3 : 0L;
        this.counterA = (counterA != null) ? counterA : 0L;
        this.counterB = (counterB != null) ? counterB : 0L;
        this.counterC = (counterC != null) ? counterC : 0L;
    }


}
