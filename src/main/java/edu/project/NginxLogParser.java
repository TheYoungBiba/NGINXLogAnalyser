package edu.project;

import java.net.InetAddress;
import java.time.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NginxLogParser implements LogParser {
    public NginxLogParser() {}

    private InetAddress parseIP(String ip) {
        try {
            return InetAddress.getByName(ip);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Month getMonth(String month) {
        return switch (month) {
            case ("Jan") -> Month.JANUARY;
            case ("Feb") -> Month.FEBRUARY;
            case ("Mar") -> Month.MARCH;
            case ("Apr") -> Month.APRIL;
            case ("May") -> Month.MAY;
            case ("Jun") -> Month.JUNE;
            case ("Jul") -> Month.JULY;
            case ("Aug") -> Month.AUGUST;
            case ("Sep") -> Month.SEPTEMBER;
            case ("Oct") -> Month.OCTOBER;
            case ("Nov") -> Month.NOVEMBER;
            case ("Dec") -> Month.DECEMBER;
            default -> throw new RuntimeException("Invalid month");
        };
    }

    private ZonedDateTime parseDate(String dayOfMonth, String month, String year, String time, String timeZone) {
        try {
            return ZonedDateTime.of(
                LocalDate.of(Integer.parseInt(year), getMonth(month), Integer.parseInt(dayOfMonth)),
                LocalTime.parse(time),
                ZoneId.of(timeZone)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("parameterassignment")
    private LogRecord.Request parseRequest(String type, String resource, String protocol) {
        try {
            protocol = protocol.replace('/', '_').replace('.', '_');
            return new LogRecord.Request(
                LogRecord.ConnectionType.valueOf(type),
                resource,
                LogRecord.ProtocolVersion.valueOf(protocol)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LogRecord parseLine(String line) {
        Pattern pattern = Pattern.compile(
            "^(?<IP>((?:\\d{1,3}\\.){3}\\d{1,3})|((?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4})|"
                + "(((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?))) - "
                + "(?<login>\\S+) \\[(?<dateTime>(?<date>(?<dayOfMonth>\\d{2})/"
                + "(?<month>[A-Z][a-z]{2})/(?<year>\\d{4})):"
                + "(?<time>(?<hours>\\d{2}):(?<mins>\\d{2}):(?<secs>\\d{2})) (?<timeZone>\\+\\d{4}))\\] "
                + "\"(?<request>(?<type>GET|POST|PUT|DELETE|HEAD|CONNECT|PRI) (?<resource>\\S+) "
                + "(?<protocol>HTTP/\\d\\.\\d))\" (?<code>\\d{3}) (?<byteSize>\\d+) \"(?<referer>\\S+)\" "
                + "\"(?<userAgent>.+)\"$");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new RuntimeException("Invalid line.");
        }
        String ip = matcher.group("IP");
        String login = matcher.group("login");
        String dayOfMonth = matcher.group("dayOfMonth");
        String month = matcher.group("month");
        String year = matcher.group("year");
        String time = matcher.group("time");
        String timeZone = matcher.group("timeZone");
        String type = matcher.group("type");
        String resource = matcher.group("resource");
        String protocol = matcher.group("protocol");
        int code = Integer.parseInt(matcher.group("code"));
        int byteSize = Integer.parseInt(matcher.group("byteSize"));
        String referer = matcher.group("referer");
        String userAgent = matcher.group("userAgent");
        return new LogRecord(
            parseIP(ip),
            login,
            parseDate(dayOfMonth, month, year, time, timeZone),
            parseRequest(type, resource, protocol),
            code,
            byteSize,
            referer,
            userAgent
        );
    }
}
