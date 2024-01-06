package synrgy.finalproject.skyexplorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import synrgy.finalproject.skyexplorer.model.dto.response.FailResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

@ControllerAdvice
public class CustomizedResponseExceptionHandler {

    @ExceptionHandler(EmailAlreadyException.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
        FailResponse response = buildFailResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private FailResponse buildFailResponse(String message) {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("data", message);
        return new FailResponse("fail", dataMap);    }
}
