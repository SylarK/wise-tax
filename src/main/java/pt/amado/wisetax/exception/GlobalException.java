package pt.amado.wisetax.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class GlobalException extends Exception {

    private int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public GlobalException(String message, Throwable error) {
        super(message, error);
    }

    public GlobalException(String message) {super(message);}

}
