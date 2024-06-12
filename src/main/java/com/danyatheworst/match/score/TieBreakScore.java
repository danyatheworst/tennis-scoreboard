package com.danyatheworst.match.score;

import static com.danyatheworst.match.score.State.*;

public class TieBreakScore extends GameScore<Integer> {
    @Override
    protected Integer getZeroScore() {
        return 0;
    }

    @Override
    public State pointWon(int playerNumber) {
        this.setPlayerScore(playerNumber, this.getPlayerScore(playerNumber) + 1);
        int playerScore = this.getPlayerScore(playerNumber);
        int oppositePlayerScore = this.getOppositePlayerScore(playerNumber);

        if (playerScore >= 7 && playerScore - oppositePlayerScore >= 2) {
            return playerNumber == 0 ? PLAYER_ONE_WON : PLAYER_TWO_WON;
        }

        return ONGOING;
    }
}

