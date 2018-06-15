package email.service.exception;

/**
 * Generic service exception that indicates that something known went wrong in the service tier
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
