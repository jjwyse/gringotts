package email.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import email.service.exception.EmailServiceException;
import email.pojo.Email;
import email.service.EmailService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

/**
 * Handles all interactions with the Mandrill API
 * @author jjwyse
 */
public class MandrillServiceImpl implements EmailService {
    private static final String FROM_EMAIL = "from_email";
    private static final String FROM_NAME = "from_name";
    private static final String KEY = "key";
    private static final String MESSAGE = "message";
    private static final String SEND_EMAIL_URI = "messages/send.json";

    @Value("${mandrill.base.url}")
    private String baseUrl;

    @Value("${mandrill.api.key}")
    private String apiKey;

    @Override
    public void sendEmail(Email email) {
        String url = String.format("%s/%s", baseUrl, SEND_EMAIL_URI);

        // :grimacing:
        String jsonBody = new JSONObject()
                .put(KEY, apiKey)
                .put(MESSAGE, new JSONObject()
                        .put(TEXT, email.getBody())
                        .put(SUBJECT, email.getSubject())
                        .put(FROM_EMAIL, email.getFrom())
                        .put(FROM_NAME, email.getFrom_name()))
                .put(TO, new JSONArray()
                        .put(new JSONObject()
                                .put(EMAIL, email.getTo())
                                .put(NAME, email.getTo_name())
                                .put(TYPE, TO)))
                .toString();

        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .header(ACCEPT, APPLICATION_JSON)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .body(jsonBody)
                    .asJson();
            if (!HttpStatus.resolve(response.getStatus()).is2xxSuccessful()) {
                throw new EmailServiceException(response.getBody().toString());
            }
        } catch (UnirestException e) {
            throw new EmailServiceException(e);
        }
    }
}
