package kauesoares.eestagio.authservice.messages.exception;

import kauesoares.eestagio.authservice.messages.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ServiceException{

    public UnauthorizedException(Messages message, String... args) {
        super(message, args);
    }
}
