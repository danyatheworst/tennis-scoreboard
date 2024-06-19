package com.danyatheworst.utils;

import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.match.score.Point;


public class PointUtil {
    public static String getString(Point point) throws Exception {
        return switch (point) {
            case FIFTEEN -> "15";
            case THIRTY -> "30";
            case FORTY -> "40";
            case ADVANTAGE -> "AD";
            default -> throw new DatabaseOperationException("something wrong happened");
        };
    }
}
