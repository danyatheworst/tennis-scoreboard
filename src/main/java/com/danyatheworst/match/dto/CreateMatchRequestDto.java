package com.danyatheworst.match.dto;

import com.danyatheworst.match.score.Format;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchRequestDto {
    private String playerName1;
    private String playerName2;
    private Format format;
}
