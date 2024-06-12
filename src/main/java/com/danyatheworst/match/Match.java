package com.danyatheworst.match;


import com.danyatheworst.match.score.MatchScore;
import com.danyatheworst.player.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    //    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player1_id", nullable = false)
    private Player player1;

    //    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player2_id", nullable = false)
    private Player player2;

    //    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id", nullable = false)
    private Player winner;

    @Transient
    private MatchScore score = new MatchScore();
}
