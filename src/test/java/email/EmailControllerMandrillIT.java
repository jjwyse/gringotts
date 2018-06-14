package email;

import core.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"email.service=mandrill"})
public class EmailControllerMandrillIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testSendEmailViaMandrill() throws Exception {
        TestHelper.sendEmailAndValidate(mvc);
    }
}