package kauesoares.eestagio.storageservice.messages.exception;

import kauesoares.eestagio.storageservice.messages.MessageFactory;
import kauesoares.eestagio.storageservice.messages.Messages;

public abstract class ServiceException extends RuntimeException {

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
