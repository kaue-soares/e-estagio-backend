package kauesoares.eestagio.storageservice.messages.exception;

import kauesoares.eestagio.storageservice.messages.Messages;

public class S3Exception extends ServiceException{
    public S3Exception(Messages message) {
        super(message);
    }
}
