package pt.amado.wisetax.model;

import jakarta.persistence.*;
import lombok.Setter;
import pt.amado.wisetax.model.enums.Service;

import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
public class ChargingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private MSISDN phoneNumber;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Service service;

    @Column(nullable = false)
    private long rsu;

    @Column(nullable = false)
    private boolean roaming;
}
