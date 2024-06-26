package com.danyatheworst.match;

import com.danyatheworst.ErrorResponseDto;
import com.danyatheworst.ThymeleafRenderer;
import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.exceptions.InvalidParameterException;
import com.danyatheworst.exceptions.NotFoundException;
import com.danyatheworst.match.score.*;
import com.danyatheworst.player.PlayerScoreDto;
import com.danyatheworst.utils.Validation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "MatchScoreServlet", urlPatterns = "/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();
    private final FinishedMatchSaverService finishedMatchSaverService = new FinishedMatchSaverService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");

        try {
            Validation.validatePresence(uuid, "uuid");
            Match ongoingMatch = this.ongoingMatchService.getById(fromString(uuid));

            MatchScoreViewDto matchScoreViewDto = new MatchScoreViewDto(this.get(ongoingMatch));
            ThymeleafRenderer.fromRequest(req, resp)
                    .addVariableToContext("matchScoreViewDto", matchScoreViewDto)
                    .addVariableToContext("uuid", uuid)
                    .build()
                    .render("match-score");
        } catch (NotFoundException e) {
            ThymeleafRenderer.renderFromRequest("not-found", req, resp);
        } catch (InvalidParameterException e) {
            ThymeleafRenderer.fromRequest(req, resp)
                    .addVariableToContext("errorResponseDto", new ErrorResponseDto(e.getMessage()))
                    .build()
                    .render("match-score");
        } catch (DatabaseOperationException e) {
            ThymeleafRenderer.renderFromRequest("internal-server", req, resp);
        }
        //todo: filter??
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        String pointWinnerId = req.getParameter("playerId");

        try {
            Validation.validatePresence(uuid, "uuid");
            Validation.validatePointWinnerId(pointWinnerId);
            Match ongoingMatch = this.ongoingMatchService.getById(fromString(uuid));

            //using calculation service seems quite redundant here
            State state = ongoingMatch.score.pointWon(Integer.parseInt(pointWinnerId));
            if (state != State.ONGOING) {
                int winnerId = state == State.PLAYER_ONE_WON ? 0 : 1;
                
                this.finishedMatchSaverService.save(ongoingMatch, winnerId, this.fromString(uuid));
                MatchScoreViewDto matchScoreViewDto = new MatchScoreViewDto(this.get(ongoingMatch));
                matchScoreViewDto.winnerId = winnerId;

                ThymeleafRenderer.fromRequest(req, resp)
                        .addVariableToContext("matchScoreViewDto", matchScoreViewDto)
                        .build()
                        .render("match-score");
            } else {
                resp.sendRedirect("match-score?uuid=" + uuid);
            }
        } catch (NotFoundException e) {
            ThymeleafRenderer.renderFromRequest("not-found", req, resp);
        } catch (InvalidParameterException e) {
            ThymeleafRenderer.fromRequest(req, resp)
                    .addVariableToContext("errorResponseDto", new ErrorResponseDto(e.getMessage()))
                    .build()
                    .render("match-score");
        } catch (DatabaseOperationException e) {
            ThymeleafRenderer.renderFromRequest("internal-server", req, resp);

        }
        //todo: filter??
    }

    private UUID fromString(String string) {
        try {
            if (string.length() != 36) {
                throw new InvalidParameterException(string + " match id is not valid.");
            }
            return UUID.fromString(string);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(string + " match id is not valid.");
        }
    }

    private PlayerScoreDto get(Integer playerId, String playerName, MatchScore matchScore) {
        List<String> previousSets = new ArrayList<>(3);
        List<SetScore> setsHistory = matchScore.setsHistory;
        for (SetScore setScore : setsHistory) {
            previousSets.add(setScore.getPlayerScore(playerId).toString());
        }
        for (int i = previousSets.size(); i < 3; i++) {
            previousSets.add("0");
        }
        return new PlayerScoreDto(
                playerId,
                playerName,
                matchScore.currentSet.getPlayerScore(playerId).toString(),
                this.getPoint(playerId, matchScore.currentSet.currentGame),
                previousSets
        );
    }

    private String getPoint(Integer id, GameScore<?> gameScore) {
        if (gameScore instanceof RegularGameScore) {
            return switch ((Point) gameScore.getPlayerScore(id)) {
                case ZERO -> "0";
                case FIFTEEN -> "15";
                case THIRTY -> "30";
                case FORTY -> "40";
                case ADVANTAGE -> "AD";
            };
        } else {
            return gameScore.getPlayerScore(id).toString();
        }
    }

    private List<PlayerScoreDto> get(Match ongoingMatch) {
        List<PlayerScoreDto> players = new ArrayList<>();
        players.add(this.get(0, ongoingMatch.getPlayer1().getName(), ongoingMatch.score));
        players.add(this.get(1, ongoingMatch.getPlayer2().getName(), ongoingMatch.score));
        return players;
    }
}
