package email.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import email.pojo.Email;
import email.pojo.EmailResponse;
import email.service.EmailService;
import email.util.MailgunEmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.HtmlUtils;

/**
 * Handles interacting with the Mailgun API
 * @author jjwyse
 */
public class MailgunServiceImpl implements EmailService {
    private static final String HEADER_FORM_URL_ENCODED = "x-www-form-urlencoded";

    @Value("${mailgun.base.url}")
    private String baseUrl;

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Override
    public EmailResponse sendEmail(Email email) {
        // something like https://api.mailgun.net/v3/some-domain.mailgun.org/messages
        String url = String.format("%s/%s", baseUrl, "messages");

        String from = String.format("%s %s", email.getFrom_name(), email.getFrom());
        String to = String.format("%s %s", email.getTo_name(), email.getTo());
        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .basicAuth("api", apiKey)
                    .header("accept", "application/json")
                    .field("from", from, HEADER_FORM_URL_ENCODED)
                    .field("to", to, HEADER_FORM_URL_ENCODED)
                    .field("subject", email.getSubject(), HEADER_FORM_URL_ENCODED)
                    .field("text", HtmlUtils.htmlEscape(email.getBody()), HEADER_FORM_URL_ENCODED)
                    .asJson();
            return MailgunEmailUtil.toEmailResponse(response.getBody().getObject());
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }
}
