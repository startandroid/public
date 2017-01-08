package ru.startandroid.messagelist.storage.database;

import java.util.Collection;

public class SqlUtils {

    public static String createWhereIn(String column, Collection<String> values) {
        if (values != null && values.size() > 0) {
            return String.format("%1$s in (%2$s)", column, getInPlaceholders(values.size()));
        }
        return "";
    }


    public static String getInPlaceholders(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        return sb.toString();
    }

}
