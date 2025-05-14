package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TemplateEngine {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("#\\{(.*?)\\}");


    public String generateMessage(Template template, Client client, Map<String, String> values) {
        String templateContent = "Hello, #{name}!";
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(templateContent);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String placeholder = matcher.group(0);
            String key = matcher.group(1);
            String value = values.get(key);
            if (value != null) {
                matcher.appendReplacement(buffer, value);
            } else {
                throw new IllegalArgumentException("Missing value for placeholder: " + key);
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
