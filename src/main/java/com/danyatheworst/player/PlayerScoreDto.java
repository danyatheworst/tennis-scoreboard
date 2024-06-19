package com.danyatheworst.player;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PlayerScoreDto {
    public int id;
    public String name;
    public String currentGame;
    public String currentPoint;
    public List<String> previousSets;
}