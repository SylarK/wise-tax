package pt.amado.wisetax.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionMessageDTO {
    private int code;
    private String generalMessage;
    private String specificMessage;
}
