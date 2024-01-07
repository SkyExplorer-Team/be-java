package synrgy.finalproject.skyexplorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmailAlreadyException extends RuntimeException{

    public EmailAlreadyException() {
        super("Email already exist!");
    }
}
