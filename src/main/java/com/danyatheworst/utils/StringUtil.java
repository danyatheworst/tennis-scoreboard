package com.danyatheworst.utils;

import com.danyatheworst.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public static UUID fromString(String string) {
        try {
            if (string.length() != 36) {
                throw new InvalidParameterException();
            }
            return UUID.fromString(string);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException();
        }
    }
}
