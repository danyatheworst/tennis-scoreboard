package score;

import com.danyatheworst.match.score.TieBreakScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.danyatheworst.match.score.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TieBreakTests {
    private TieBreakScore score;

    @BeforeEach
    public void setUp() {
        this.score = new TieBreakScore();
    }

    @Test
    public void testPlayerOneWinsWithDifferenceOfTwo() {
        //both players win 5 points
        for (int i = 0; i < 5; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
            assertEquals(ONGOING, this.score.pointWon(1));
        }

        //first player wins two more points => 7:5 — first player wins the tie-break;
        assertEquals(ONGOING, this.score.pointWon(0));
        assertEquals(PLAYER_ONE_WON, this.score.pointWon(0));
    }

    @Test
    public void testPlayerTwoWinsWithDifferenceOfTwo() {
        //both players win 11 points
        for (int i = 0; i < 11; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
            assertEquals(ONGOING, this.score.pointWon(1));
        }

        //second player wins two more points => 13:11 — second player wins the tie-break;
        assertEquals(ONGOING, this.score.pointWon(1));
        assertEquals(PLAYER_TWO_WON, this.score.pointWon(1));
    }

    @Test
    public void testTieBreakContinues() {
        //both players win 6 points
        for (int i = 0; i < 6; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
            assertEquals(ONGOING, this.score.pointWon(1));
        }

        //first player wins one more point => the first player has not won
        assertEquals(ONGOING, this.score.pointWon(0));
    }

    @Test
    public void testPointsLessThanSevenDifferenceGreaterEqualTwo() {
        //first player has < 7 points and does not win the tie-break with having difference of >= 2
        for (int i = 0; i < 6; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }
    }
}
