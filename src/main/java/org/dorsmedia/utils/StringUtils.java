package org.dorsmedia.utils;

public class StringUtils {

    public static String parseNullSafe(Object column) {
        return column == null ? null : column.toString();
    }

    public static String blankOnNullString(String column) {
        return column == null ? "" : column;
    }

}
