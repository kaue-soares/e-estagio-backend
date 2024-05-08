package kauesoares.eestagio.authservice.base;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import kauesoares.eestagio.authservice.messages.exception.BadRequestException;
import kauesoares.eestagio.authservice.messages.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ConstraintViolationExceptionResponse unknownException(MethodArgumentNotValidException ex, WebRequest req) {
        HashMap<String, String> exceptions = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> exceptions.put(error.getField(), error.getDefaultMessage()));
        return new ConstraintViolationExceptionResponse(exceptions);
    }

    @ExceptionHandler(value = {jakarta.validation.ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ConstraintViolationExceptionResponse unknownException(jakarta.validation.ConstraintViolationException ex, WebRequest req) {
        return new ConstraintViolationExceptionResponse(getMessagesFromConstraintViolationException(ex));
    }

    @ExceptionHandler(value = {TransactionSystemException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ConstraintViolationExceptionResponse unknownException(TransactionSystemException ex, WebRequest req) {
        return new ConstraintViolationExceptionResponse(
                getMessagesFromConstraintViolationException((jakarta.validation.ConstraintViolationException) ex.getMostSpecificCause()));
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Map<String, String> unknownException(DataIntegrityViolationException ex, WebRequest req) {
        return getMessagesFromConstraintViolationException(
                (SQLIntegrityConstraintViolationException) ex.getMostSpecificCause());
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorizedException(UnauthorizedException ex, WebRequest req) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequestException(BadRequestException ex, WebRequest req) {
        return ex.getMessage();
    }

    @Data
    @AllArgsConstructor
    private static class ConstraintViolationExceptionResponse {
        private Map<String, String> exceptions;
    }

    private static Map<String, String> getMessagesFromConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> map = new HashMap<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            map.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return map;
    }

    private static Map<String, String> getMessagesFromConstraintViolationException(
            SQLIntegrityConstraintViolationException e) {
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getMessage());
        return map;
    }

}
