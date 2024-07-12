package com.danyatheworst.utils;

import com.danyatheworst.match.dto.CreateMatchRequestDto;
import com.danyatheworst.exceptions.InvalidParameterException;

import java.util.Objects;

public final class Validation {
    //{*} — the function is used in the context of form validation:
    // an argument 'String paramName' is needed to inform a user which submitted input value is invalid

    public static void validate(CreateMatchRequestDto createMatchRequestDto) {
        String playerName1 = createMatchRequestDto.getPlayerName1();
        String playerName2 = createMatchRequestDto.getPlayerName2();

        if (Objects.equals(playerName1.toLowerCase(), playerName2.toLowerCase())) {
            throw new InvalidParameterException("Players' names must be unique");
        }

        validatePlayerName(playerName1, "player name 1");
        validatePlayerName(playerName2, "player name 2");
    }

    //{*}
    public static void validatePresence(String value, String paramName) {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException(paramName + " is missing");
        }
    }

    //{*}
    public static void validatePlayerName(String name, String paramName) {
        validatePresence(name, paramName);
        if (!name.matches("^[\\p{L}\\s\\-.]+$")) {
            throw new InvalidParameterException(paramName + " is invalid (characters, spaces, '.' and '-' are only allowed)");
        }
        if (name.length() > 100) {
            throw new InvalidParameterException(paramName + " must contain no more than 100 letters");
        }
    }

    public static void validatePointWinnerId(String id) {
        if (!"0".equals(id) && !"1".equals(id)) {
            throw new IllegalArgumentException("point winner id must be '0' or '1'.");
        }
    }
}
