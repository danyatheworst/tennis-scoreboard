package com.danyatheworst.utils;

import com.danyatheworst.Match.CreateMatchRequestDto;
import com.danyatheworst.exceptions.InvalidParameterException;

import java.util.Objects;

public final class Validation {
    public static void validate(CreateMatchRequestDto createMatchRequestDto) {
        String playerName1 = createMatchRequestDto.getPlayerName1();
        String playerName2 = createMatchRequestDto.getPlayerName2();

        if (Objects.equals(playerName1, playerName2)) {
            throw new InvalidParameterException("Players' names must be unique");
        }

        validatePlayerName(playerName1, "playerName1");
        validatePlayerName(playerName2, "playerName2");
    }

    private static void validatePresence(String value, String key) {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException(key + " is missing");
        }
    }

    //TODO: key????
    private static void validatePlayerName(String name, String key) {
        validatePresence(name, key);
        if (!name.matches("^[\\p{L}\\s-]+$")) {
            throw new InvalidParameterException(key + " is invalid (characters, spaces and '-' are only allowed)");
        }
        if (name.length() > 100) {
            throw new InvalidParameterException(key + " must contain no more than 100 letters");
        }
    }
}
