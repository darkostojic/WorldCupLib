package org.worldcup.service;

import org.worldcup.model.Match;

import java.util.List;

public interface StorageService {

    String addNewMatch(Match match);
    Match getMatch(String matchId);
    void updateMatchScore(Match match, Integer homeTeamScore, Integer awayTeamScore);
    Match getLiveMatchByTeamNames(String homeTeamName, String awayTeamName);
    List<Match> getAllLiveMatches();
    List<Match> getAllFinishedMatches();

}
