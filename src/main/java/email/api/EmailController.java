package email.api;

import email.api.exception.BadRequestException;
import email.pojo.Email;
import email.service.EmailService;
import email.service.exception.EmailServiceException;
import email.util.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    /**
     * Defines our /email API.  This API just returns a 200 if all things are successful.  If not, then it
     * @param email The email to send
     */
    @RequestMapping(value = "/v1/email", method = RequestMethod.POST)
    public void sendEmail(@Valid @RequestBody Email email) {
        try {
            emailService.sendEmail(email);
        } catch (EmailServiceException e) {
            throw new BadRequestException(e);
        }
    }

    /**
     * Specifies how to validate incoming email POJOs
     * @param binder The Spring binder to bind our validator onto
     */
    @InitBinder("email")
    public void initBrandingBinder(WebDataBinder binder) { binder.setValidator(new EmailValidator()); }
}
