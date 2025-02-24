package org.worldcup.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.worldcup.model.Match;
import org.worldcup.model.MatchStatus;
import org.worldcup.service.StorageService;
import org.worldcup.storage.InMemoryStorage;

import java.util.List;

public class InMemoryStorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryStorageServiceImpl.class);

    @Override
    public String addNewMatch(Match match) {
        logger.info("Adding new match: {} vs {}", match.getHomeTeam().getName(), match.getAwayTeam().getName());
        return InMemoryStorage.addMatch(match);
    }

    @Override
    public Match getMatch(String matchId) {
        logger.info("Retrieving match with ID: {}", matchId);
        return InMemoryStorage.getMatch(matchId);
    }

    @Override
    public void updateMatchScore(Match match, Integer homeTeamScore, Integer awayTeamScore) {
        logger.info("Updating score for match: {} vs {}", match.getHomeTeam().getName(), match.getAwayTeam().getName());
        match.getHomeTeam().setScore(homeTeamScore);
        match.getAwayTeam().setScore(awayTeamScore);
    }

    @Override
    public Match getLiveMatchByTeamNames(String homeTeamName, String awayTeamName) {
        logger.info("Fetching live match for teams: {} vs {}", homeTeamName, awayTeamName);
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
        logger.info("Fetching all live matches");
        return InMemoryStorage.getAllMatches()
                .values()
                .stream()
                .filter(match -> match.getMatchStatus().equals(MatchStatus.LIVE))
                .toList();
    }

    @Override
    public List<Match> getAllFinishedMatches() {
        logger.info("Fetching all finished matches");
        return InMemoryStorage.getAllMatches()
                .values()
                .stream()
                .filter(match -> match.getMatchStatus().equals(MatchStatus.FINISHED))
                .toList();
    }

}
