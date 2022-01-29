package com.springboot.klos.utils;

public class RegExpUtil {
    public static final String DATE_REGEXP = "(([0-2][0-9]|3[0-1])"
            + "\\.(0[1-9]|1[0-2])\\.[1-2]\\d{3})";
    public static final String TIME_PATTERN = "(([0-1][0-9]|2[0-3])"
            + "\\:([0-5][0-9])\\:([0-5][0-9]))";
    public static final String DATE_TIME_REGEXP =
            "(([0-2][0-9]|3[0-1])\\.(0[1-9]|1[0-2])\\.[1-2]\\d{3}) "
                    + "(([0-1][0-9]|2[0-3])\\:([0-5][0-9]))";
    public static final String PASSWORD_REGEXP =
            "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{8,20}$";
    public static final String PASSWORD_REGEXP_ADMIN =
            "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";
    public static final String EMAIL_REGEXP = "^(?=.{1,64}@)[A-Za-z0-9_-]+"
            + "(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+"
            + "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
}
