package com.danyatheworst.match;

import com.danyatheworst.common.ThymeleafRenderer;
import com.danyatheworst.common.Paginated;
import com.danyatheworst.exceptions.InvalidParameterException;
import com.danyatheworst.match.dto.MatchesResponseDto;
import com.danyatheworst.utils.StringUtil;
import com.danyatheworst.utils.Validation;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(name = "MatchesServlet", urlPatterns = "/matches")
public class MatchesServlet extends HttpServlet {
    private final static int DEFAULT_PAGE_NUMBER = 1;
    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static String DEFAULT_FILTER_BY_PLAYER_NAME = "";
    private final MatchRepository matchRepository = new MatchRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = req.getParameter("page");
        String playerName = req.getParameter("filter_by_player_name");
        int pageNumber = parsePageNumberOrGetDefault(page);
        playerName = StringUtil.removeExtraSpaces(parsePlayerNameOrGetDefault(playerName));

        Paginated<Match> paginatedMatches = this.matchRepository.find(pageNumber, DEFAULT_PAGE_SIZE, playerName);
        MatchesResponseDto matchesResponseDto = new MatchesResponseDto(
                paginatedMatches.result, paginatedMatches.totalCount, pageNumber, DEFAULT_PAGE_SIZE
        );
        resp.setStatus(SC_OK);
        ThymeleafRenderer.fromRequest(req, resp)
                .addVariableToContext("matchesResponseDto", matchesResponseDto)
                .build()
                .render("matches");
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
