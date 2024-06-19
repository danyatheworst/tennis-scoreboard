package com.danyatheworst.match.score;

public enum Point {
    ZERO, FIFTEEN, THIRTY, FORTY, ADVANTAGE;

   public Point next() {
        return Point.values()[this.ordinal() + 1];
    }
}
