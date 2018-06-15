package email.util;

import email.pojo.Email;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Validates that the given email object is valid, where valid is defined as having all of the proper fields
 * populated, and ensuring all of their constraints are met
 * @author jjwyse
 */
public class EmailValidator implements Validator {
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    @Override
    public boolean supports(Class<?> clazz) {
        return Email.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object o, Errors e) {
        // should never really happen ...
        if (!(o instanceof Email)) { return; }

        Email email = (Email) o;

        // to and from should be email address
        ValidationUtils.rejectIfEmpty(e, "to", "email.to", "Please specify a valid email address in the 'to' field");
        ValidationUtils.rejectIfEmpty(e, "from", "email.from", "Please specify a valid email address in the 'from' " +
                "field");
        if (email.getTo() != null && !isValidEmail(email.getTo())) {
            e.rejectValue("to", "email.to", "The email address in the 'to' field is invalid");
        }

        if (email.getFrom() != null && !isValidEmail(email.getFrom())) {
            e.rejectValue("from", "email.from", "The email address in the 'from' field is invalid");
        }

        // to_name and from_name shouldn't be empty
        ValidationUtils.rejectIfEmpty(e, "to_name", "email.to_name",
                "Please specify a name in the 'to_name' field");
        ValidationUtils.rejectIfEmpty(e, "from_name", "email.from_name",
                "Please specify a name in the 'from_name' field");

        // subject and body shouldn't be empty
        ValidationUtils.rejectIfEmpty(e, "subject", "email.subject",
                "Please specify an email subject in the 'subject' field");
        ValidationUtils.rejectIfEmpty(e, "body", "email.body",
                "Please specify the body of the email in the 'body' field");
    }

    private static boolean isValidEmail(String email) {
        return email != null && pattern.matcher(email).matches();
    }
}
