package com.danyatheworst.match.dto;

import com.danyatheworst.match.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MatchesViewDto {
    private List<Match> matches;
    private long totalCount;
    private PaginationDto paginationDto;
}
