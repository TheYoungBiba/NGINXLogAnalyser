package edu.project;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ConsoleParser {
    private ConsoleParser() {}

    public static ZonedDateTime parseDateTime(String time) {
        if (time.split("T").length == 1) {
            return ZonedDateTime.of(LocalDate.parse(time), LocalTime.MIN, ZoneId.systemDefault());
        } else {
            return ZonedDateTime.parse(time);
        }
    }
}
