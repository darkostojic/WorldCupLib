package org.worldcup.impl;

import org.worldcup.exception.IllegalScoreException;
import org.worldcup.exception.NoMatchFoundException;
import org.worldcup.model.Match;
import org.worldcup.model.MatchStatus;
import org.worldcup.model.Team;
import org.worldcup.service.StorageService;
import org.worldcup.service.WorldCupService;
import org.worldcup.util.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class WorldCupServiceImpl implements WorldCupService {

    private final StorageService storageService;

    public WorldCupServiceImpl() {
        this.storageService = new InMemoryStorageServiceImpl();
    }

    @Override
    public String startMatch(String homeTeamName, String awayTeamName) {
        Team homeTeam = new Team(homeTeamName, 0);
        Team awayTeam = new Team(awayTeamName, 0);

        Match match = new Match(homeTeam, awayTeam, LocalDateTime.now(), MatchStatus.LIVE);
        //TODO check if already started
        return storageService.addNewMatch(match);
    }

    @Override
    public void updateMatchScore(String matchId, Integer homeTeamScore, Integer awayTeamScore)
            throws NoMatchFoundException, IllegalScoreException {
        Match match = storageService.getMatch(matchId);
        if(match == null || match.getMatchStatus().equals(MatchStatus.FINISHED)) {
            throw new NoMatchFoundException("No live match found for id: " + matchId);
        }
        Validator.validateNewMatchScore(match, homeTeamScore, awayTeamScore);
        storageService.updateMatchScore(match, homeTeamScore, awayTeamScore);
    }

    @Override
    public void finishMatch(String matchId) throws NoMatchFoundException {
        Match match = storageService.getMatch(matchId);
        if(match == null || match.getMatchStatus().equals(MatchStatus.FINISHED)) {
            throw new NoMatchFoundException("No live match found for id: " + matchId);
        }
        match.setMatchFinish(LocalDateTime.now());
        match.setMatchStatus(MatchStatus.FINISHED);
    }

    @Override
    public List<Match> getAllLiveMatches() {
        List<Match> matches = storageService.getAllLiveMatches();
        //TODO sort
        return matches;
    }

    @Override
    public List<Match> getAllFinishedMatches() {
        List<Match> matches = storageService.getAllLiveMatches();
        //TODO sort
        return matches;
    }
}
