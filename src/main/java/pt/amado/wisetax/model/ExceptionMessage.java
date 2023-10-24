package pt.amado.wisetax.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionMessage {
    private int code;
    private String generalMessage;
    private String specificMessage;
}
