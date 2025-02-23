package org.worldcup.storage;

import org.worldcup.model.Match;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage {

    private static final Map<String, Match> MATCHES = new HashMap<>();
    private final static String ID_PREFIX = "M_";
    private static Integer ID_SEQUENCE = 1;

    public static String addMatch(Match match) {
        String newId = getNextId();
        match.setId(newId);
        MATCHES.put(newId, match);
        return newId;
    }

    public static Match getMatch(String key) {
        return MATCHES.get(key);
    }

    public static void removeMatch(String key) {
        MATCHES.remove(key);
    }

    public static Map<String, Match> getAllMatches() {
        return MATCHES;
    }

    private static String getNextId() {
        String nextId = ID_PREFIX + String.format("%04d", ID_SEQUENCE);
        ID_SEQUENCE++;
        return nextId;
    }

}
