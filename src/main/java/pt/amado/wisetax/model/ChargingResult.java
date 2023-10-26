package pt.amado.wisetax.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import pt.amado.wisetax.model.enums.Status;

@Embeddable
@Builder
@NoArgsConstructor
public class ChargingResult {

    @Enumerated(EnumType.STRING)
    private Status status;
    private String reason;

    public ChargingResult(Status status, String reason) {
        if(Status.NOT_ELIGIBLE.equals(status)) {
            if(!StringUtils.hasText(reason))
                throw new IllegalArgumentException("The provided status must to implement a reason description");
            this.reason = reason;
        }
    }

}
