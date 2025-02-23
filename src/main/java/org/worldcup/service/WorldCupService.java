package org.worldcup.service;

import org.worldcup.exception.IllegalScoreException;
import org.worldcup.exception.MatchAlreadyExistException;
import org.worldcup.exception.NoMatchFoundException;
import org.worldcup.model.Match;

import java.util.List;
import java.util.Map;

public interface WorldCupService {

    String startMatch(String homeTeamName, String awayTeamName) throws MatchAlreadyExistException;
    void updateMatchScore(String matchId, Integer homeTeamScore, Integer awayTeamScore) throws NoMatchFoundException, IllegalScoreException;
    void finishMatch(String matchId) throws NoMatchFoundException;
    List<Match> getAllLiveMatches();
    List<Match> getAllFinishedMatches();

}
