package de.obsidiancloud.common.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ConfigTest {
    @Test
    public void testLoadJson(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.json");
        Files.writeString(
                file,
                """
                {
                    "string": "value",
                    "integer": 1,
                    "double": 2.5,
                    "boolean": true,
                    "list": [
                        "value",
                        5,
                        false
                    ],
                    "section": {
                        "string": "value",
                        "int": 5,
                        "boolean": false
                    }
                }""");
        Config config = new Config(file, Config.Type.JSON);

        Assertions.assertEquals("value", config.getString("string"));
        Assertions.assertEquals(1, config.getInt("integer"));
        Assertions.assertEquals(2.5, config.getDouble("double"));
        Assertions.assertTrue(config.getBoolean("boolean"));
        Assertions.assertEquals(List.of("value", 5.0, false), config.getList("list"));
        Assertions.assertNotNull(config.getSection("section"));
        Assertions.assertEquals(
                "value", Objects.requireNonNull(config.getSection("section")).getString("string"));
        Assertions.assertEquals(
                5, Objects.requireNonNull(config.getSection("section")).getInt("int"));
        Assertions.assertFalse(
                Objects.requireNonNull(config.getSection("section")).getBoolean("boolean"));
    }

    @Test
    public void testLoadYaml(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.yml");
        Files.writeString(
                file,
                """
                string: value
                integer: 1
                double: 2.5
                boolean: true
                list:
                - value
                - 5
                - false
                section:
                  string: value
                  int: 5
                  boolean: false
                """);
        Config config = new Config(file, Config.Type.YAML);

        Assertions.assertEquals("value", config.getString("string"));
        Assertions.assertEquals(1, config.getInt("integer"));
        Assertions.assertEquals(2.5, config.getDouble("double"));
        Assertions.assertTrue(config.getBoolean("boolean"));
        Assertions.assertEquals(List.of("value", 5, false), config.getList("list"));
        Assertions.assertNotNull(config.getSection("section"));
        Assertions.assertEquals(
                "value", Objects.requireNonNull(config.getSection("section")).getString("string"));
        Assertions.assertEquals(
                5, Objects.requireNonNull(config.getSection("section")).getInt("int"));
        Assertions.assertFalse(
                Objects.requireNonNull(config.getSection("section")).getBoolean("boolean"));
    }
}
