package synrgy.finalproject.skyexplorer.controller;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import synrgy.finalproject.skyexplorer.exception.BadRequestResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BadRequestResponse> constraintViolationException (ConstraintViolationException e) {
        log.error("[Error] {} ", e.getMessage());
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(
                err -> errors.put(err.getPropertyPath().toString(), err.getMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BadRequestResponse.<String>builder()
                        .errors(errors)
                        .build());


    }
}
