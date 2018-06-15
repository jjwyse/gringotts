package email.pojo;

/**
 * Email POJO, which defines all of the fields that make up a POJO in gringotts
 * @author jjwyse
 */
public class Email {
    private String to;
    private String to_name;
    private String from;
    private String from_name;
    private String subject;
    private String body;

    /* Required for Jackson */
    public Email() {}

    public Email(String to, String to_name, String from, String from_name, String subject, String body) {
        this.to = to;
        this.to_name = to_name;
        this.from = from;
        this.from_name = from_name;
        this.subject = subject;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public String getTo_name() {
        return to_name;
    }

    public String getFrom() {
        return from;
    }

    public String getFrom_name() {
        return from_name;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
