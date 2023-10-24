package pt.amado.wisetax.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pt.amado.wisetax.model.ExceptionMessage;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleException(Exception ex) {
        ExceptionMessage exceptionDTO = ExceptionMessage.builder()
                .code(500)
                .generalMessage("An error occurred while processing the request.")
                .specificMessage(ex.getMessage()).build();

        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionDTO = ExceptionMessage.builder()
                .code(status.value())
                .generalMessage("An error occurred while validation of provided input.")
                .specificMessage(ex.getMessage()).build();

        return ResponseEntity.status(status).body(exceptionDTO);
    }

}
