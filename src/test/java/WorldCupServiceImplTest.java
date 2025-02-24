import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.worldcup.exception.IllegalScoreException;
import org.worldcup.exception.MatchAlreadyExistException;
import org.worldcup.exception.NoMatchFoundException;
import org.worldcup.impl.InMemoryStorageServiceImpl;
import org.worldcup.impl.WorldCupServiceImpl;
import org.worldcup.model.Match;
import org.worldcup.model.MatchStatus;
import org.worldcup.model.Team;
import org.worldcup.service.StorageService;
import org.worldcup.storage.InMemoryStorage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class WorldCupServiceImplTest {

    private WorldCupServiceImpl worldCupService;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        worldCupService = new WorldCupServiceImpl();
        storageService = new InMemoryStorageServiceImpl();
    }

    @AfterEach
    void clear() {
        InMemoryStorage.emptyStorage();
    }

    @Test
    void testStartMatch_Success() throws MatchAlreadyExistException {
        String homeTeam = "Team A";
        String awayTeam = "Team B";
        String matchId = worldCupService.startMatch(homeTeam, awayTeam);

        Match match = storageService.getMatch(matchId);

        assertNotNull(matchId);
        assertNotNull(match);
    }

    @Test
    void testStartMatch_MatchAlreadyExist() throws MatchAlreadyExistException {
        String homeTeam = "Team A";
        String awayTeam = "Team B";
        worldCupService.startMatch(homeTeam, awayTeam);

        assertThrows(MatchAlreadyExistException.class, () -> {
            worldCupService.startMatch(homeTeam, awayTeam);
        });

    }

    @Test
    void testUpdateMatchScore_Success() throws NoMatchFoundException, IllegalScoreException, MatchAlreadyExistException {
        String homeTeam = "Team A";
        String awayTeam = "Team B";
        String matchId = worldCupService.startMatch(homeTeam, awayTeam);

        assertDoesNotThrow(() -> {
            worldCupService.updateMatchScore(matchId, 0, 1);
        });
        //score is 0 1, VAR decided its offside and not valid goal
        assertDoesNotThrow(() -> {
            worldCupService.updateMatchScore(matchId, 0, 0);
        });
        //home team punish them for offside goal
        assertDoesNotThrow(() -> {
            worldCupService.updateMatchScore(matchId, 1, 0);
        });
    }

    @Test
    void testUpdateMatchScore_NoMatchFound() {
        String matchId = "match123";

        assertThrows(NoMatchFoundException.class, () -> worldCupService.updateMatchScore(matchId, 2, 3));
    }

    @Test
    void testFinishMatch_Success() throws NoMatchFoundException, MatchAlreadyExistException {
        String homeTeam = "Team A";
        String awayTeam = "Team B";
        String matchId = worldCupService.startMatch(homeTeam, awayTeam);

        worldCupService.finishMatch(matchId);

        Match match = storageService.getMatch(matchId);

        assertEquals(MatchStatus.FINISHED, match.getMatchStatus());
        assertNotNull(match.getMatchFinish());
    }

    @Test
    void testFinishMatch_NoMatchFound() {
        String matchId = "match123";

        assertThrows(NoMatchFoundException.class, () -> worldCupService.finishMatch(matchId));
    }

    @Test
    void testGetAllLiveMatches_Success() throws MatchAlreadyExistException, NoMatchFoundException, IllegalScoreException {
        //one test to rule them all
        //all teams are selected randomly and have no connection to real events

        Team homeTeam = new Team("Croatia", 0);
        Team awayTeam = new Team("France", 0);
        Match match1 = new Match(homeTeam, awayTeam, LocalDateTime.now(), MatchStatus.LIVE);
        String match1Id = storageService.addNewMatch(match1);
        worldCupService.updateMatchScore(match1Id, 1, 0);
        worldCupService.updateMatchScore(match1Id, 2, 0);
        worldCupService.updateMatchScore(match1Id, 3, 0);

        Team homeTeam2 = new Team("Switzerland", 0);
        Team awayTeam2 = new Team("Brazil", 0);
        Match match2 = new Match(homeTeam2, awayTeam2, LocalDateTime.now().plusMinutes(10), MatchStatus.LIVE);
        String match2Id = storageService.addNewMatch(match2);
        worldCupService.updateMatchScore(match2Id, 1, 0);
        worldCupService.updateMatchScore(match2Id, 2, 0);
        worldCupService.updateMatchScore(match2Id, 3, 0);

        Team homeTeam3 = new Team("Germany", 0);
        Team awayTeam3 = new Team("USA", 0);
        Match match3 = new Match(homeTeam3, awayTeam3, LocalDateTime.now().plusMinutes(20), MatchStatus.LIVE);
        String match3Id = storageService.addNewMatch(match3);
        worldCupService.updateMatchScore(match3Id, 1, 0);


        List<Match> allLiveMatches = worldCupService.getAllLiveMatches();

        assertEquals(3, allLiveMatches.size());
        assertEquals(match2Id, allLiveMatches.get(0).getId());
        assertEquals(match1Id, allLiveMatches.get(1).getId());
        assertEquals(match3Id, allLiveMatches.get(2).getId());

        //after some score updates

        worldCupService.updateMatchScore(match1Id, 4, 0);
        allLiveMatches = worldCupService.getAllLiveMatches();

        assertEquals(3, allLiveMatches.size());
        assertEquals(match1Id, allLiveMatches.get(0).getId());
        assertEquals(match2Id, allLiveMatches.get(1).getId());
        assertEquals(match3Id, allLiveMatches.get(2).getId());

        worldCupService.finishMatch(match1Id);
        allLiveMatches = worldCupService.getAllLiveMatches();

        assertEquals(2, allLiveMatches.size());
        assertEquals(match2Id, allLiveMatches.get(0).getId());
        assertEquals(match3Id, allLiveMatches.get(1).getId());

    }

    @Test
    void testGetAllFinishedMatches_Success() throws MatchAlreadyExistException, NoMatchFoundException {
        String matchId = worldCupService.startMatch("Spain", "Argentina");
        worldCupService.finishMatch(matchId);

        List<Match> allLiveMatches = worldCupService.getAllFinishedMatches();

        assertEquals(1, allLiveMatches.size());
    }

}
