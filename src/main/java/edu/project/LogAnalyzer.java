package edu.project;

import edu.project.LogRecord.ConnectionType;
import edu.project.LogRecord.HttpStatusCode;
import edu.project.LogRecord.ProtocolVersion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class LogAnalyzer {
    private final List<Path> paths;

    private final ZonedDateTime from;

    private final ZonedDateTime to;

    public LogAnalyzer(List<Path> paths, ZonedDateTime from, ZonedDateTime to) {
        this.paths = paths;
        this.from = from;
        this.to = to;
    }

    public long countOfRequests() throws IOException {
        long count = 0;
        for (Path path: paths) {
            count += getStreamOfLogs(path).count();
        }
        return count;
    }

    public long mediumSizeOfServerAns() throws IOException {
        long count = 0;
        long size = 0;
        for (Path path: paths) {
            count += getStreamOfLogs(path).count();
            size = getStreamOfLogs(path).reduce(size, (aLong, logRecord) -> aLong + logRecord.byteSize(), Long::sum);
        }
        return size / count;
    }

    public List<AnalyzeCounter> determinateMostRequestedResources() throws IOException {
        HashMap<String, Long> resources = new HashMap<>();
        for (Path path: paths) {
            getStreamOfLogs(path)
                .forEach(logRecord -> {
                    String resource = logRecord.request().resource();
                    if (!resources.containsKey(resource)) {
                        resources.put(resource, 1L);
                    } else {
                        resources.put(resource, resources.get(resource) + 1);
                    }
                });
        }
        return toSortedLimitList(resources);
    }

    @SuppressWarnings("MagicNumber")
    public List<MostFrequentCode> determinateMostFrequentCode() throws IOException {
        HashMap<Integer, Long> codes = new HashMap<>();
        for (Path path: paths) {
            getStreamOfLogs(path)
                .forEach(logRecord -> {
                    int code = logRecord.httpStatusCode();
                    if (!codes.containsKey(code)) {
                        codes.put(code, 1L);
                    } else {
                        codes.put(code, codes.get(code) + 1);
                    }
                });
        }
        return codes.entrySet().stream()
            .sorted(new LogCmp<Integer>().reversed())
            .limit(3)
            .map(integerLongEntry -> new MostFrequentCode(toEnumVal(integerLongEntry.getKey()),
                integerLongEntry.getValue()))
            .toList();
    }

    public List<AnalyzeCounter> determinateMostFrequentConnectionType() throws IOException {
        HashMap<ConnectionType, Long> connections = new HashMap<>();
        for (Path path: paths) {
            getStreamOfLogs(path)
                .forEach(logRecord -> {
                    ConnectionType connection = logRecord.request().connectionType();
                    if (!connections.containsKey(connection)) {
                        connections.put(connection, 1L);
                    } else {
                        connections.put(connection, connections.get(connection) + 1);
                    }
                });
        }
        return toSortedLimitList(connections);
    }

    public List<AnalyzeCounter> determinateMostFrequentProtocolVersion() throws IOException {
        Map<ProtocolVersion, Long> versions = new HashMap<>();
        for (Path path: paths) {
            getStreamOfLogs(path)
                .forEach(logRecord -> {
                    ProtocolVersion version = logRecord.request().version();
                    if (!versions.containsKey(version)) {
                        versions.put(version, 1L);
                    } else {
                        versions.put(version, versions.get(version) + 1);
                    }
                });
        }
        return toSortedLimitList(versions);
    }

    private Stream<LogRecord> getStreamOfLogs(Path path) throws IOException {
        LogParser logParser = new NginxLogParser();
        List<String> listOfLines = Files.readAllLines(path);
        if (from == null && to == null) {
            return listOfLines.stream().map(logParser::parseLine);
        }
        if (to == null) {
            return listOfLines.stream()
                .map(logParser::parseLine)
                .filter(logRecord -> logRecord.dateOfRequest().isAfter(from));
        }
        if (from == null) {
            return listOfLines.stream()
                .map(logParser::parseLine)
                .filter(logRecord -> logRecord.dateOfRequest().isBefore(to));
        }
        return listOfLines.stream()
            .map(logParser::parseLine)
            .filter(logRecord -> logRecord.dateOfRequest().isAfter(from) && logRecord.dateOfRequest().isBefore(to));
    }

    private HttpStatusCode toEnumVal(int code) {
        for (HttpStatusCode enumCode: HttpStatusCode.values()) {
            if (enumCode.getCode() == code) {
                return enumCode;
            }
        }
        throw new RuntimeException("Unknown code");
    }

    @SuppressWarnings("MagicNumber")
    private <T> List<AnalyzeCounter> toSortedLimitList(Map<T, Long> counterMap) {
        return counterMap.entrySet().stream()
            .sorted(new LogCmp<T>().reversed())
            .limit(3)
            .map(tLongEntry -> new AnalyzeCounter(tLongEntry.getKey(),
                tLongEntry.getValue()))
            .toList();
    }

    private class LogCmp<T> implements Comparator<Map.Entry<T, Long>> {
        @Override
        public int compare(Map.Entry<T, Long> o1, Map.Entry<T, Long> o2) {
            return Long.compare(o1.getValue(), o2.getValue());
        }
    }

    public record AnalyzeCounter<T>(T t, long count) {}

    public record MostFrequentCode(HttpStatusCode statusCode, long count) {}
}
