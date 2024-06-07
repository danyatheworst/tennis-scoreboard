package com.danyatheworst.match.score;

public enum Point {
    NULL, FIFTEEN, THIRTY, FORTY, ADVANTAGE;

   public Point next() {
        return Point.values()[this.ordinal() + 1];
    }
}
