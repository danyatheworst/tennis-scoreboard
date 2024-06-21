package com.danyatheworst.match;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MatchesResponseDto {
    public List<Match> matches;
    long totalCount;
    int currentPage;
    int lastPageNumber;
}
