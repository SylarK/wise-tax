package pt.amado.wisetax.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pt.amado.wisetax.exception.GlobalException;
import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.exception.UserNotFoundException;
import pt.amado.wisetax.model.ChargingReply;
import pt.amado.wisetax.model.ChargingResult;
import pt.amado.wisetax.model.ExceptionMessage;
import pt.amado.wisetax.model.enums.Status;

import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleException(Exception ex) {
        ExceptionMessage exceptionDTO = ExceptionMessage.builder()
                .code(500)
                .generalMessage("An error occurred while processing the request.")
                .specificMessage(ex.getMessage()).build();

        log.error(Arrays.toString(ex.getStackTrace()), ex.getCause());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionDTO = ExceptionMessage.builder()
                .code(status.value())
                .generalMessage("An error occurred while validation of provided input.")
                .specificMessage(ex.getMessage()).build();

        log.error(Arrays.toString(ex.getStackTrace()), ex.getCause());
        return ResponseEntity.status(status).body(exceptionDTO);
    }

    @ExceptionHandler(RequestNotEligibleException.class)
    public ResponseEntity<ChargingReply> handleRequestNotEligibleCustomException(RequestNotEligibleException ex) {
        ChargingResult result = new ChargingResult(Status.NOT_ELIGIBLE, ex.getMessage());
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode());

        log.error(Arrays.toString(ex.getStackTrace()), ex.getCause());
        return ResponseEntity.status(status).body(new ChargingReply(result, 0));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleUserNotFoundCustomException(UserNotFoundException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode());
        ExceptionMessage exceptionDTO = ExceptionMessage.builder()
                .code(status.value())
                .generalMessage("Was not possible to fetch any data because the provided user does not exists in our database.")
                .specificMessage(ex.getMessage()).build();

        log.error(Arrays.toString(ex.getStackTrace()), ex.getCause());
        return ResponseEntity.status(status).body(exceptionDTO);
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionMessage> handleGlobalCustomException(GlobalException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode());
        ExceptionMessage exceptionDTO = ExceptionMessage.builder()
                .code(status.value())
                .generalMessage("Something went wrong during the processing flow.")
                .specificMessage(ex.getMessage()).build();

        log.error(Arrays.toString(ex.getStackTrace()), ex.getCause());
        return ResponseEntity.status(status).body(exceptionDTO);
    }

}
