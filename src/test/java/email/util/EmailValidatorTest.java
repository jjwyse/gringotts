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
        Email email = new Email("lbj@cavs.com", "GOAT", "jr.smith@cavs.com", "swish", "i'm sorry",
                "thought we were ahead");
        Errors errors = new BeanPropertyBindingResult(email, "email");
        validator.validate(email, errors);
        Assert.assertFalse(errors.hasErrors());
    }

    @Test
    public void testValidateToFieldNull() throws Exception {
        Email email = new Email(null, "GOAT", "jr.smith@cavs.com", "swish", "i'm sorry", "thought we were ahead");
        validateNullField(email, "to");
    }

    @Test
    public void testValidateFromFieldNull() throws Exception {
        Email email = new Email("lbj@cavs.com", "GOAT", null, "swish", "i'm sorry", "thought we were ahead");
        validateNullField(email, "from");
    }

    @Test
    public void testValidateToFieldInvalidEmail() throws Exception {
        Email email = new Email("ImNotAValidEmailAddress!", "GOAT", "jr.smith@cavs.com", "swish", "i'm sorry",
                "thought we were ahead");
        Errors errors = validateBadEmail(email, "to");
        Assert.assertTrue(errors.getFieldError("to").getRejectedValue().equals(email.getTo()));
    }

    @Test
    public void testValidateFromFieldInvalidEmail() throws Exception {
        // no @
        Email email = new Email("lbj@cavs.com", "GOAT", "jr.smithcavs.com", "swish", "i'm sorry",
                "thought we were ahead");
        Errors errors = validateBadEmail(email, "from");
        Assert.assertTrue(errors.getFieldError("from").getRejectedValue().equals(email.getFrom()));
    }

    @Test
    public void testValidateTo_NameFieldNull() throws Exception {
        Email email = new Email("lbj@j.com", null, "jr.smith@cavs.com", "swish", "i'm sorry", "though we were ahead");
        validateNullField(email, "to_name");
    }

    @Test
    public void testValidateFrom_NameFieldNull() throws Exception {
        Email email = new Email("lbj@j.com", "GOAT", "jr.smith@cavs.com", null, "i'm sorry", "though we were ahead");
        validateNullField(email, "from_name");
    }

    @Test
    public void testValidateSubjectFieldNull() throws Exception {
        Email email = new Email("lbj@j.com", "GOAT", "jr.smith@cavs.com", "swish", null, "though we were ahead");
        validateNullField(email, "subject");
    }

    @Test
    public void testValidateBodyFieldNull() throws Exception {
        Email email = new Email("lbj@j.com", "GOAT", "jr.smith@cavs.com", "swish", "wtf", null);
        validateNullField(email, "body");
    }

    @Test
    public void testAllFieldsNull() throws Exception {
        EmailValidator validator = new EmailValidator();
        Email email = new Email();
        Errors errors = new BeanPropertyBindingResult(email, "email");
        validator.validate(email, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertTrue(errors.getErrorCount() == 6); // all 6 fields
    }

    private static void validateNullField(Email email, String field) {
        EmailValidator validator = new EmailValidator();
        Errors errors = new BeanPropertyBindingResult(email, "email");
        validator.validate(email, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(1, errors.getErrorCount());
        Assert.assertNotNull(errors.getFieldError(field).getField());
    }

    private static Errors validateBadEmail(Email email, String field) {
        EmailValidator validator = new EmailValidator();
        Errors errors = new BeanPropertyBindingResult(email, "email");
        validator.validate(email, errors);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertEquals(1, errors.getErrorCount());
        Assert.assertNotNull(errors.getFieldError(field).getField());
        return errors;
    }
}