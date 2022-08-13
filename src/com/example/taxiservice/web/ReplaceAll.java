package com.example.taxiservice.web;

/**
 * Custom function
 */
public final class ReplaceAll {
    private ReplaceAll() {
    }

    public static String replaceAll(String string, String pattern, String replacement) {
        return string.replaceAll(pattern, replacement);
    }
}
