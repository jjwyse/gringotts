package email.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is used at our API layer, and indicates to our framework that a `400` HTTP status code should be
 * set if this exception is being thrown
 * @author jjwyse
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(Exception e) {
        super(e);
    }
}
