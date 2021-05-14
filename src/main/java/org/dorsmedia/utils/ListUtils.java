package org.dorsmedia.utils;

import java.util.List;

public class ListUtils {

    public static <T> T getFirstValue(List<T> list) {
        if (list != null) {
            return list.stream().findFirst().orElse(null);
        }
        return null;
    }

}
