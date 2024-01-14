package synrgy.finalproject.skyexplorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import synrgy.finalproject.skyexplorer.model.dto.response.ResponseDto;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class CustomizedResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyException.class)
    public final ResponseEntity<Object> handleEmailAlreadyExistException(Exception ex, WebRequest request) throws Exception {
        HashMap<String, String> resp = new HashMap<>();
        resp.put("data", ex.getMessage());
        return new ResponseEntity<>(new ResponseDto("fail", resp), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailUnregisterException.class)
    public final ResponseEntity<Object> handleEmailUnregisterException(Exception ex, WebRequest request) throws Exception {
        HashMap<String, String> resp = new HashMap<>();
        resp.put("data", ex.getMessage());
        return new ResponseEntity<>(new ResponseDto("fail", resp), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public final ResponseEntity<Object> handleWrongPasswordException(Exception ex, WebRequest request) throws Exception {
        HashMap<String, String> resp = new HashMap<>();
        resp.put("data", ex.getMessage());
        return new ResponseEntity<>(new ResponseDto("fail", resp), HttpStatus.CONFLICT);
    }


}
