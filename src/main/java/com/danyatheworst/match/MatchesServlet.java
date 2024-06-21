package com.danyatheworst.match;

import com.danyatheworst.common.Paginated;
import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.exceptions.InvalidParameterException;
import com.danyatheworst.utils.StringUtil;
import com.danyatheworst.utils.Validation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "MatchesServlet", urlPatterns = "/matches")
public class MatchesServlet extends HttpServlet {
    private final static int DEFAULT_PAGE_NUMBER = 1;
    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static String DEFAULT_FILTER_BY_PLAYER_NAME = "";
    private final MatchRepository matchRepository = new MatchRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        String playerName = req.getParameter("filter_by_player_name");

        int pageNumber = parsePageNumberOrGetDefault(page);
        playerName = StringUtil.removeExtraSpaces(parsePlayerNameOrGetDefault(playerName));
        try {
            Paginated<Match> paginatedMatches = this.matchRepository.findBy(pageNumber, DEFAULT_PAGE_SIZE, playerName);

            int lastPageNumber = (int) ((paginatedMatches.totalCount + DEFAULT_PAGE_SIZE - 1) / DEFAULT_PAGE_SIZE);
            MatchesResponseDto matchesResponseDto = new MatchesResponseDto(
                    paginatedMatches.result, paginatedMatches.totalCount, pageNumber, lastPageNumber
            );
            req.setAttribute("matches", matchesResponseDto);
            //req.getRequestDispatcher("/matches.jsp").forward(req, resp);
        } catch (DatabaseOperationException e) {

        }
    }


    private int parsePageNumberOrGetDefault(String page) {
        try {
            int pageNumber = Integer.parseInt(page);
            return Math.max(pageNumber, DEFAULT_PAGE_NUMBER);
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_NUMBER;
        }
    }

    private String parsePlayerNameOrGetDefault(String playerName) {
        try {
            Validation.validatePlayerName(playerName, "filter_by_player_name");
            return playerName;
        } catch (InvalidParameterException e) {
            return DEFAULT_FILTER_BY_PLAYER_NAME;
        }
    }
}
