package com.danyatheworst.match;

import com.danyatheworst.common.ThymeleafRenderer;
import com.danyatheworst.match.dto.MatchScoreViewDto;
import com.danyatheworst.match.score.*;
import com.danyatheworst.player.PlayerScoreDto;
import com.danyatheworst.utils.FormatUtil;
import com.danyatheworst.utils.PointUtil;
import com.danyatheworst.utils.StringUtil;
import com.danyatheworst.utils.Validation;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebServlet(name = "MatchScoreServlet", urlPatterns = "/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();
    private final FinishedMatchSaverService finishedMatchSaverService = new FinishedMatchSaverService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        if (uuid == null || uuid.isBlank()) {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        Match ongoingMatch = this.ongoingMatchService.getById(StringUtil.fromString(uuid));

        MatchScoreViewDto matchScoreViewDto = new MatchScoreViewDto(this.map(ongoingMatch));
        resp.setStatus(SC_OK);
        ThymeleafRenderer.fromRequest(req, resp)
                .addVariableToContext("matchScoreViewDto", matchScoreViewDto)
                .addVariableToContext("uuid", uuid)
                .build()
                .render("match-score");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        String pointWinnerId = req.getParameter("playerId");


        Validation.validatePresence(uuid, "uuid");
        Validation.validatePointWinnerId(pointWinnerId);
        Match ongoingMatch = this.ongoingMatchService.getById(StringUtil.fromString(uuid));

        //using calculation service seems quite redundant here
        State state = ongoingMatch.score.pointWon(Integer.parseInt(pointWinnerId));
        if (state != State.ONGOING) {
            int winnerId = state == State.PLAYER_ONE_WON ? 0 : 1;

            this.finishedMatchSaverService.save(ongoingMatch, winnerId, StringUtil.fromString(uuid));
            MatchScoreViewDto matchScoreViewDto = new MatchScoreViewDto(this.map(ongoingMatch));
            matchScoreViewDto.setWinnerId(winnerId);

            resp.setStatus(SC_CREATED);
            ThymeleafRenderer.fromRequest(req, resp)
                    .addVariableToContext("matchScoreViewDto", matchScoreViewDto)
                    .build()
                    .render("match-score");
        } else {
            resp.sendRedirect("match-score?uuid=" + uuid);
        }

    }
    
    private PlayerScoreDto map(Integer playerId, String playerName, MatchScore matchScore) {
        List<String> previousSets = new ArrayList<>();
        List<SetScore> setsHistory = matchScore.setsHistory;
        for (SetScore setScore : setsHistory) {
            previousSets.add(setScore.getPlayerScore(playerId).toString());
        }
        for (int i = previousSets.size(); i < FormatUtil.getNumeric(matchScore.format); i++) {
            previousSets.add("0");
        }
        return new PlayerScoreDto(
                playerId,
                playerName,
                matchScore.currentSet.getPlayerScore(playerId).toString(),
                PointUtil.getPointRepresentation(playerId, matchScore.currentSet.currentGame),
                previousSets
        );
    }

    private List<PlayerScoreDto> map(Match ongoingMatch) {
        List<PlayerScoreDto> players = new ArrayList<>();
        players.add(this.map(0, ongoingMatch.getPlayer1().getName(), ongoingMatch.score));
        players.add(this.map(1, ongoingMatch.getPlayer2().getName(), ongoingMatch.score));
        return players;
    }
}
