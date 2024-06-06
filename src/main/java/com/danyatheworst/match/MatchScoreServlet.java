package com.danyatheworst.match;

import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.exceptions.InvalidParameterException;
import com.danyatheworst.exceptions.NotFoundException;
import com.danyatheworst.utils.Validation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "MatchScoreServlet", urlPatterns = "/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        try {
            Validation.validatePresence(uuid, "match id");
            Match ongoingMatch = this.ongoingMatchService.getById(fromString(uuid));

            req.setAttribute("uuid", uuid);
            req.setAttribute("playerName1", ongoingMatch.getPlayer1());
            req.setAttribute("playerName2", ongoingMatch.getPlayer2());
            req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DatabaseOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    private UUID fromString(String string) {
        try {
            return UUID.fromString(string);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Match id " + string + " is not valid");
        }
    }
}
