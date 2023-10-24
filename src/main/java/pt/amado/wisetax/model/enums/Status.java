package pt.amado.wisetax.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pt.amado.wisetax.model.ChargingResult;

@AllArgsConstructor
@Getter
public enum Status {

    OK("Eligible"),
    CREDIT_LIMIT_REACHED("Credit Limit Reached"),
    NOT_ELIGIBLE("Not Eligible");

    private final String description;
}
