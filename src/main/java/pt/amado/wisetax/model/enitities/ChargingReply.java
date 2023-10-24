package pt.amado.wisetax.model.enitities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.amado.wisetax.model.ChargingResult;

import java.util.UUID;

@Entity
@Setter
@Getter
public class ChargingReply {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private ChargingResult chargingResult;

    private long gsu;

}