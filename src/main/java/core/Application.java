package core;

import com.mashape.unirest.http.Unirest;
import email.service.EmailService;
import email.util.EmailServiceFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    private static final String SSL = "SSL";

    @Value("${email.service}")
    private String emailService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public EmailService emailService() {
        return EmailServiceFactory.build(emailService);
    }

    /**
     * Mentioned a bit in my README, but really would have liked to abstract out any direct usages of Unirest as the
     * HTTP client, so that gringotts isn't so tightly coupled to that HTTP client.  That being said,
     * I spent too much time working on this, and made the compromise that for this small app,
     * I'll just tightly couple us to Unirest.  I had this initialization originally in that Unirest HTTP
     * implementation of the HTTP interface, but moved it to here after I scratched that effort.  Anyways,
     * don't _love_ that this is here.
     */
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

            SSLContext sslcontext = SSLContext.getInstance(SSL);
            sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            CloseableHttpClient httpClient =
                    HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(sslcontext)).build();
            Unirest.setHttpClient(httpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
