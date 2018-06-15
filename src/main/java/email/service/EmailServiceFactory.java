package email.service;

import email.service.impl.MailgunServiceImpl;
import email.service.impl.MandrillServiceImpl;
import email.service.impl.SendgridServiceImpl;

/**
 * Knows about the different {@link email.service.EmailService} that exist in gringotts,
 * and generates the proper one given a well known name
 * @author jjwyse
 */
public class EmailServiceFactory {
    /**
     * Builds the proper EmailService based on the given email service provider
     * @param emailService The service provider
     * @return The email service based on the given provider
     */
    public static EmailService build(EmailServiceProvider emailService) {
        if (emailService == null) {
            return new MandrillServiceImpl();
        }

        switch (emailService) {
            case mailgun:
                return new MailgunServiceImpl();
            case sendgrid:
                return new SendgridServiceImpl();
            case mandrill:
            default:
                return new MandrillServiceImpl();
        }
    }
}
