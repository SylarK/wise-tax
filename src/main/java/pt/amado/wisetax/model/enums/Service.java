package pt.amado.wisetax.model.enums;

public enum Service {
    A("A"),
    B("B");

    private final String value;

    Service(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
