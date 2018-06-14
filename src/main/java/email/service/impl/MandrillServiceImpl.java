package email.service.impl;

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
        // TODO - JJW
        return new EmailResponse("foo", "bar");
    }
}
