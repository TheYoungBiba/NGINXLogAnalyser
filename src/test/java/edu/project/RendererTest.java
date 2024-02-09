package edu.project;

import edu.project.LogAnalyzer.AnalyzeCounter;
import edu.project.LogAnalyzer.MostFrequentCode;
import edu.project.LogRecord.HttpStatusCode;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RendererTest {
    @Test
    void mainInformationRenderTest() {
        String referent =
            "#### Общая информация\n\n" +
            "|Метрика               |Значение                              |\n" +
            "|----------------------|--------------------------------------|\n" +
            "|Файл(-ы)              |nginx_test_logs, nginx_test_logs      |\n" +
            "|Начальная дата        |2015-05-17T00:00+03:00[Europe/Moscow] |\n" +
            "|Конечная дата         |2015-06-01T00:00+03:00[Europe/Moscow] |\n" +
            "|Количество запросов   |83340                                 |\n" +
            "|Средний размер ответа |667133b                               |\n";
        assertEquals(referent, Renderer.mainInformationRender(
            List.of(Path.of("src", "main", "resources", "nginx_test_logs"),
                Path.of("src", "main", "resources", "nginx_test_logs")),
            ZonedDateTime.parse("2015-05-17T00:00+03:00[Europe/Moscow]"),
            ZonedDateTime.parse("2015-06-01T00:00+03:00[Europe/Moscow]"),
            83340,
            667133
            )
        );
    }

    @Test
    void twoColumnRenderTest() {
        String referent =
            "#### Запрашиваемые ресурсы\n\n" +
            "|Ресурс               |Количество |\n" +
            "|---------------------|-----------|\n" +
            "|/downloads/product_1 |47968      |\n" +
            "|/downloads/product_2 |35256      |\n" +
            "|/downloads/product_3 |116        |\n";
        assertEquals(referent, Renderer.twoColumnRender(
            List.of(new AnalyzeCounter<>("/downloads/product_1", 47968),
                new AnalyzeCounter<>("/downloads/product_2", 35256),
                new AnalyzeCounter<>("/downloads/product_3", 116)),
            "Запрашиваемые ресурсы",
            "Ресурс",
            "Количество"
            )
        );
    }

    @Test
    void codeRenderTest() {
        String referent =
            "#### Коды ответа\n\n" +
            "|Код |Имя          |Количество |\n" +
            "|----|-------------|-----------|\n" +
            "|404 |NOT_FOUND    |54580      |\n" +
            "|304 |NOT_MODIFIED |21714      |\n" +
            "|200 |OK           |6690       |\n";
        assertEquals(referent, Renderer.codeRender(
            List.of(new MostFrequentCode(HttpStatusCode.NOT_FOUND, 54580),
                new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 21714),
                new MostFrequentCode(HttpStatusCode.OK, 6690))
        ));
    }
}
