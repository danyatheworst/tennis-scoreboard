package com.danyatheworst.match.score;

import static com.danyatheworst.match.score.State.*;

public class MatchScore extends Score<Integer> {
    protected SetScore currentSet;

    public MatchScore() {
        this.currentSet = new SetScore();
    }

    @Override
    protected Integer getZeroScore() {
        return 0;
    }

    @Override
    public State pointWon(int playerNumber) {
        State gameState = this.currentSet.pointWon(playerNumber);

        if (gameState != ONGOING) {
            return this.setWon(playerNumber);
        }

        return ONGOING;
    }

    private State setWon(int playerNumber) {
        this.setPlayerScore(playerNumber, this.getPlayerScore(playerNumber) + 1);
        int playerScore = this.getPlayerScore(playerNumber);
        int oppositePlayerScore = this.getOppositePlayerScore(playerNumber);
        this.currentSet = new SetScore();

        if (playerScore == 2 && oppositePlayerScore <= 1) {
            return playerNumber == 0 ? PLAYER_ONE_WON : PLAYER_TWO_WON;
        }

        return ONGOING;
    }
}
