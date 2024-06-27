package com.danyatheworst.match.dto;

import com.danyatheworst.player.PlayerScoreDto;

import java.util.List;


public class MatchScoreViewDto {
    public MatchScoreViewDto(List<PlayerScoreDto> players) {
        this.players = players;
    }

    public List<PlayerScoreDto> players;
    public Integer winnerId;
}
