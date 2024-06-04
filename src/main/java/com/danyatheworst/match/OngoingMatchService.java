package com.danyatheworst.match;

import com.danyatheworst.player.Player;
import com.danyatheworst.player.PlayerRepository;

public class OngoingMatchService {
    private final PlayerRepository playerRepository = new PlayerRepository();
    private final OngoingMatchRepository ongoingMatchRepository = new OngoingMatchRepository();

    public String createNewMatch(CreateMatchRequestDto createMatchRequestDto) {
        String playerName1 = createMatchRequestDto.getPlayerName1();
        String playerName2 = createMatchRequestDto.getPlayerName2();

        Match ongoingMatch = new Match();
        ongoingMatch.setPlayer1(new Player(playerName1));
        ongoingMatch.setPlayer2(new Player(playerName2));
        return this.ongoingMatchRepository.save(ongoingMatch);
    }
}
