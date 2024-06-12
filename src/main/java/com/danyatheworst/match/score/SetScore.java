package com.danyatheworst.match.score;

import static com.danyatheworst.match.score.State.*;

public class SetScore extends Score<Integer> {

    protected GameScore<?> currentGame;

    public SetScore() {
        this.currentGame = new RegularGameScore();
    }

    @Override
    protected Integer getZeroScore() {
        return 0;
    }

    @Override
    public State pointWon(int playerNumber) {
        State gameState = this.currentGame.pointWon(playerNumber);

        if (gameState != ONGOING) {
            return this.gameWon(playerNumber);
        }

        return ONGOING;
    }

    private State gameWon(int playerNumber) {
        this.setPlayerScore(playerNumber, this.getPlayerScore(playerNumber) + 1);
        int playerScore = this.getPlayerScore(playerNumber);
        int oppositePlayerScore = this.getOppositePlayerScore(playerNumber);

        if (this.currentGame instanceof TieBreakScore || playerScore == 6 && oppositePlayerScore < 5) {
            this.currentGame = new RegularGameScore();
            return playerNumber == 0 ? PLAYER_ONE_WON : PLAYER_TWO_WON;
        }

        if (playerScore == 6 && oppositePlayerScore == 6) {
            this.currentGame = new TieBreakScore();
            return ONGOING;
        }

        this.currentGame = new RegularGameScore();
        return ONGOING;
    }
}
