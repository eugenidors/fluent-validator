package org.dorsmedia.utils;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtils {

    public static Date parseDateNullSafe(Object column) {
        return column == null ? null : new Date(((Timestamp) column).getTime());
    }

}
