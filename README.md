# World Cup Match Tracker

This project is a simple in-memory storage system for tracking World Cup matches. It allows users to start matches, update scores, and retrieve live or finished matches.

## Features

- Start a new match between two teams.
- Update match scores dynamically.
- Retrieve live and finished matches.
- Store match details in an in-memory database.

## Installation & Setup

1. Build library
2. Implement it in your project
3. Use it with smile

## Usage

### Start a Match
```java
String matchId = worldCupService.startMatch("Argentina", "Brazil");
```

### Update Match Score
```java
worldCupService.updateMatchScore(matchId, 2, 1);
```

### Finish a Match
```java
worldCupService.finishMatch(matchId);
```

### Retrieve Live Matches
```java
List<Match> liveMatches = worldCupService.getAllLiveMatches();
```
