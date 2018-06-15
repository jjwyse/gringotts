package email.util;

import email.pojo.Email;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates that the given email object is valid, where valid is defined as having all of the proper fields
 * populated, and ensuring all of their constraints are met
 * @author jjwyse
 */
public class EmailValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Email.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object o, Errors e) {
        // should never really happen ...
        if (!(o instanceof Email)) { return; }

        // Validate required fields
        Email email = (Email) o;
        ValidationUtils.rejectIfEmpty(e, "to", "to.empty");
    }
}
