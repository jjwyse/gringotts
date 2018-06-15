package email.util;

import email.pojo.Email;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class EmailValidatorTest {
    @Test
    public void testSupports() throws Exception {
        EmailValidator validator = new EmailValidator();
        Assert.assertTrue(validator.supports(Email.class));
        Assert.assertFalse(validator.supports(String.class));
        Assert.assertFalse(validator.supports(null));
    }

    @Test
    public void testValidateAllFields() throws Exception {
        EmailValidator validator = new EmailValidator();
        Email email = new Email(
                "lbj@cavs.com",
                "GOAT",
                "jr.smith@cavs.com",
                "swish",
                "i'm sorry",
                "i thought we were ahead"
        );
        Errors errors = new BeanPropertyBindingResult(email, "email");
        validator.validate(email, errors);
    }
}