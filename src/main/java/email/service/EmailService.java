package email.service;

import email.pojo.Email;

/**
 * Main email service interface, defining the API for interacting with any of our different email services within
 * gringotts
 * @author jjwyse
 */
public interface EmailService {
    String ACCEPT = "Accept";
    String API = "api";
    String APPLICATION_JSON = "application/json";
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer";
    String CONTENT = "content";
    String CONTENT_TYPE = "Content-type";
    String EMAIL = "email";
    String FORM_URL_ENCODED = "x-www-form-urlencoded";
    String FROM = "from";
    String NAME = "name";
    String SUBJECT = "subject";
    String TEXT = "text";
    String TEXT_PLAIN = "text/plain";
    String TO = "to";
    String TYPE = "type";
    String VALUE = "value";

    /**
     * Send an email
     * @param email The email to send
     */
    void sendEmail(Email email);
}
