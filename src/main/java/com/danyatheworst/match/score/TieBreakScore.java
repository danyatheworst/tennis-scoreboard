package com.danyatheworst.match.score;

import static com.danyatheworst.match.score.State.ONGOING;

public class TieBreakScore extends GameScore<Integer> {
    @Override
    protected Integer getZeroScore() {
        return 0;
    }

    @Override
    protected State pointWon(int playerNumber) {

        return ONGOING;
    }
}

