package email.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import email.service.exception.EmailServiceException;
import email.pojo.Email;
import email.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

/**
 * Handles interacting with the Mailgun API
 * @author jjwyse
 */
public class MailgunServiceImpl implements EmailService {

    @Value("${mailgun.base.url}")
    private String baseUrl;

    @Value("${mailgun.api.key}")
    private String apiKey;

    @Override
    public void sendEmail(Email email) {
        // something like https://api.mailgun.net/v3/some-domain.mailgun.org/messages
        String url = String.format("%s/%s", baseUrl, "messages");

        String from = String.format("%s %s", email.getFrom_name(), email.getFrom());
        String to = String.format("%s %s", email.getTo_name(), email.getTo());
        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .basicAuth(API, apiKey)
                    .header(ACCEPT, APPLICATION_JSON)
                    .field(TO, to, FORM_URL_ENCODED)
                    .field(FROM, from, FORM_URL_ENCODED)
                    .field(SUBJECT, email.getSubject(), FORM_URL_ENCODED)
                    .field(TEXT, email.getBody(), FORM_URL_ENCODED)
                    .asJson();
            if (!HttpStatus.resolve(response.getStatus()).is2xxSuccessful()) {
                throw new EmailServiceException(response.getBody().toString());
            }
        } catch (UnirestException e) {
            throw new EmailServiceException(e);
        }
    }
}
