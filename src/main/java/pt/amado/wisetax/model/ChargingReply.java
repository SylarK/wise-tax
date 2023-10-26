package pt.amado.wisetax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.amado.wisetax.model.ChargingResult;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargingReply {

    @Embedded
    private ChargingResult chargingResult;

    private long gsu;

}
