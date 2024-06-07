package com.danyatheworst.match.score;


import static com.danyatheworst.match.score.Point.*;
import static com.danyatheworst.match.score.State.*;

public class RegularGameScore extends GameScore<Point> {
    @Override
    protected Point getZeroScore() {
        return Point.NULL;
    }

    @Override
    protected State pointWon(int playerNumber) {
        Point winnerScore = this.getPlayerScore(playerNumber);
        Point loserScore = this.getOppositePlayerScore(playerNumber);

        if (winnerScore.ordinal() <= THIRTY.ordinal()) {
            this.setPlayerScore(playerNumber, winnerScore.next());
            return ONGOING;
        } else {
            if (loserScore.ordinal() <= THIRTY.ordinal()) {
                if (playerNumber == 0) {
                    return PLAYER_ONE_WON;
                }
                return PLAYER_TWO_WON;
//                40-40 / AD-40 / 40-AD
            } else {
                if (winnerScore.ordinal() == loserScore.ordinal()) {
                    this.setPlayerScore(playerNumber, ADVANTAGE);
                    return ONGOING;
                } else if (winnerScore.ordinal() == ADVANTAGE.ordinal()) {
                    if (playerNumber == 0) {
                        return PLAYER_ONE_WON;
                    }
                    return PLAYER_TWO_WON;
                } else {
                    setOppositePlayerScore(playerNumber, FORTY);
                    setPlayerScore(playerNumber, ADVANTAGE);
                    return ONGOING;
                }
            }
        }
    }
}
