package email.pojo;

/**
 * TODO - JJW
 * @author jjwyse
 */
public class EmailResponse {
    private String id;
    private String message;

    public EmailResponse(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
