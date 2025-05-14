package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TemplateEngineTest {

    @Test
    void shouldReplaceSimplePlaceholder() {
        String template = "Hello, #{name}!";
        Client client = new Client();
        client.setAddresses("test@example.com");
        java.util.Map<String, String> values = java.util.Map.of("name", "World");
        TemplateEngine engine = new TemplateEngine();
        String result = engine.generateMessage(new Template(), client, values);
        assertEquals("Hello, World!", result);
    }

    @Test
    void shouldThrowExceptionForMissingPlaceholderValue() {
        String template = "Hello, #{name}!";
        Client client = new Client();
        client.setAddresses("test@example.com");
        java.util.Map<String, String> values = java.util.Map.of();
        TemplateEngine engine = new TemplateEngine();
        assertThrows(IllegalArgumentException.class, () -> engine.generateMessage(new Template(), client, values));
    }

    @Test
    void shouldIgnoreExtraRuntimeValues() {
        String template = "Hello, #{name}!";
        Client client = new Client();
        client.setAddresses("test@example.com");
        java.util.Map<String, String> values = java.util.Map.of("name", "World", "extra", "value");
        TemplateEngine engine = new TemplateEngine();
        String result = engine.generateMessage(new Template(), client, values);
        assertEquals("Hello, World!", result);
    }
    @Test
    void shouldHandleDifferentPlaceholderSyntax() {
        String template = "Some text: #{value}";
        Client client = new Client();
        client.setAddresses("test@example.com");
        java.util.Map<String, String> values = java.util.Map.of("tag", "replacement");
        TemplateEngine engine = new TemplateEngine();
        String result = engine.generateMessage(new Template(), client, values);
        assertEquals("Some text: #{value}", result); // Expect no replacement as keys don't match
    }
    @Test
    void shouldHandleDifferentPlaceholderSyntax() {
        String template = "Some text: #{value}";
        Client client = new Client();
        client.setAddresses("test@example.com");
        java.util.Map<String, String> values = java.util.Map.of("value", "#{tag}");
        TemplateEngine engine = new TemplateEngine();
        String result = engine.generateMessage(new Template(), client, values);
        assertEquals("Some text: #{tag}", result);
    }
    @Test
    void shouldSupportLatin1Characters() {
        String template = "Bonjour, #{nom} à éàçüö!";
        Client client = new Client();
        client.setAddresses("test@example.com");
        java.util.Map<String, String> values = java.util.Map.of("nom", "Björn Ström");
        TemplateEngine engine = new TemplateEngine();
        String result = engine.generateMessage(new Template(), client, values);
        assertEquals("Bonjour, Björn Ström à éàçüö!", result);
    }
}