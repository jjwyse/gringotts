package email.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import email.exception.EmailServiceException;
import email.pojo.Email;
import email.service.EmailService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

/**
 * Handles all interactions with the Mandrill API
 * @author jjwyse
 */
public class MandrillServiceImpl implements EmailService {
    private static final String KEY = "key";
    private static final String SEND_EMAIL_URI = "messages/send.json";

    @Value("${mandrill.base.url}")
    private String baseUrl;

    @Value("${mandrill.api.key}")
    private String apiKey;

    @Override
    public void sendEmail(Email email) {
        String url = String.format("%s/%s", baseUrl, SEND_EMAIL_URI);

        String jsonBody = new JSONObject()
                .put(KEY, apiKey)
                .toString();

        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
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
