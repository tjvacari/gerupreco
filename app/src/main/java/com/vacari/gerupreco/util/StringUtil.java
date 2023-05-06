package com.vacari.gerupreco.util;

public class StringUtil {

    public static String or(String st1, String st2) {
        if(isNotEmpty(st1)) {
            return st1;
        }

        return st2;
    }

    public static boolean isEmpty(String st) {
        return st == null || st.trim().isEmpty();
    }

    public static boolean isNotEmpty(String st) {
        return !isEmpty(st);
    }
}
