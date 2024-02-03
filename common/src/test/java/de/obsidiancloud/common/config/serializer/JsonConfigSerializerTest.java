package de.obsidiancloud.common.config.serializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonConfigSerializerTest {
    @Test
    public void testSerialize() {
        JsonConfigSerializer serializer = new JsonConfigSerializer();
        String expected =
                """
                {
                  "boolean": true,
                  "string": "value",
                  "array": [
                    "value",
                    5,
                    false
                  ],
                  "double": 2.5,
                  "integer": 1,
                  "float": 2.5,
                  "list": [
                    "value",
                    5,
                    false
                  ]
                }""";
        Map<String, Object> map = new HashMap<>();
        map.put("boolean", true);
        map.put("string", "value");
        map.put("array", new Object[] {"value", 5, false});
        map.put("double", 2.5);
        map.put("integer", 1);
        map.put("float", 2.5);
        map.put("list", List.of("value", 5, false));
        String actual = serializer.serialize(map);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDeserialize() {
        JsonConfigSerializer serializer = new JsonConfigSerializer();
        Map<String, Object> expected = new HashMap<>();
        expected.put("boolean", true);
        expected.put("string", "value");
        expected.put("double", 2.5);
        expected.put("integer", 1.0);
        expected.put("float", 2.5);
        expected.put("list", List.of("value", 5.0, false));
        String str =
                """
                {
                  "boolean": true,
                  "string": "value",
                  "double": 2.5,
                  "integer": 1,
                  "float": 2.5,
                  "list": [
                    "value",
                    5,
                    false
                  ]
                }""";
        Map<String, Object> actual = serializer.deserialize(str);

        Assertions.assertEquals(expected, actual);
    }
}
