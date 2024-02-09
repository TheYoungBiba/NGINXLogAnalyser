package edu.project;

import edu.project.LogAnalyzer.AnalyzeCounter;
import edu.project.LogAnalyzer.MostFrequentCode;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class Renderer {
    private Renderer() {}

    public static String mainInformationRender(List<Path> listOfPath, ZonedDateTime from, ZonedDateTime to,
        long countOfRequests, long mediumSizeOfServerAns) {
        final int lengthOfValueName = 8;
        StringBuilder render = new StringBuilder("#### Общая информация\n\n");
        StringBuilder fileNames = new StringBuilder();
        Path[] arrayOfPath = listOfPath.toArray(new Path[0]);
        for (Path value : arrayOfPath) {
            fileNames.append(value.getFileName());
            fileNames.append(", ");
        }
        fileNames.delete(fileNames.length() - 2, fileNames.length());
        List<Integer> listOfMaxLengths = List.of(
            fileNames.length(),
            from != null ? from.toString().length() : 0,
            to != null ? to.toString().length() : 0,
            String.valueOf(countOfRequests).length(),
            String.valueOf(mediumSizeOfServerAns).length() + 1,
            lengthOfValueName);
        final int firstColumnLength = 24;
        int maxLength = listOfMaxLengths.stream()
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .get() + firstColumnLength + 1;
        render.append(stringLengthLeveler("|Метрика               |Значение", maxLength))
        .append(System.lineSeparator())
        .append(getLine("|----------------------|", maxLength))
        .append(System.lineSeparator())
        .append(stringLengthLeveler("|Файл(-ы)              |" + fileNames, maxLength))
        .append(System.lineSeparator());
        if (from != null) {
            render.append(stringLengthLeveler("|Начальная дата        |" + from, maxLength))
            .append(System.lineSeparator());
        } else {
            render.append(stringLengthLeveler("|Начальная дата        | - ", maxLength))
            .append(System.lineSeparator());
        }
        if (to != null) {
            render.append(stringLengthLeveler("|Конечная дата         |" + to, maxLength))
            .append(System.lineSeparator());
        } else {
            render.append(stringLengthLeveler("|Конечная дата         | - ", maxLength))
            .append(System.lineSeparator());
        }
        render.append(stringLengthLeveler("|Количество запросов   |" + countOfRequests, maxLength))
        .append(System.lineSeparator())
        .append(stringLengthLeveler("|Средний размер ответа |" + mediumSizeOfServerAns + "b",
            maxLength))
        .append(System.lineSeparator());
        return render.toString();
    }

    public static String twoColumnRender(
        List<AnalyzeCounter> listOfCounters, String mainAnnotation, String firstColumnName,
        String secondColumnName) {
        StringBuilder render = new StringBuilder("#### " + mainAnnotation + "\n\n");
        int maxLengthOfFirstColumnValue = listOfCounters.stream()
            .map(analyzeCounter -> analyzeCounter.t().toString().length())
            .max(Integer::compareTo)
            .get();
        maxLengthOfFirstColumnValue = Math.max(maxLengthOfFirstColumnValue, firstColumnName.length());
        long maxLengthOfSecondColumnValue = listOfCounters.stream()
            .map(AnalyzeCounter::count)
            .max(Long::compareTo)
            .get();
        maxLengthOfSecondColumnValue = Math.max(String.valueOf(maxLengthOfSecondColumnValue).length() + 1,
            secondColumnName.length());
        final int shiftToUncheckedPositions = 4;
        int fullLength = maxLengthOfFirstColumnValue + ((int) maxLengthOfSecondColumnValue) + shiftToUncheckedPositions;
        render.append(stringLengthLeveler(stringLengthLeveler("|" + firstColumnName,
                maxLengthOfFirstColumnValue + 2) + secondColumnName, fullLength))
            .append(System.lineSeparator())
            .append(getLine(getLine("|", maxLengthOfFirstColumnValue + 2), fullLength))
            .append(System.lineSeparator());
        for (AnalyzeCounter counter: listOfCounters.toArray(new AnalyzeCounter[0])) {
            render.append(stringLengthLeveler(stringLengthLeveler("|" + counter.t(),
                    maxLengthOfFirstColumnValue + 2) + counter.count(), fullLength))
            .append(System.lineSeparator());
        }
        return render.toString();
    }

    public static String codeRender(List<MostFrequentCode> listOfCodes) {
        StringBuilder stringBuilder = new StringBuilder("#### Коды ответа\n\n");
        int maxLengthOfCodeName = listOfCodes.stream()
            .map(httpStatusCodeAnalyzeCounter -> httpStatusCodeAnalyzeCounter.statusCode().toString().length())
            .max(Integer::compareTo).get();
        int maxCounterLength = listOfCodes.stream()
            .map(httpStatusCodeAnalyzeCounter -> String.valueOf(httpStatusCodeAnalyzeCounter.count()).length())
            .max(Integer::compareTo).get();
        final int lengthOfFirstColumn = 6;
        final int lengthOfSecondColumnName = 3;
        int lengthOfTwoFirstColumns = lengthOfFirstColumn + Math.max(maxLengthOfCodeName, lengthOfSecondColumnName) + 1;
        final int lengthOfCountNameColumn = 10;
        int fullLength = lengthOfTwoFirstColumns + Math.max(maxCounterLength, lengthOfCountNameColumn) + 2;
        stringBuilder.append(stringLengthLeveler(stringLengthLeveler("|Код |Имя", lengthOfTwoFirstColumns)
                    + "Количество", fullLength))
        .append(System.lineSeparator());
        stringBuilder.append(getLine(getLine("|----|---", lengthOfTwoFirstColumns), fullLength))
        .append(System.lineSeparator());
        MostFrequentCode[] mostFrequentCodes = new MostFrequentCode[listOfCodes.size()];
        listOfCodes.toArray(mostFrequentCodes);
        for (MostFrequentCode code: mostFrequentCodes) {
            stringBuilder.append(stringLengthLeveler(stringLengthLeveler("|" + code.statusCode().getCode()
                    + " |" + code.statusCode(), lengthOfTwoFirstColumns) + code.count(), fullLength))
            .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private static String stringLengthLeveler(String value, int valuableLength) {
        return value + " ".repeat(Math.max(1, valuableLength - value.length())) + "|";
    }

    private static String getLine(String baseLine, int valuableLength) {
        return baseLine + "-".repeat(valuableLength - baseLine.length()) + "|";
    }
}
