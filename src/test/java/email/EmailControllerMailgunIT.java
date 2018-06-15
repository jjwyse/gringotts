package email;

import core.Application;
import email.pojo.Email;
import email.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"email.service=mailgun"})
public class EmailControllerMailgunIT {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testSendEmailViaMailgun() throws Exception {
        TestHelper.sendEmailAndValidate(mvc);
    }

    @Test
    public void testSendEmailWithMissingFields() throws Exception {
        Email email = new Email(
                null,
                "Frank Ricard",
                "joshua.wyse@gmail.com",
                "Joseph Pulaski",
                "subject",
                "body");
        mvc.perform(MockMvcRequestBuilders.post("/v1/email")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.toString(email)))
                .andExpect(status().isBadRequest());
    }
}