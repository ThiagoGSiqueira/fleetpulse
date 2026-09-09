package com.fleetpulse.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid fields.");
        List<String> listErrors = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(error -> error.getField() +": " + error.getDefaultMessage())
        .toList();
        pd.setProperty("errors", listErrors);
        return pd;
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ProblemDetail driverNotFoundException(DriverNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        return pd;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String providedValue = ex.getValue() != null ? ex.getValue().toString() : "No value provided.";
        String expectedType = ex.getRequiredType().getSimpleName();
        String detail = String.format("The parameter '%s' received the value '%s', but expected type '%s'.", 
                              paramName, providedValue, expectedType);        
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail genericException(Exception ex) {
        ex.printStackTrace();
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Please try again later.");
        return pd;
    }
}
