package score;

import com.danyatheworst.match.score.Format;
import com.danyatheworst.match.score.MatchScore;
import com.danyatheworst.match.score.RegularGameScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.danyatheworst.match.score.State.ONGOING;
import static com.danyatheworst.match.score.State.PLAYER_ONE_WON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchTests {
    private MatchScore score;
    //best of 3
    @Test
    public void testPlayerOneWinsBestOfThreeMatch() {
        this.score = new MatchScore(Format.BEST_OF_THREE);
        for (int i = 0; i < 47; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }

        assertEquals(PLAYER_ONE_WON, this.score.pointWon(0));
    }

    //best of 5
    @Test
    public void testPlayerOneWinsBestOfFiveMatch() {
        this.score = new MatchScore(Format.BEST_OF_FIVE);
        for (int i = 0; i < 71; i++) {
            assertEquals(ONGOING, this.score.pointWon(0));
        }

        assertEquals(PLAYER_ONE_WON, this.score.pointWon(0));
    }

}