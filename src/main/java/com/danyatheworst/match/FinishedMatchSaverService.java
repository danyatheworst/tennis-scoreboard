package com.danyatheworst.match;

import com.danyatheworst.player.Player;

import java.util.UUID;

public class FinishedMatchSaverService {
    final private OngoingMatchRepository ongoingMatchRepository = new OngoingMatchRepository();
    final private MatchRepository matchRepository = new MatchRepository();

    public void save(Match finishedMatch, int winnerId, UUID uuid) {
        Player winner = winnerId == 0 ? finishedMatch.getPlayer1() : finishedMatch.getPlayer2();
        finishedMatch.setWinner(winner);
        this.ongoingMatchRepository.remove(uuid);
        this.matchRepository.save(finishedMatch);
    };
}
