package email.service.impl;

import email.exception.EmailServiceException;
import email.pojo.Email;
import email.pojo.EmailResponse;
import email.service.EmailService;

/**
 * TODO - JJW
 * @author jjwyse
 */
public class MandrillServiceImpl implements EmailService {
    @Override
    public EmailResponse sendEmail(Email email) {
        throw new EmailServiceException("Not yet implemented");
    }
}
