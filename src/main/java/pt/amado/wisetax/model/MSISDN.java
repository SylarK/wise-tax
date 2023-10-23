package pt.amado.wisetax.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Entity
@NoArgsConstructor
public class MSISDN {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String msisdn;

    private static final String MSISDN_REGEX = "^\\+?[1-9]\\d{1,14}$";

    public MSISDN(String value) {
        if (isValidMSISDN(value)) {
            this.msisdn = value;
        } else {
            log.error("The provided value {} is not a valid MSISDN", value);
            throw new IllegalArgumentException("Invalid MSISDN: " + value);
        }
    }

    public boolean isValidMSISDN(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        if (value.startsWith("+")) {
            value = value.substring(1);
        }

        return Pattern.matches(MSISDN_REGEX, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MSISDN msisdn1 = (MSISDN) o;
        return Objects.equals(id, msisdn1.id) && Objects.equals(msisdn, msisdn1.msisdn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, msisdn);
    }
}

