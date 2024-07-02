package com.danyatheworst.utils;

import com.danyatheworst.match.score.Format;

public class FormatUtil {
    public static Integer getNumeric(Format format) {
        return switch (format) {
            case BEST_OF_THREE -> 3;
            case BEST_OF_FIVE -> 5;
        };
    }
}
