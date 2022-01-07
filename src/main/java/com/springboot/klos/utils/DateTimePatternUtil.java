package com.springboot.klos.utils;

import java.time.LocalTime;

public class DateTimePatternUtil {
    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final LocalTime ZERO_LOCAL_TIME = LocalTime.of(0,0,0);
}
