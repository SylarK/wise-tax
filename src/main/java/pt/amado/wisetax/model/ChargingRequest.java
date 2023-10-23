package pt.amado.wisetax.model;

import jakarta.persistence.*;
import pt.amado.wisetax.model.enums.Service;

import java.time.Instant;
import java.util.UUID;

@Entity
public class ChargingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String requestId;

    @JoinColumn(nullable = false)
    @ManyToOne
    private MSISDN phoneNumber;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Service service;

    @Column(nullable = false)
    private long requestedServiceUnits;

}
