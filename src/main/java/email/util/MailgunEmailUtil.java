package email.util;

import email.pojo.EmailResponse;
import org.json.JSONObject;

/**
 * TODO - JJW
 * @author jjwyse
 */
public class MailgunEmailUtil {
    public static EmailResponse toEmailResponse(JSONObject json) {
        if (json == null) {
            return null;
        }

        return new EmailResponse(
                json.getString("id"),
                json.getString("message"));
    }
}
