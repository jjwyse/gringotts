package email.util;

import email.service.EmailService;
import email.service.impl.MailgunServiceImpl;
import email.service.impl.MandrillServiceImpl;
import email.service.impl.SendgridServiceImpl;

/**
 * Knows about the different {@link email.service.EmailService} that exist in gringotts,
 * and generates the proper one given a well known name
 * @author jjwyse
 */
public class EmailServiceFactory {
    public static EmailService build(String emailService) {
        switch (emailService) {
            case "mailgun":
                return new MailgunServiceImpl();
            case "sendgrid":
                return new SendgridServiceImpl();
            case "mandrill":
            default:
                return new MandrillServiceImpl();
        }
    }
}
