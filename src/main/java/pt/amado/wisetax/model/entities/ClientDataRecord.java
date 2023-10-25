package pt.amado.wisetax.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import pt.amado.wisetax.model.Buckets;
import pt.amado.wisetax.model.Counters;
import pt.amado.wisetax.model.enums.Tariff;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class ClientDataRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Tariff tariff;

    @OneToOne(mappedBy = "clientDataRecord")
    private ChargingRequest chargingRequest;

    @Column(nullable = false)
    private Buckets buckets;

    @Column(nullable = false)
    private Counters buckets;
}
