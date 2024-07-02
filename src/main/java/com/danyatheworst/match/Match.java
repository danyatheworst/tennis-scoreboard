package com.danyatheworst.match;


import com.danyatheworst.match.score.MatchScore;
import com.danyatheworst.player.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long ID;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    public Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false)
    public Player player2;

    @ManyToOne
    @JoinColumn(name = "winner_id", nullable = false)
    public Player winner;

    @Transient
    public MatchScore score;
}
