package synrgy.finalproject.skyexplorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EmailUnregisterException extends RuntimeException {

    public EmailUnregisterException() {
        super("Email didn't register");
    }
}
