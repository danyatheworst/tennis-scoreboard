package com.danyatheworst.match;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MatchesResponseDto {
    public List<Match> matches;
    public long totalCount;
    public int currentPage;
    public int pageSize;
}
