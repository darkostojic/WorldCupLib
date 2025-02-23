package org.worldcup.util;

import org.worldcup.model.Match;

import java.util.Comparator;
import java.util.List;

public class MatchSorter {

    public static void sortMatchesByScoreSumAndStartTime(List<Match> matches) {
        matches.sort(Comparator
                .comparing((Match m) -> m.getHomeTeam().getScore() + m.getAwayTeam().getScore())
                .reversed() // Descending order of score sum
                .thenComparing(Match::getMatchStart) // Ascending order of matchStart
        );
    }

}
