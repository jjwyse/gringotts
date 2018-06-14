package core;

import com.mashape.unirest.http.Unirest;
import email.service.EmailService;
import email.service.impl.MailgunServiceImpl;
import email.service.impl.MandrillServiceImpl;
import email.service.impl.SendgridServiceImpl;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@SpringBootApplication
@ComponentScan({"email"})
public class Application {
    @Value("${email.service:mandrill}")
    private String emailService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public EmailService emailService(ApplicationContext ctx) {
        switch (emailService) {
            case "mailgun":
                return new MailgunServiceImpl();
            case "sendgrid":
                return new SendgridServiceImpl();
            case "mandrill":
            default:
                return new MandrillServiceImpl();
        }
    }

    static {
        try {
            // stolen from: https://github.com/Kong/unirest-java/issues/70 to just trust all certs for now
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            Unirest.setHttpClient(httpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
