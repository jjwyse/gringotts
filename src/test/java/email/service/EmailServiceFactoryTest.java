package email.service;

import email.service.impl.MailgunServiceImpl;
import email.service.impl.MandrillServiceImpl;
import email.service.impl.SendgridServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class EmailServiceFactoryTest {
    @Test
    public void testBuildHandlesAllProviders() throws Exception {
        EmailService service = EmailServiceFactory.build(EmailServiceProvider.mailgun);
        Assert.assertTrue(MailgunServiceImpl.class.equals(service.getClass()));

        service = EmailServiceFactory.build(EmailServiceProvider.mandrill);
        Assert.assertTrue(MandrillServiceImpl.class.equals(service.getClass()));

        service = EmailServiceFactory.build(EmailServiceProvider.sendgrid);
        Assert.assertTrue(SendgridServiceImpl.class.equals(service.getClass()));
    }

    @Test
    public void testBuildHandlesNull() throws Exception {
        // should default to mandrill
        EmailService service = EmailServiceFactory.build(null);
        Assert.assertTrue(MandrillServiceImpl.class.equals(service.getClass()));
    }
}