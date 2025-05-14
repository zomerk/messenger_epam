import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TemplateEngineParameterizedTest {

    @ParameterizedTest
    @CsvSource({
            "Hello, #{name}!, name, World, Hello, World!",
            "Welcome, #{user}!, user, Guest, Welcome, Guest!",
            "Price: #{price}, price, 10.99, Price: 10.99",
            "#{greeting} #{name}!, greeting, Hello, Hello #{name}!", // Test when placeholder is at the beginning
            "Hello, #{name}!, name, , Hello, !"  // Empty value
    })
    void shouldReplacePlaceholdersWithCsvSource(String template, String key, String value, String expected) {
        Client client = new Client();
        client.setAddresses("test@example.com");
        Map<String, String> values = Map.of(key, value);
        TemplateEngine engine = new TemplateEngine();
        String result = engine.generateMessage(template, values);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "Hello, #{name}!",
            "Welcome, #{user}!",
            "Price: #{price}",
    })
    void shouldThrowExceptionForMissingValuesWithCsvSource(String template) {
        Client client = new Client();
        client.setAddresses("test@example.com");
        Map<String, String> values = Map.of();
        TemplateEngine engine = new TemplateEngine();
        assertThrows(IllegalArgumentException.class, () -> engine.generateMessage(template, values));
    }
}