package edu.project;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathFinderTest {

    @Test
    void configTest() throws IOException {
        String[] testCase = {
                "--path",
                "src/main/resources/nginx_logs_example.txt",
                "src/main/resources/nginx_logs_example.txt",
                "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
                "--from"
        };
        List<Path> referent = List.of(
                Path.of("src", "main", "resources", "nginx_logs_example.txt"),
                Path.of("src", "main", "resources", "nginx_logs_example.txt"),
                Path.of("src", "main", "resources", "nginx_logs")
        );
        assertEquals(referent, PathFinder.config(testCase));
    }

    @AfterAll
    public static void deleteRemoteFile() throws IOException {
        Path path = Path.of("src", "main", "resources", "nginx_logs");
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
