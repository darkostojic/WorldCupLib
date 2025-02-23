package org.worldcup.storage;

import org.worldcup.model.Match;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage {

    private static final Map<String, Match> MATCHES = new HashMap<>();

    public static void addMatch(String key, Match value) {
        MATCHES.put(key, value);
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

}
