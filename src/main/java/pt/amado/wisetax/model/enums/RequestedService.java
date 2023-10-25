package pt.amado.wisetax.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestedService {
    A("A"),
    B("B");

    private final String value;

}
