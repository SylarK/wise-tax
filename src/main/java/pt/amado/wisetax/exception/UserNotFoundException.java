package pt.amado.wisetax.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GlobalException{

    public UserNotFoundException(String message, Throwable error) {
        super(message, error);
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
    }

    public UserNotFoundException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
    }

}
