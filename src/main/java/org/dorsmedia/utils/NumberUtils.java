package org.dorsmedia.utils;

public class NumberUtils {

    public static Integer parseIntNullSafe(Object column) {
        return column == null ? null : Integer.parseInt(column.toString());
    }
    public static Integer parseIntNullSafe(String string) {
        return string != null ? Integer.parseInt(string) : null;
    }

}
