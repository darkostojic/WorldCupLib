package org.worldcup.model;

import java.time.LocalDateTime;

public class Match {

    private String id;
    private Team homeTeam;
    private Team awayTeam;
    private LocalDateTime matchStart;
    private LocalDateTime matchFinish;
    private MatchStatus matchStatus;

    public Match() {
    }

    public Match(Team homeTeam, Team awayTeam, LocalDateTime matchStart, MatchStatus matchStatus) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchStart = matchStart;
        this.matchStatus = matchStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDateTime getMatchStart() {
        return matchStart;
    }

    public void setMatchStart(LocalDateTime matchStart) {
        this.matchStart = matchStart;
    }

    public LocalDateTime getMatchFinish() {
        return matchFinish;
    }

    public void setMatchFinish(LocalDateTime matchFinish) {
        this.matchFinish = matchFinish;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }
}
