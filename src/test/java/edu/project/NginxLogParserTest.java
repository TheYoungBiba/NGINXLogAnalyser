package edu.project;

import edu.project.LogRecord.Request;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NginxLogParserTest {
    private static Stream<Arguments> provideArgumentsForParseLineStandardTest() throws UnknownHostException {
        String line1 = "46.4.66.76 - - [17/May/2015:08:05:38 +0000] \"GET /downloads/product_1 HTTP/1.1\" " +
            "304 0 \"-\" \"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\"";
        LogRecord referent1 = new LogRecord(
            InetAddress.getByName("46.4.66.76"),
            "-",
            ZonedDateTime.parse("2015-05-17T08:05:38Z"),
            new Request(
                LogRecord.ConnectionType.GET,
                "/downloads/product_1",
                LogRecord.ProtocolVersion.HTTP_1_1
            ),
            304,
            0,
            "-",
            "Debian APT-HTTP/1.3 (1.0.1ubuntu2)"
        );
        Arguments testCase1 = Arguments.of(line1, referent1);

        String line2 = "2001:4800:7815:102:8bee:6e66:ff05:215f - - [04/Jun/2015:03:06:36 +0000] "
            + "\"POST /product_1 HTTP/2.0\" 200 85619205 \"-\" "
            + "\"Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)\"";
        LogRecord referent2 = new LogRecord(
            InetAddress.getByName("2001:4800:7815:102:8bee:6e66:ff05:215f"),
            "-",
            ZonedDateTime.parse("2015-06-04T03:06:36Z"),
            new Request(
                LogRecord.ConnectionType.POST,
                "/product_1",
                LogRecord.ProtocolVersion.HTTP_2_0
            ),
            200,
            85619205,
            "-",
            "Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)"
        );
        Arguments testCase2 = Arguments.of(line2, referent2);
        return Stream.of(testCase1, testCase2);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForParseLineStandardTest")
    void parseLineStandardTest(String testCase, LogRecord referent) {
        assertEquals(referent, new NginxLogParser().parseLine(testCase));
    }

    private static Stream<Arguments> provideArgumentsForParseLineExceptionTest() throws UnknownHostException {
        String line1 = "464.6676 - - [17/May/2015:08:05:38 +0000] \"GET /downloads/product_1 HTTP/1.1\" " +
            "304 0 \"-\" \"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\"";
        Arguments testCase1 = Arguments.of(line1, "Invalid line.");

        String line2 = "2001:6e66:ff05:215f - - [04/Jun/2015:03:06:36 +0000] "
            + "\"POST /product_1 HTTP/2.0\" 200 85619205 \"-\" "
            + "\"Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)\"";
        Arguments testCase2 = Arguments.of(line2, "Invalid line.");

        String line4 = "2001:4800:7815:102:8bee:6e66:ff05:215f - - [60/Jzn/2015:30:06:36 +0000] "
            + "\"POST /product_1 HTTP/2.0\" 200 85619205 \"-\" "
            + "\"Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)\"";
        Arguments testCase4 = Arguments.of(line4, "java.lang.RuntimeException: Invalid month");

        String line5 = "2001:4800:7815:102:8bee:6e66:ff05:215f - - [04/Jun/2015:03:06:36 +0000] "
            + "\"PST /product_1 HTTP/2.0\" 200 85619205 \"-\" "
            + "\"Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)\"";
        Arguments testCase5 = Arguments.of(line5, "Invalid line.");

        String line6 = "2001:4800:7815:102:8bee:6e66:ff05:215f - - [04/Jun/2015:03:06:36 +0000] "
            + "\"POST HTTP/2.0\" 200 85619205 \"-\" "
            + "\"Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)\"";
        Arguments testCase6 = Arguments.of(line6, "Invalid line.");

        String line7 = "2001:4800:7815:102:8bee:6e66:ff05:215f - - [04/Jun/2015:03:06:36 +0000] "
            + "\"POST /product_1 HTTP/5.0\" 200 85619205 \"-\" "
            + "\"Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)\"";
        Arguments testCase7 = Arguments.of(line7,
            "java.lang.IllegalArgumentException: No enum constant edu.project.LogRecord.ProtocolVersion.HTTP_5_0");

        String line8 = "2001:4800:7815:102:8bee:6e66:ff05:215f - - [04/Jun/2015:03:06:36 +0000] "
            + "\"POST /product_1 HTTP/2.0\" 2000 85619205 \"-\" "
            + "\"Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)\"";
        Arguments testCase8 = Arguments.of(line8, "Invalid line.");

        String line9 = "2001:4800:7815:102:8bee:6e66:ff05:215f - - [04/Jun/2015:03:06:36 +0000] "
            + "\"POST /product_1 HTTP/2.0\" 200 \"-\" "
            + "\"Chef Client/12.0.3 (ruby-2.1.4-p265; ohai-8.0.1; x86_64-linux; +http://opscode.com)\"";
        Arguments testCase9 = Arguments.of(line9, "Invalid line.");

        return Stream.of(testCase1, testCase2, testCase4,
            testCase5, testCase6, testCase7, testCase8, testCase9);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForParseLineExceptionTest")
    void parseLineExceptionTest(String testCase, String exceptionMessage) {
        Exception e = assertThrows(RuntimeException.class, () -> {
            new NginxLogParser().parseLine(testCase);
        });
        assertEquals(exceptionMessage, e.getMessage());
    }
}
