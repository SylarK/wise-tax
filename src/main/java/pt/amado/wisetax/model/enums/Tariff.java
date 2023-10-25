package pt.amado.wisetax.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tariff {

    ALPHA_1("Alpha 1"),
    ALPHA_2("Alpha 2"),
    ALPHA_3("Alpha 3"),
    BETA_1("Beta 1"),
    BETA_2("Beta 2"),
    BETA_3("Beta 3");


    private final String value;

    public static boolean isServiceA(Tariff tariff) {
        return ALPHA_1.equals(tariff) || ALPHA_2.equals(tariff) || ALPHA_3.equals(tariff);
    }
}
