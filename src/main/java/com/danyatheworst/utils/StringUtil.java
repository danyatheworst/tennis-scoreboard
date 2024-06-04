package com.danyatheworst.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class StringUtil {
    public static String removeExtraSpaces(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : string.split(" ")) {
            if (!Objects.equals(s, "")) {
                stringBuilder.append(s);
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString().trim();
    }
}
