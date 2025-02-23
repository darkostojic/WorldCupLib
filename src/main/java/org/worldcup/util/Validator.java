package org.worldcup.util;

import org.worldcup.exception.IllegalScoreException;
import org.worldcup.model.Match;

public class Validator {

    public static void validateNewMatchScore(Match match, Integer newHomeTeamScore, Integer newAwayTeamScore)
            throws IllegalScoreException {

        Integer currentHomeTeamScore = match.getHomeTeam().getScore();
        Integer currentAwayTeamScore = match.getAwayTeam().getScore();
        if (!currentHomeTeamScore.equals(newHomeTeamScore) && !currentAwayTeamScore.equals(newAwayTeamScore)) {
            throw new IllegalScoreException("Score for both teams can not be changed at same time.");
        }
        if (Math.abs(currentHomeTeamScore - newHomeTeamScore) > 1) {
            throw new IllegalScoreException("The score can only be increased or decreased by 1.");
        }
        if (Math.abs(currentAwayTeamScore - newAwayTeamScore) > 1) {
            throw new IllegalScoreException("The score can only be increased or decreased by 1.");
        }
    }

}
