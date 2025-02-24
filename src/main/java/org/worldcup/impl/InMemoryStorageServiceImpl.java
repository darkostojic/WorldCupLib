package org.worldcup.impl;

import org.worldcup.model.Match;
import org.worldcup.model.MatchStatus;
import org.worldcup.service.StorageService;
import org.worldcup.storage.InMemoryStorage;

import java.util.List;

public class InMemoryStorageServiceImpl implements StorageService {

    @Override
    public String addNewMatch(Match match) {
        return InMemoryStorage.addMatch(match);
    }

    @Override
    public Match getMatch(String matchId) {
        return InMemoryStorage.getMatch(matchId);
    }

    @Override
    public void updateMatchScore(Match match, Integer homeTeamScore, Integer awayTeamScore) {
        match.getHomeTeam().setScore(homeTeamScore);
        match.getAwayTeam().setScore(awayTeamScore);
    }

    @Override
    public Match getLiveMatchByTeamNames(String homeTeamName, String awayTeamName) {
        return InMemoryStorage.getAllMatches()
                .values()
                .stream()
                .filter(match -> match.getMatchStatus() == MatchStatus.LIVE)
                .filter(match -> match.getHomeTeam().getName().equalsIgnoreCase(homeTeamName) &&
                        match.getAwayTeam().getName().equalsIgnoreCase(awayTeamName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Match> getAllLiveMatches() {
        return InMemoryStorage.getAllMatches()
                .values()
                .stream()
                .filter(match -> match.getMatchStatus().equals(MatchStatus.LIVE))
                .toList();
    }

    @Override
    public List<Match> getAllFinishedMatches() {
        return InMemoryStorage.getAllMatches()
                .values()
                .stream()
                .filter(match -> match.getMatchStatus().equals(MatchStatus.FINISHED))
                .toList();
    }

}
