package com.danyatheworst.match.score;


import static com.danyatheworst.match.score.Point.*;
import static com.danyatheworst.match.score.State.*;

public class RegularGameScore extends GameScore<Point> {
    @Override
    protected Point getZeroScore() {
        return Point.ZERO;
    }

    @Override
    public State pointWon(int playerNumber) {
        Point playerScore = this.getPlayerScore(playerNumber);
        Point oppositePlayerScore = this.getOppositePlayerScore(playerNumber);

        if (playerScore.ordinal() <= THIRTY.ordinal()) {
            this.setPlayerScore(playerNumber, playerScore.next());
            return ONGOING;
        } else {
            if (oppositePlayerScore.ordinal() <= THIRTY.ordinal()) {
                if (playerNumber == 0) {
                    return PLAYER_ONE_WON;
                }
                return PLAYER_TWO_WON;
//                40-40 / AD-40 / 40-AD
            } else {
                if (playerScore.ordinal() == oppositePlayerScore.ordinal()) {
                    this.setPlayerScore(playerNumber, ADVANTAGE);
                    return ONGOING;
                } else if (playerScore.ordinal() == ADVANTAGE.ordinal()) {
                    if (playerNumber == 0) {
                        return PLAYER_ONE_WON;
                    }
                    return PLAYER_TWO_WON;
                } else {
                    setOppositePlayerScore(playerNumber, FORTY);
                    setPlayerScore(playerNumber, FORTY);
                    return ONGOING;
                }
            }
        }
    }
}
