package pt.amado.wisetax.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import pt.amado.wisetax.model.enums.RequestedService;

import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
@Getter
public class ChargingRequest {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String phoneNumber;

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;

    @Column(nullable = false)
    private RequestedService service;

    @Column(nullable = false)
    private long rsu;

    @Column(nullable = false)
    private boolean roaming;
}
