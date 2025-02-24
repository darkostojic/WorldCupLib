package org.worldcup.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.worldcup.exception.IllegalScoreException;
import org.worldcup.exception.MatchAlreadyExistException;
import org.worldcup.exception.NoMatchFoundException;
import org.worldcup.model.Match;
import org.worldcup.model.MatchStatus;
import org.worldcup.model.Team;
import org.worldcup.service.StorageService;
import org.worldcup.service.WorldCupService;
import org.worldcup.util.MatchSorter;
import org.worldcup.util.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class WorldCupServiceImpl implements WorldCupService {

    private static final Logger logger = LoggerFactory.getLogger(WorldCupServiceImpl.class);

    private final StorageService storageService;

    public WorldCupServiceImpl() {
        this.storageService = new InMemoryStorageServiceImpl();
    }

    @Override
    public String startMatch(String homeTeamName, String awayTeamName) throws MatchAlreadyExistException {
        logger.info("Starting match between {} and {}", homeTeamName, awayTeamName);

        Team homeTeam = new Team(homeTeamName, 0);
        Team awayTeam = new Team(awayTeamName, 0);

        Match existingMatch = storageService.getLiveMatchByTeamNames(homeTeamName, awayTeamName);
        if(existingMatch != null) {
            throw new MatchAlreadyExistException("Match is in progress.");
        }

        Match match = new Match(homeTeam, awayTeam, LocalDateTime.now(), MatchStatus.LIVE);

        return storageService.addNewMatch(match);
    }

    @Override
    public void updateMatchScore(String matchId, Integer homeTeamScore, Integer awayTeamScore)
            throws NoMatchFoundException, IllegalScoreException {
        logger.debug("Updating match {} with scores: Home - {}, Away - {}", matchId, homeTeamScore, awayTeamScore);

        Match match = storageService.getMatch(matchId);
        if(match == null || match.getMatchStatus().equals(MatchStatus.FINISHED)) {
            throw new NoMatchFoundException("No live match found for id: " + matchId);
        }
        Validator.validateNewMatchScore(match, homeTeamScore, awayTeamScore);
        storageService.updateMatchScore(match, homeTeamScore, awayTeamScore);
    }

    @Override
    public void finishMatch(String matchId) throws NoMatchFoundException {
        logger.error("Error finishing match: {}", matchId);

        Match match = storageService.getMatch(matchId);
        if(match == null || match.getMatchStatus().equals(MatchStatus.FINISHED)) {
            throw new NoMatchFoundException("No live match found for id: " + matchId);
        }
        match.setMatchFinish(LocalDateTime.now());
        match.setMatchStatus(MatchStatus.FINISHED);
    }

    @Override
    public List<Match> getAllLiveMatches() {
        logger.error("Get all live matches");

        List<Match> matches = storageService.getAllLiveMatches();
        return MatchSorter.sortMatchesByScoreSumAndStartTime(matches);
    }

    @Override
    public List<Match> getAllFinishedMatches() {
        logger.error("Get all finished matches");

        List<Match> matches = storageService.getAllFinishedMatches();
        return MatchSorter.sortMatchesByScoreSumAndStartTime(matches);
    }
}
