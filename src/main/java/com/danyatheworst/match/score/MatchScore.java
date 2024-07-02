package com.danyatheworst.match.score;

import java.util.ArrayList;
import java.util.List;

import static com.danyatheworst.match.score.State.*;

public class MatchScore extends Score<Integer> {
    public SetScore currentSet;
    public final List<SetScore> setsHistory = new ArrayList<>(3);
    public Format format;

    public MatchScore(Format format) {
        this.currentSet = new SetScore();
        this.format = format;
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
        this.setsHistory.add(this.currentSet);
        this.currentSet = new SetScore();

        if (this.format == Format.BEST_OF_THREE) {
            if (playerScore == 2 && oppositePlayerScore <= 1) {
                return playerNumber == 0 ? PLAYER_ONE_WON : PLAYER_TWO_WON;
            }
        } else {
            if (playerScore == 3 && oppositePlayerScore <= 2) {
                return playerNumber == 0 ? PLAYER_ONE_WON : PLAYER_TWO_WON;
            }
        }


        return ONGOING;
    }
}
