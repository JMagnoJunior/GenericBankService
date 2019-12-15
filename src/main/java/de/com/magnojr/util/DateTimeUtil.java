package de.com.magnojr.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateTimeUtil {

    public static LocalDate parseLocalDateTime(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch(Exception e) {
            log.warn("error parsing date {}", date);
            throw e;
        }
    }
}
