package com.springboot.klos.utils;

public class RegExpUtil {
    public static final String DATE_REGEXP = "(([0-2][0-9]|3[0-1])\\.(0[1-9]|1[0-2])\\.[1-2]\\d{3})";
    public static final String TIME_PATTERN = "(([0-1][0-9]|2[0-3])\\:([0-5][0-9])\\:([0-5][0-9]))";
    public static final String DATE_TIME_REGEXP =
            "(([0-2][0-9]|3[0-1])\\.(0[1-9]|1[0-2])\\.[1-2]\\d{3}) "
                    + "(([0-1][0-9]|2[0-3])\\:([0-5][0-9]))";
    public static final String PHONE_REGEXP = "\\+\\d{12}";
}
