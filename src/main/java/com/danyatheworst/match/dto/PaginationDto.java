package com.danyatheworst.match.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaginationDto {
    private int lastPage;
    private int leftMost;
    private int rightMost;
    private boolean leftDots;
    private boolean rightDots;
    private int currentPage;
}
