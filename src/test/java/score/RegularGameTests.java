package score;

import com.danyatheworst.match.score.RegularGameScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.danyatheworst.match.score.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularGameTests {
    private RegularGameScore score;
    @BeforeEach
    public void setUp() {
        this.score = new RegularGameScore();
    }

    @Test
    public void testPlayerOneFourPointsWinGame() {
        //first player wins 3 points => 15:0, 30:0, 40:0
        for (int i = 0; i < 3; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }

        //40:0 — first player wins one more point => first player wins the game
        assertEquals(PLAYER_ONE_WON, this.score.pointWon(0));
    }

    @Test
    public void testPlayerTwoFourPointsWinGame() {
        //second player wins 3 points => 15:0, 30:0, 40:0
        for (int i = 0; i < 3; i++) {
            assertEquals(ONGOING, this.score.pointWon(1));
        }

        //40:0 — second player wins one more point => second player wins the game
        assertEquals(PLAYER_TWO_WON, this.score.pointWon(1));
    }

    @Test
    public void testDeuce() {
        //both players win 3 points => 40:40
        for (int i = 0; i < 3; i++) {
            this.score.pointWon(0);
            this.score.pointWon(1);
        }

        //fourth point does not win the game => AD:40
        assertEquals(ONGOING, this.score.pointWon(0));

        //second player wins a point => 40:40
        assertEquals(ONGOING, this.score.pointWon(1));

        //first player wins a point => AD:40;
        //first player wins one more point => first player wins the game
        assertEquals(ONGOING, this.score.pointWon(0));
        assertEquals(PLAYER_ONE_WON, this.score.pointWon(0));
    }

    //todo test the throwing of exceptions (next() method)
}
