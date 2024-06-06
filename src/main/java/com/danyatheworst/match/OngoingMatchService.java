package com.danyatheworst.match;

import com.danyatheworst.exceptions.NotFoundException;
import com.danyatheworst.player.Player;

import java.util.UUID;

public class OngoingMatchService {
    private final OngoingMatchRepository ongoingMatchRepository = new OngoingMatchRepository();

    public Match getById(UUID uuid) {
        return this.ongoingMatchRepository
                .getById(uuid)
                .orElseThrow(() -> new NotFoundException("Ongoing match with " + uuid + " match id is not found"));
    }

    public UUID createNewMatch(CreateMatchRequestDto createMatchRequestDto) {
        String playerName1 = createMatchRequestDto.getPlayerName1();
        String playerName2 = createMatchRequestDto.getPlayerName2();

        Match ongoingMatch = new Match();
        ongoingMatch.setPlayer1(new Player(playerName1));
        ongoingMatch.setPlayer2(new Player(playerName2));
        return this.ongoingMatchRepository.save(ongoingMatch);
    }
}
