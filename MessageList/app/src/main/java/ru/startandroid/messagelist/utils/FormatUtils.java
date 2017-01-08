package ru.startandroid.messagelist.utils;

import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    public static String formatDateTime(long dateTime) {
        return String.format(Locale.getDefault(), "%1$tF %1$tT", new Date(dateTime));
    }
}
