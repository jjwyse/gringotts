package email;

import email.pojo.Email;
import email.util.JacksonUtil;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test helper, which provides a static interface with useful functions that are used in multiple tests
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
        mvc.perform(MockMvcRequestBuilders.post("/v1/email")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.toString(email)))
                .andExpect(status().isOk());
    }
}
