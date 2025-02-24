package org.worldcup.service;

import org.worldcup.exception.IllegalScoreException;
import org.worldcup.exception.MatchAlreadyExistException;
import org.worldcup.exception.NoMatchFoundException;
import org.worldcup.model.Match;

import java.util.List;

public interface WorldCupService {

    /**
     * Starts a new match between two teams.
     * <p>
     * This method initializes a new match with the provided home and away team names.
     * The match will be added to the system and marked as "LIVE". The unique match ID
     * will be returned upon successful creation.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     *     String matchId = worldCupService.startMatch("Brazil", "Argentina");
     * </pre>
     *
     * @param homeTeamName the name of the home team (must not be null or empty)
     * @param awayTeamName the name of the away team (must not be null or empty)
     * @return a unique match ID representing the newly created match
     * @throws MatchAlreadyExistException if a match between the same teams is already live
     * @throws IllegalArgumentException if either team name is null or empty
     */
    String startMatch(String homeTeamName, String awayTeamName) throws MatchAlreadyExistException, IllegalArgumentException;

    /**
     * Updates the score of an ongoing match.
     * <p>
     * This method updates the score of a live match identified by the given match ID.
     * The scores of both the home and away teams will be updated accordingly.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     *     worldCupService.updateMatchScore("M_0001", 2, 1);
     * </pre>
     *
     * @param matchId the unique identifier of the match to be updated (must not be null or empty)
     * @param homeTeamScore the new score for the home team (must be non-negative)
     * @param awayTeamScore the new score for the away team (must be non-negative)
     * @throws NoMatchFoundException if no match with the given ID exists
     * @throws IllegalScoreException if the provided scores are invalid (e.g., negative values)
     * @throws IllegalArgumentException if the match ID is null or empty
     */
    void updateMatchScore(String matchId, Integer homeTeamScore, Integer awayTeamScore)
            throws NoMatchFoundException, IllegalScoreException;

    /**
     * Marks a match as finished.
     * <p>
     * This method updates the status of an ongoing match to "FINISHED", preventing
     * any further score updates. The match is identified using its unique match ID.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     *     worldCupService.finishMatch("M_0001");
     * </pre>
     *
     * @param matchId the unique identifier of the match to be marked as finished (must not be null or empty)
     * @throws NoMatchFoundException if no match with the given ID exists
     */
    void finishMatch(String matchId) throws NoMatchFoundException;

    /**
     * Retrieves a list of all currently live matches.
     * <p>
     * This method returns a list of matches that are in progress, meaning they
     * have started but have not yet been marked as finished.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     *     List<Match> liveMatches = worldCupService.getAllLiveMatches();
     *     for (Match match : liveMatches) {
     *         System.out.println(match.getHomeTeam().getName() + " vs " + match.getAwayTeam().getName());
     *     }
     * </pre>
     *
     * @return a list of {@link Match} objects that are currently live (never null, but may be empty)
     */
    List<Match> getAllLiveMatches();

    /**
     * Retrieves a list of all completed matches.
     * <p>
     * This method returns a list of matches that have been marked as finished.
     * Finished matches can no longer be updated or modified.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     *     List<Match> finishedMatches = worldCupService.getAllFinishedMatches();
     *     for (Match match : finishedMatches) {
     *         System.out.println("Final Score: " + match.getHomeTeam().getScore() + " - " + match.getAwayTeam().getScore());
     *     }
     * </pre>
     *
     * @return a list of {@link Match} objects that have finished (never null, but may be empty)
     */
    List<Match> getAllFinishedMatches();

}
