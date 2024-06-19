package com.danyatheworst.match.score;

import java.util.ArrayList;
import java.util.List;

abstract public class Score<T> {
    private final List<T> playerScores = new ArrayList<>(2);

    abstract protected T getZeroScore();
    abstract protected State pointWon(int playerNumber);

    protected Score() {
        this.playerScores.add(this.getZeroScore());
        this.playerScores.add(this.getZeroScore());
    }

    public T getPlayerScore(int playerNumber) {
        return this.playerScores.get(playerNumber);
    }

    protected T getOppositePlayerScore(int playerNumber) {
        if (playerNumber == 0) {
            return this.playerScores.get(1);
        }
        return this.playerScores.get(0);
    }

    protected void setPlayerScore(int playerNumber, T score) {
        this.playerScores.set(playerNumber, score);
    }

    protected void setOppositePlayerScore(int playerNumber, T score) {
        if (playerNumber == 0) {
            this.playerScores.set(1, score);
        } else {
            this.playerScores.set(0, score);
        }
    }
}

//TODO: add validation to setters!