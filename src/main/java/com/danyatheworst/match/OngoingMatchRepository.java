package com.danyatheworst.match;

import com.danyatheworst.OngoingMatchesDB;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchRepository {

    public Optional<Match> getById(UUID uuid) {
        return OngoingMatchesDB.getById(uuid);
    }

    public UUID save(Match ongoingMatch) {
        return OngoingMatchesDB.save(ongoingMatch);
    }

    public void remove(UUID uuid) {
        OngoingMatchesDB.remove(uuid);
    }
}
