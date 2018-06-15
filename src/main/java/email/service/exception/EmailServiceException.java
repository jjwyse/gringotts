package email.service.exception;

/**
 * TODO - JJW
 * @author jjwyse
 */
public class EmailServiceException extends RuntimeException {
    public EmailServiceException(Exception e) {
        super(e);
    }

    public EmailServiceException(String message) {
        super(message);
    }
}
