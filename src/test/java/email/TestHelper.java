package email;

import email.pojo.Email;
import email.util.JacksonUtil;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO - JJW
 * @author jjwyse
 */
public class TestHelper {
    public static void sendEmailAndValidate(MockMvc mvc) throws Exception {
        Email email = new Email(
                "joshua.wyse@gmail.com",
                "Frank Ricard",
                "joshua.wyse@gmail.com",
                "Joseph Pulaski",
                "subject",
                "body");
        mvc.perform(MockMvcRequestBuilders.post("/email")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.toString(email)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("message")));
    }
}
