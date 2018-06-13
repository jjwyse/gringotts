package core;

import email.service.EmailService;
import email.service.impl.MailgunServiceImpl;
import email.service.impl.MandrillServiceImpl;
import email.service.impl.SendgridServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"email"})
public class Application {
    @Value("${email.service:mandrill}")
    private String emailService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public EmailService emailService(ApplicationContext ctx) {
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
