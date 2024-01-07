package synrgy.finalproject.skyexplorer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("Password incorrect");
    }
}
