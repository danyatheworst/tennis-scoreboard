package com.danyatheworst.match;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchRepository {
    private final ConcurrentHashMap<UUID, Match> ongoingMatches = new ConcurrentHashMap<>();

    public Match getById(String id) {
        return ongoingMatches.get(UUID.fromString(id));
    }

    public String save(Match ongoingMatch) {
        UUID uuid = UUID.randomUUID();
        this.ongoingMatches.put(uuid, ongoingMatch);

        return uuid.toString();
    }

    public void remove(UUID uuid) {
        this.ongoingMatches.remove(uuid);
    }
}
