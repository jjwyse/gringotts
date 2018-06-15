package email.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import email.exception.EmailServiceException;
import email.pojo.Email;
import email.service.EmailService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

/**
 * Handles all interactions with the Sendgrid email service
 * @author jjwyse
 */
public class SendgridServiceImpl implements EmailService {
    private static final String PERSONALIZATIONS = "personalizations";
    private static final String SEND_EMAIL_URI = "mail/send";

    @Value("${sendgrid.base.url}")
    private String baseUrl;

    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Override
    public void sendEmail(Email email) {
        String url = String.format("%s/%s", baseUrl, SEND_EMAIL_URI);

        // woof ... one of those times where I really hate the verbosity of Java.  save me JS :prayer:
        String jsonBody = new JSONObject()
                .put(PERSONALIZATIONS, new JSONArray()
                        .put(new JSONObject()
                                .put(TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put(EMAIL, email.getTo())
                                                .put(NAME, email.getTo_name())))))
                .put(FROM, new JSONObject()
                        .put(EMAIL, email.getFrom())
                        .put(NAME, email.getFrom_name()))
                .put(SUBJECT, email.getSubject())
                .put(CONTENT, new JSONArray()
                        .put(new JSONObject()
                                .put(TYPE, TEXT_PLAIN)
                                .put(VALUE, email.getBody())))
                .toString();

        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .header(AUTHORIZATION, String.format("%s %s", BEARER, apiKey))
                    .header(ACCEPT, APPLICATION_JSON)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .body(jsonBody)
                    .asJson();
            // TODO - JJW - check for errors in the response and handle appropriately
        } catch (UnirestException e) {
            throw new EmailServiceException(e);
        }
    }
}
