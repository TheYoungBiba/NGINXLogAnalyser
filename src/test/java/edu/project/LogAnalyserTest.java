package edu.project;

import edu.project.LogAnalyzer.AnalyzeCounter;
import edu.project.LogAnalyzer.MostFrequentCode;
import edu.project.LogRecord.ConnectionType;
import edu.project.LogRecord.HttpStatusCode;
import edu.project.LogRecord.ProtocolVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogAnalyserTest {
    static Path pathToTestFile = Path.of("src", "main", "resources", "nginx_logs_example.txt");

    private static Stream<Arguments> provideArgumentsForCountOfRequestsTest() {
        LogAnalyzer testLogAnalyzer1 = new LogAnalyzer(List.of(pathToTestFile), null, null);
        final long countOfRequestsReferent1 = 51462;
        final long mediumSizeOfServerAnsReferent1 = 659509;
        final List<AnalyzeCounter> listOfMostRequestedResourcesReferent1 = List.of(
            new AnalyzeCounter<>("/downloads/product_1", 30285),
            new AnalyzeCounter<>("/downloads/product_2", 21104),
            new AnalyzeCounter<>("/downloads/product_3", 73)
        );
        final List<MostFrequentCode> listOfMostFrequentCodesReferent1 = List.of(
            new MostFrequentCode(HttpStatusCode.NOT_FOUND, 33876),
            new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 13330),
            new MostFrequentCode(HttpStatusCode.OK, 4028)
        );
        final List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent1 = List.of(
            new AnalyzeCounter<>(ConnectionType.GET, 51379),
            new AnalyzeCounter<>(ConnectionType.HEAD, 83)
        );
        final List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent1 = List.of(
            new AnalyzeCounter<>(ProtocolVersion.HTTP_1_1, 51462)
        );
        Arguments testCase1 = Arguments.of(
            testLogAnalyzer1,
            countOfRequestsReferent1,
            mediumSizeOfServerAnsReferent1,
            listOfMostRequestedResourcesReferent1,
            listOfMostFrequentCodesReferent1,
            listOfMostFrequentConnectionTypeReferent1,
            listOfMostFrequentProtocolVersionsReferent1
        );

        LogAnalyzer testLogAnalyzer2 = new LogAnalyzer(List.of(pathToTestFile, pathToTestFile), null, null);
        final long countOfRequestsReferent2 = 102924;
        final long mediumSizeOfServerAnsReferent2 = 659509;
        final List<AnalyzeCounter> listOfMostRequestedResourcesReferent2 = List.of(
            new AnalyzeCounter<>("/downloads/product_1", 60570),
            new AnalyzeCounter<>("/downloads/product_2", 42208),
            new AnalyzeCounter<>("/downloads/product_3", 146)
        );
        final List<MostFrequentCode> listOfMostFrequentCodesReferent2 = List.of(
            new MostFrequentCode(HttpStatusCode.NOT_FOUND, 67752),
            new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 26660),
            new MostFrequentCode(HttpStatusCode.OK, 8056)
        );
        final List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent2 = List.of(
            new AnalyzeCounter<>(ConnectionType.GET, 102758),
            new AnalyzeCounter<>(ConnectionType.HEAD, 166)
        );
        final List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent2 = List.of(
            new AnalyzeCounter<>(ProtocolVersion.HTTP_1_1, 102924)
        );
        Arguments testcase2 = Arguments.of(
            testLogAnalyzer2,
            countOfRequestsReferent2,
            mediumSizeOfServerAnsReferent2,
            listOfMostRequestedResourcesReferent2,
            listOfMostFrequentCodesReferent2,
            listOfMostFrequentConnectionTypeReferent2,
            listOfMostFrequentProtocolVersionsReferent2
        );

        LogAnalyzer testLogAnalyzer3 = new LogAnalyzer(List.of(pathToTestFile),
                ZonedDateTime.parse("2015-05-17T09:00:00+00:00"), null);
        final long countOfRequestsReferent3 = 51347;
        final long mediumSizeOfServerAnsReferent3 = 660986;
        final List<AnalyzeCounter> listOfMostRequestedResourcesReferent3 = List.of(
            new AnalyzeCounter<>("/downloads/product_1", 30216),
            new AnalyzeCounter<>("/downloads/product_2", 21058),
            new AnalyzeCounter<>("/downloads/product_3", 73)
        );
        final List<MostFrequentCode> listOfMostFrequentCodesReferent3 = List.of(
            new MostFrequentCode(HttpStatusCode.NOT_FOUND, 33842),
            new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 13260),
            new MostFrequentCode(HttpStatusCode.OK, 4017)
        );
        final List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent3 = List.of(
            new AnalyzeCounter<>(ConnectionType.GET, 51264),
            new AnalyzeCounter<>(ConnectionType.HEAD, 83)
        );
        final List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent3 = List.of(
            new AnalyzeCounter<>(ProtocolVersion.HTTP_1_1, 51347)
        );
        Arguments testcase3 = Arguments.of(
            testLogAnalyzer3,
            countOfRequestsReferent3,
            mediumSizeOfServerAnsReferent3,
            listOfMostRequestedResourcesReferent3,
            listOfMostFrequentCodesReferent3,
            listOfMostFrequentConnectionTypeReferent3,
            listOfMostFrequentProtocolVersionsReferent3
        );

        LogAnalyzer testLogAnalyzer4 = new LogAnalyzer(List.of(pathToTestFile, pathToTestFile),
            ZonedDateTime.parse("2015-05-17T09:00:00+00:00"), null);
        final long countOfRequestsReferent4 = 102694;
        final long mediumSizeOfServerAnsReferent4 = 660986;
        final List<AnalyzeCounter> listOfMostRequestedResourcesReferent4 = List.of(
            new AnalyzeCounter<>("/downloads/product_1", 60432),
            new AnalyzeCounter<>("/downloads/product_2", 42116),
            new AnalyzeCounter<>("/downloads/product_3", 146)
        );
        final List<MostFrequentCode> listOfMostFrequentCodesReferent4 = List.of(
            new MostFrequentCode(HttpStatusCode.NOT_FOUND, 67684),
            new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 26520),
            new MostFrequentCode(HttpStatusCode.OK, 8034)
        );
        final List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent4 = List.of(
            new AnalyzeCounter<>(ConnectionType.GET, 102528),
            new AnalyzeCounter<>(ConnectionType.HEAD, 166)
        );
        final List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent4 = List.of(
            new AnalyzeCounter<>(ProtocolVersion.HTTP_1_1, 102694)
        );
        Arguments testCase4 = Arguments.of(
            testLogAnalyzer4,
            countOfRequestsReferent4,
            mediumSizeOfServerAnsReferent4,
            listOfMostRequestedResourcesReferent4,
            listOfMostFrequentCodesReferent4,
            listOfMostFrequentConnectionTypeReferent4,
            listOfMostFrequentProtocolVersionsReferent4
        );

        LogAnalyzer testLogAnalyzer5 = new LogAnalyzer(List.of(pathToTestFile), null,
                ZonedDateTime.parse("2015-05-17T09:00:00+00:00"));
        final long countOfRequestsReferent5 = 115;
        final long mediumSizeOfServerAnsReferent5 = 241;
        final List<AnalyzeCounter> listOfMostRequestedResourcesReferent5 = List.of(
            new AnalyzeCounter<>("/downloads/product_1", 69),
            new AnalyzeCounter<>("/downloads/product_2", 46)
        );
        final List<MostFrequentCode> listOfMostFrequentCodesReferent5 = List.of(
            new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 70),
            new MostFrequentCode(HttpStatusCode.NOT_FOUND, 34),
            new MostFrequentCode(HttpStatusCode.OK, 11)
        );
        final List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent5 = List.of(
            new AnalyzeCounter<>(ConnectionType.GET, 115)
        );
        final List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent5 = List.of(
            new AnalyzeCounter<>(ProtocolVersion.HTTP_1_1, 115)
        );
        Arguments testCase5 = Arguments.of(
            testLogAnalyzer5,
            countOfRequestsReferent5,
            mediumSizeOfServerAnsReferent5,
            listOfMostRequestedResourcesReferent5,
            listOfMostFrequentCodesReferent5,
            listOfMostFrequentConnectionTypeReferent5,
            listOfMostFrequentProtocolVersionsReferent5
        );

        LogAnalyzer testLogAnalyzer6 = new LogAnalyzer(List.of(pathToTestFile, pathToTestFile),
            null, ZonedDateTime.parse("2015-05-17T09:00:00+00:00"));
        final long countOfRequestsReferent6 = 230;
        final long mediumSizeOfServerAnsReferent6 = 241;
        final List<AnalyzeCounter> listOfMostRequestedResourcesReferent6 = List.of(
            new AnalyzeCounter<>("/downloads/product_1", 138),
            new AnalyzeCounter<>("/downloads/product_2", 92)
        );
        final List<MostFrequentCode> listOfMostFrequentCodesReferent6 = List.of(
            new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 140),
            new MostFrequentCode(HttpStatusCode.NOT_FOUND, 68),
            new MostFrequentCode(HttpStatusCode.OK, 22)
        );
        final List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent6 = List.of(
            new AnalyzeCounter<>(ConnectionType.GET, 230)
        );
        final List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent6 = List.of(
            new AnalyzeCounter<>(ProtocolVersion.HTTP_1_1, 230)
        );
        Arguments testCase6 = Arguments.of(
            testLogAnalyzer6,
            countOfRequestsReferent6,
            mediumSizeOfServerAnsReferent6,
            listOfMostRequestedResourcesReferent6,
            listOfMostFrequentCodesReferent6,
            listOfMostFrequentConnectionTypeReferent6,
            listOfMostFrequentProtocolVersionsReferent6
        );

        LogAnalyzer testLogAnalyzer7 = new LogAnalyzer(List.of(pathToTestFile),
                ZonedDateTime.parse("2015-05-17T09:00:00+00:00"),
                ZonedDateTime.parse("2015-05-17T10:00:00+00:00"));
        final long countOfRequestsReferent7 = 121;
        final long mediumSizeOfServerAnsReferent7 = 165;
        final List<AnalyzeCounter> listOfMostRequestedResourcesReferent7 = List.of(
            new AnalyzeCounter<>("/downloads/product_1", 63),
            new AnalyzeCounter<>("/downloads/product_2", 58)
        );
        final List<MostFrequentCode> listOfMostFrequentCodesReferent7 = List.of(
            new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 64),
            new MostFrequentCode(HttpStatusCode.NOT_FOUND, 54),
            new MostFrequentCode(HttpStatusCode.OK, 3)
        );
        final List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent7 = List.of(
            new AnalyzeCounter<>(ConnectionType.GET, 120),
            new AnalyzeCounter<>(ConnectionType.HEAD, 1)
        );
        final List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent7 = List.of(
            new AnalyzeCounter<>(ProtocolVersion.HTTP_1_1, 121)
        );
        Arguments testCase7 = Arguments.of(
            testLogAnalyzer7,
            countOfRequestsReferent7,
            mediumSizeOfServerAnsReferent7,
            listOfMostRequestedResourcesReferent7,
            listOfMostFrequentCodesReferent7,
            listOfMostFrequentConnectionTypeReferent7,
            listOfMostFrequentProtocolVersionsReferent7
        );

        LogAnalyzer testLogAnalyzer8 = new LogAnalyzer(List.of(pathToTestFile, pathToTestFile),
            ZonedDateTime.parse("2015-05-17T09:00:00+00:00"),
            ZonedDateTime.parse("2015-05-17T10:00:00+00:00"));
        final long countOfRequestsReferent8 = 242;
        final long mediumSizeOfServerAnsReferent8 = 165;
        final List<AnalyzeCounter> listOfMostRequestedResourcesReferent8 = List.of(
            new AnalyzeCounter<>("/downloads/product_1", 126),
            new AnalyzeCounter<>("/downloads/product_2", 116)
        );
        final List<MostFrequentCode> listOfMostFrequentCodesReferent8 = List.of(
            new MostFrequentCode(HttpStatusCode.NOT_MODIFIED, 128),
            new MostFrequentCode(HttpStatusCode.NOT_FOUND, 108),
            new MostFrequentCode(HttpStatusCode.OK, 6)
        );
        final List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent8 = List.of(
            new AnalyzeCounter<>(ConnectionType.GET, 240),
            new AnalyzeCounter<>(ConnectionType.HEAD, 2)
        );
        final List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent8 = List.of(
            new AnalyzeCounter<>(ProtocolVersion.HTTP_1_1, 242)
        );
        Arguments testCase8 = Arguments.of(
            testLogAnalyzer8,
            countOfRequestsReferent8,
            mediumSizeOfServerAnsReferent8,
            listOfMostRequestedResourcesReferent8,
            listOfMostFrequentCodesReferent8,
            listOfMostFrequentConnectionTypeReferent8,
            listOfMostFrequentProtocolVersionsReferent8
        );
        return Stream.of(testCase1, testcase2, testcase3, testCase4, testCase5, testCase6, testCase7, testCase8);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCountOfRequestsTest")
    void countOfRequestsTest(
        LogAnalyzer testAnalyzer,
        long countOfRequestsReferent,
        long mediumSizeOfServerAnsReferent,
        List<AnalyzeCounter> listOfMostRequestedResourcesReferent,
        List<MostFrequentCode> listOfMostFrequentCodesReferent,
        List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent,
        List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent
    ) throws IOException {
        assertEquals(countOfRequestsReferent, testAnalyzer.countOfRequests());
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCountOfRequestsTest")
    void mediumSizeOfServerAnsTest(
        LogAnalyzer testAnalyzer,
        long countOfRequestsReferent,
        long mediumSizeOfServerAnsReferent,
        List<AnalyzeCounter> listOfMostRequestedResourcesReferent,
        List<MostFrequentCode> listOfMostFrequentCodesReferent,
        List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent,
        List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent
    ) throws IOException {
        assertEquals(mediumSizeOfServerAnsReferent, testAnalyzer.mediumSizeOfServerAns());
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCountOfRequestsTest")
    void determinateMostRequestedResourcesTest(
        LogAnalyzer testAnalyzer,
        long countOfRequestsReferent,
        long mediumSizeOfServerAnsReferent,
        List<AnalyzeCounter> listOfMostRequestedResourcesReferent,
        List<MostFrequentCode> listOfMostFrequentCodesReferent,
        List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent,
        List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent
    ) throws IOException {
        assertEquals(listOfMostRequestedResourcesReferent, testAnalyzer.determinateMostRequestedResources());
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCountOfRequestsTest")
    void determinateMostFrequentCodeTest(
        LogAnalyzer testAnalyzer,
        long countOfRequestsReferent,
        long mediumSizeOfServerAnsReferent,
        List<AnalyzeCounter> listOfMostRequestedResourcesReferent,
        List<MostFrequentCode> listOfMostFrequentCodesReferent,
        List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent,
        List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent
    ) throws IOException {
        assertEquals(listOfMostFrequentCodesReferent, testAnalyzer.determinateMostFrequentCode());
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCountOfRequestsTest")
    void determinateMostFrequentConnectionTypeTest(
        LogAnalyzer testAnalyzer,
        long countOfRequestsReferent,
        long mediumSizeOfServerAnsReferent,
        List<AnalyzeCounter> listOfMostRequestedResourcesReferent,
        List<MostFrequentCode> listOfMostFrequentCodesReferent,
        List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent,
        List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent
    ) throws IOException {
        assertEquals(listOfMostFrequentConnectionTypeReferent, testAnalyzer.determinateMostFrequentConnectionType());
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCountOfRequestsTest")
    void determinateMostFrequentProtocolVersionTest(
        LogAnalyzer testAnalyzer,
        long countOfRequestsReferent,
        long mediumSizeOfServerAnsReferent,
        List<AnalyzeCounter> listOfMostRequestedResourcesReferent,
        List<MostFrequentCode> listOfMostFrequentCodesReferent,
        List<AnalyzeCounter> listOfMostFrequentConnectionTypeReferent,
        List<AnalyzeCounter> listOfMostFrequentProtocolVersionsReferent
    ) throws IOException {
        assertEquals(listOfMostFrequentProtocolVersionsReferent, testAnalyzer.determinateMostFrequentProtocolVersion());
    }
}
