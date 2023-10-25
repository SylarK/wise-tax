package pt.amado.wisetax.exception;

import org.springframework.http.HttpStatus;

public class RequestNotEligibleException extends GlobalException {

    public RequestNotEligibleException(String message, Throwable error){
        super(String.format("%s:%s", "NOT ELIGIBLE", message), error);
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
    };

    public RequestNotEligibleException(String message){
        super(String.format("%s:%s", "NOT ELIGIBLE", message));
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
    };

    public RequestNotEligibleException(Throwable error){
        super("NOT ELIGIBLE", error);
    };

}
