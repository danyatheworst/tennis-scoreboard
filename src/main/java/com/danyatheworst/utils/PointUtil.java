package com.danyatheworst.utils;

import com.danyatheworst.match.score.GameScore;
import com.danyatheworst.match.score.Point;
import com.danyatheworst.match.score.RegularGameScore;


public class PointUtil {
    public static String getString(Point point) {
        return switch (point) {
            case ZERO -> "0";
            case FIFTEEN -> "15";
            case THIRTY -> "30";
            case FORTY -> "40";
            case ADVANTAGE -> "AD";
        };
    }

    public static String getPointRepresentation(Integer id, GameScore<?> gameScore) {
        if (gameScore instanceof RegularGameScore) {
            return PointUtil.getString((Point) gameScore.getPlayerScore(id));
        } else {
            return gameScore.getPlayerScore(id).toString();
        }
    }
}
