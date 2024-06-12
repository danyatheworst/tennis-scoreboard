package score;

import com.danyatheworst.match.score.MatchScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.danyatheworst.match.score.State.ONGOING;
import static com.danyatheworst.match.score.State.PLAYER_ONE_WON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchTests {
    private MatchScore match;

    @BeforeEach
    public void setUp() {
        this.match = new MatchScore();
    }

    @Test
    public void testPlayerOneWinsMatchTwoZero() {
        //6 5  |40
        //0 0  |0
        for (int i = 0; i < 47; i++) {
            assertEquals(ONGOING, this.match.pointWon(0));
        }
        //6 6
        //0 0
        //sets: 2:0
        assertEquals(PLAYER_ONE_WON, this.match.pointWon(0));
    }

    @Test
    public void testPlayerOneWinsMatchTwoOne() {
        //6
        //0
        //sets: 1:0
        for (int i = 0; i < 24; i++) {
            assertEquals(ONGOING, this.match.pointWon(0));
        }

        //6 0
        //0 6
        //sets: 1:1
        for (int i = 0; i < 24; i++) {
            assertEquals(ONGOING, this.match.pointWon(1));
        }

        //6 0 5  |40
        //0 6 0  |0
        //sets: 1:1
        for (int i = 0; i < 23; i++) {
            assertEquals(ONGOING, this.match.pointWon(0));
        }

        //6 0 6
        //0 6 0
        //sets: 2:1
        assertEquals(PLAYER_ONE_WON, this.match.pointWon(0));
    }

    @Test
    void testPlayerOneWinsMatchTwoOneWithTieBreaks() {
        //6
        //6
        this.tieBreakBegins();

        //first player wins 6 tie-breaks points in a row
        //6  |6
        //6  |0
        for (int i = 0; i < 6; i++) {
            assertEquals(ONGOING, this.match.pointWon(0));
        }

        //first player wins one more tie-break point => tie-break score is 7:0 => wins the set
        //7
        //6
        assertEquals(ONGOING, this.match.pointWon(0));

        //7 6
        //6 6
        this.tieBreakBegins();

        //7 6  |6
        //6 6  |0
        for (int i = 0; i < 6; i++) {
            assertEquals(ONGOING, this.match.pointWon(0));
        }

        //7 7
        //6 6
        assertEquals(PLAYER_ONE_WON, this.match.pointWon(0));
    }

    private void tieBreakBegins() {
        //5
        //5
        for (int i = 0; i < 20; i++) {
            assertEquals(ONGOING, this.match.pointWon(0));
        }
        for (int i = 0; i < 20; i++) {
            assertEquals(ONGOING, this.match.pointWon(1));
        }
        //6
        //6
        for (int i = 0; i < 4; i++) {
            assertEquals(ONGOING, this.match.pointWon(0));
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(ONGOING, this.match.pointWon(1));
        }
    }
}
