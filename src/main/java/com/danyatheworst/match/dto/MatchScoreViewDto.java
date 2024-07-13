package com.danyatheworst.match.dto;

import com.danyatheworst.player.PlayerScoreDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class MatchScoreViewDto {
    public MatchScoreViewDto(List<PlayerScoreDto> players) {
        this.players = players;
    }
    private List<PlayerScoreDto> players;
    private Integer winnerId;
}
