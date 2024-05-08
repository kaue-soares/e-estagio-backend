package kauesoares.eestagio.authservice.messages.exception;

import kauesoares.eestagio.authservice.messages.Messages;
import kauesoares.eestagio.authservice.messages.MessageFactory;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Messages message) {
        super(MessageFactory.getMessage(message));
    }

    public ServiceException(Messages message, String... args) {
        super(MessageFactory.getMessage(message, args));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
