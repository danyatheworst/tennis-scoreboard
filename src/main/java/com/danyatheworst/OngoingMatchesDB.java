package com.danyatheworst;

import com.danyatheworst.match.Match;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesDB {
    private static final ConcurrentHashMap<UUID, Match> instance = new ConcurrentHashMap<>();

    private OngoingMatchesDB() {};

    public static Optional<Match> getById(UUID uuid) {
        return Optional.ofNullable(instance.get(uuid));
    }

    public static UUID save(Match ongoingMatch) {
        UUID uuid = UUID.randomUUID();
        instance.put(uuid, ongoingMatch);
        return uuid;
    }

    public static void remove(UUID uuid) {
        instance.remove(uuid);
    };
}
