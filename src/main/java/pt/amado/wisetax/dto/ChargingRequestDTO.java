package pt.amado.wisetax.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ChargingRequestDTO {
    @NotEmpty(message = "Service is required")
    @Pattern(regexp = "^[AB]$", message = "Service must be 'A' or 'B'")
    private String service;

    private boolean roaming;

    @Pattern(regexp = "\\+?[1-9]\\d{1,14}", message = "Invalid MSISDN format")
    private String msisdn;

    @Positive(message = "RSU must be a positive number")
    private int rsu;

}
