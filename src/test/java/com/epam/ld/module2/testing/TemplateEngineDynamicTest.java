import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.ThrowingConsumer;

public class TemplateEngineDynamicTest {

    @TestFactory
    Collection<DynamicTest> shouldReplacePlaceholdersDynamically() {
        Client client = new Client();
        client.setAddresses("test@example.com");
        TemplateEngine engine = new TemplateEngine();

        // Define test cases as a collection of (template, values, expected)
        Object[][] testCases = {
                {"Hello, #{name}!", Map.of("name", "World"), "Hello, World!"},
                {"Welcome, #{user}!", Map.of("user", "Guest"), "Welcome, Guest!"},
                {"Price: #{price}", Map.of("price", "10.99"), "Price: 10.99"},
                {"#{greeting} #{name}!", Map.of("greeting", "Hello"), "Hello #{name}!"},
                {"Hello, #{name}!", Map.of("name", ""), "Hello, !"}
        };

        return Arrays.stream(testCases)
                .map(testCase -> {
                    String template = (String) testCase[0];
                    Map<String, String> values = (Map<String, String>) testCase[1];
                    String expected = (String) testCase[2];
                    String testName = "Replace " + template + " with " + values;

                    ThrowingConsumer<String> testExecutor = (input) -> {
                        String result = engine.generateMessage(template, values);
                        assertEquals(expected, result);
                    };
                    return DynamicTest.dynamicTest(testName, testExecutor);
                })
                .toList();
    }

    @TestFactory
    Stream<DynamicTest> shouldThrowExceptionForMissingValuesDynamically() {
        Client client = new Client();
        client.setAddresses("test@example.com");
        TemplateEngine engine = new TemplateEngine();
        String[] templates = {"Hello, #{name}!", "Welcome, #{user}!", "Price: #{price}"};

        return Arrays.stream(templates)
                .map(template -> {
                    String testName = "Missing value in " + template;
                    ThrowingConsumer<String> testExecutor = (input) -> {
                        Map<String, String> values = Map.of();
                        assertThrows(IllegalArgumentException.class, () -> engine.generateMessage(template, values));
                    };
                    return DynamicTest.dynamicTest(testName, testExecutor);
                });
    }
}