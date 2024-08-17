package com.piebin.binproject.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeManager {
    public static LocalDateTime getStartOfDay() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
    }
    public static LocalDateTime getEndOfDay() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
    }
}
