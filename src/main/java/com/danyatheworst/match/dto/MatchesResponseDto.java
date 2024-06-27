package com.danyatheworst.match.dto;

import com.danyatheworst.match.Match;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MatchesResponseDto {
    public List<Match> matches;
    public long totalCount;
    public int currentPage;
    public int pageSize;
}
