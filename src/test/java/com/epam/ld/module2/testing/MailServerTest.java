package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.Test;

public class MailServerTest {
    @Test
    void shouldSendMessageUsingTemplateEngineAndMailServer() {
        MailServer mailServer = Mockito.mock(MailServer.class);
        TemplateEngine templateEngine = Mockito.mock(TemplateEngine.class);
        Client client = new Client();
        client.setAddresses("test@example.com");
        Template template = new Template();

        Mockito.when(templateEngine.generateMessage(template, client, java.util.Map.of()))
                .thenReturn("Hello, test@example.com!");

        Messenger messenger = new Messenger(mailServer, templateEngine);
        messenger.sendMessage(client, template);

        Mockito.verify(templateEngine).generateMessage(template, client, java.util.Map.of());
        Mockito.verify(mailServer).send("test@example.com", "Hello, test@example.com!");
    }
}
