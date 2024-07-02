package com.danyatheworst.match;

import com.danyatheworst.exceptions.NotFoundException;
import com.danyatheworst.match.dto.CreateMatchRequestDto;
import com.danyatheworst.match.score.MatchScore;
import com.danyatheworst.player.Player;
import com.danyatheworst.player.PlayerRepository;

import java.util.UUID;

public class OngoingMatchService {
    private final OngoingMatchRepository ongoingMatchRepository = new OngoingMatchRepository();
    private final PlayerRepository playerRepository = new PlayerRepository();

    public Match getById(UUID uuid) {
        return this.ongoingMatchRepository
                .getById(uuid)
                .orElseThrow(() -> new NotFoundException("Ongoing match with " + uuid + " match id is not found"));
    }

    public UUID createNewMatch(CreateMatchRequestDto createMatchRequestDto) {
        Player player1 = new Player(createMatchRequestDto.getPlayerName1());
        Player player2 = new Player(createMatchRequestDto.getPlayerName2());

        player1 = this.playerRepository.saveOrFindExisting(player1);
        player2 = this.playerRepository.saveOrFindExisting(player2);

        Match ongoingMatch = new Match();
        ongoingMatch.setPlayer1(player1);
        ongoingMatch.setPlayer2(player2);
        ongoingMatch.setScore(new MatchScore(createMatchRequestDto.getFormat()));
        return this.ongoingMatchRepository.save(ongoingMatch);
    }
}
