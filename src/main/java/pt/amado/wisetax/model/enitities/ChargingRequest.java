package pt.amado.wisetax.model.enitities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import pt.amado.wisetax.model.enums.Service;

import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
@Getter
public class ChargingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String phoneNumber;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;

    @Column(nullable = false)
    private Service service;

    @Column(nullable = false)
    private long rsu;

    @Column(nullable = false)
    private boolean roaming;
}
