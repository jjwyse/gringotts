package email;

import email.pojo.Email;
import email.pojo.EmailResponse;
import email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public EmailResponse sendEmail(@Valid @RequestBody Email email) {
        return emailService.sendEmail(email);
    }
}
