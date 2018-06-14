package email.service;

import email.pojo.Email;
import email.pojo.EmailResponse;

/**
 * TODO - JJW
 * @author jjwyse
 */
public interface EmailService {
    EmailResponse sendEmail(Email email);
}
