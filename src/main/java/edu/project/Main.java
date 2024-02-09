package edu.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;

//Example of Main run configuration: --path src/main/resources/nginx_logs_example.txt --format markdown

public class Main {
    private Main() {}

    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings({"MissingSwitchDefault", "MultipleStringLiterals"})
    public static void main(String[] args) throws IOException {
        List<Path> listOfPath = PathFinder.config(args);
        ZonedDateTime from = null;
        ZonedDateTime to = null;
        Format format = null;
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case ("--from"): {
                    from = ConsoleParser.parseDateTime(args[i + 1]);
                    break;
                }
                case ("--to"): {
                    to = ConsoleParser.parseDateTime(args[i + 1]);
                    break;
                }
                case ("--format"): {
                    format = Format.valueOf(args[i + 1]);
                    break;
                }
            }
        }
        LogAnalyzer analyzer = new LogAnalyzer(listOfPath, from, to);
        String mainInformation = Renderer.mainInformationRender(listOfPath, from, to, analyzer.countOfRequests(),
            analyzer.mediumSizeOfServerAns());
        String resources = Renderer.twoColumnRender(analyzer.determinateMostRequestedResources(),
            "Запрашиваемые ресурсы", "Ресурс", "Количество");
        String code = Renderer.codeRender(analyzer.determinateMostFrequentCode());
        String connection = Renderer.twoColumnRender(analyzer.determinateMostFrequentConnectionType(),
            "Типы подключения", "Тип", "Количество");
        String version = Renderer.twoColumnRender(analyzer.determinateMostFrequentProtocolVersion(),
            "Протоколы подключения", "Протокол", "Количество");
        LOGGER.info(mainInformation);
        LOGGER.info(resources);
        LOGGER.info(code);
        LOGGER.info(connection);
        LOGGER.info(version);
        if (format != null) {
            new FileConvertor(format).doFile(mainInformation, resources, code, connection, version);
        }
    }
}
