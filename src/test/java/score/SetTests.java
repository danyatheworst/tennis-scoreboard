package score;

import com.danyatheworst.match.score.SetScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.danyatheworst.match.score.State.ONGOING;
import static com.danyatheworst.match.score.State.PLAYER_ONE_WON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetTests {
    private SetScore score;

    @BeforeEach
    public void setUp() {
        this.score = new SetScore();
    }

    @Test
    public void testPlayerOneWinsSet() {
        //first player wins 5 games and 40 points
        for (int i = 0; i < 23; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }

        //first player wins one more point => wins a game => wins a set => score is 6:0 in games
        assertEquals(PLAYER_ONE_WON, this.score.pointWon(0));
    }

    @Test
    void testSetIsNotFinishedIfDifferenceLessThanTwo() {
        //first player wins 5 games => score is 5:0 in games (0:0 points)
        for (int i = 0; i < 20; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }
        //second player wins 5 games => score is 5:5 in games (0:0 points)
        for (int i = 0; i < 20; i++) {
            assertEquals(ONGOING, this.score.pointWon(1));
        }

        //first player wins 4 points => wins a game but does not win a set => score is 6:5 in games
        for (int i = 0; i < 4; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }
    }

    @Test
    void testTheStartOfTimeBreak() {
        //first player wins 5 games => score is 5:0 in games (0:0 points)
        for (int i = 0; i < 20; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }
        //second player wins 5 games => score is 5:5 in games (0:0 points)
        for (int i = 0; i < 20; i++) {
            assertEquals(ONGOING, this.score.pointWon(1));
        }

        //first player wins a game => 6:5
        for (int i = 0; i < 4; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }
        //second player wins a game => 6:6
        for (int i = 0; i < 4; i++) {
            assertEquals(ONGOING, this.score.pointWon(1));
        }

        //tie-break has been started and both players win 1 tie-break points (set is still ongoing)
        for (int i = 0; i < 1; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
            assertEquals(ONGOING, this.score.pointWon(1));
        }
    }
}
