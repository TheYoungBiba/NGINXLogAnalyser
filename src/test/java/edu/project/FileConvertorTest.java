package edu.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileConvertorTest {
    @ParameterizedTest
    @CsvSource("markdown, adoc")
    void doFileTest(String testCase) throws IOException {
        Path referent;
        if (testCase.equals("markdown")) {
            referent = Path.of("src", "main", "resources", "report.md");
        } else {
            referent = Path.of("src", "main", "resources", "report.adoc");
        }
        List<Path> pathList = List.of(Path.of("src", "main", "resources", "nginx_logs_example.txt"));
        LogAnalyzer logAnalyzer = new LogAnalyzer(pathList, null, null);
        String mainInformation = Renderer.mainInformationRender(pathList, null, null,
            logAnalyzer.countOfRequests(), logAnalyzer.mediumSizeOfServerAns());
        String resources = Renderer.twoColumnRender(logAnalyzer.determinateMostRequestedResources(),
            "Запрашиваемые ресурсы", "Ресурс", "Количество");
        String code = Renderer.codeRender(logAnalyzer.determinateMostFrequentCode());
        String connection = Renderer.twoColumnRender(logAnalyzer.determinateMostFrequentConnectionType(),
            "Типы подключения", "Тип", "Количество");
        String version = Renderer.twoColumnRender(logAnalyzer.determinateMostFrequentProtocolVersion(),
            "Протоколы подключения", "Протокол", "Количество");
        new FileConvertor(Format.valueOf(testCase)).doFile(mainInformation, resources, code, connection, version);
        boolean flag = Files.exists(referent);
        Files.delete(referent);
        assertTrue(flag);
    }

    @Test
    void doFileExceptionTest() throws IOException {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            List<Path> pathList = List.of(Path.of("src", "main", "resources", "nginx_logs_example.txt"));
            LogAnalyzer logAnalyzer = new LogAnalyzer(pathList, null, null);
            String mainInformation = Renderer.mainInformationRender(pathList, null, null,
                logAnalyzer.countOfRequests(), logAnalyzer.mediumSizeOfServerAns());
            String resources = Renderer.twoColumnRender(logAnalyzer.determinateMostRequestedResources(),
                "Запрашиваемые ресурсы", "Ресурс", "Количество");
            String code = Renderer.codeRender(logAnalyzer.determinateMostFrequentCode());
            String connection = Renderer.twoColumnRender(logAnalyzer.determinateMostFrequentConnectionType(),
                "Типы подключения", "Тип", "Количество");
            String version = Renderer.twoColumnRender(logAnalyzer.determinateMostFrequentProtocolVersion(),
                "Протоколы подключения", "Протокол", "Количество");
            new FileConvertor(Format.valueOf("qwerty")).doFile(mainInformation, resources, code, connection, version);
        });
        assertEquals("No enum constant edu.project.Format.qwerty", e.getMessage());
    }
}
