package org.worldcup.util;

import org.worldcup.model.Match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchSorter {

    public static List<Match> sortMatchesByScoreSumAndStartTime(List<Match> matches) {
        List<Match> sortedList = new ArrayList<>(matches);
        sortedList.sort(Comparator
                .comparingInt((Match m) -> m.getHomeTeam().getScore() + m.getAwayTeam().getScore()) // Sum of scores
                .reversed()
                .thenComparing(Match::getMatchStart, Comparator.reverseOrder())
        );
        return sortedList;
    }

}
