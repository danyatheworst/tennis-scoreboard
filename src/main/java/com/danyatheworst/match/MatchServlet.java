package com.danyatheworst.match;

import com.danyatheworst.utils.StringUtil;
import com.danyatheworst.utils.Validation;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "MatchServlet", urlPatterns = "/matches")
public class MatchServlet extends HttpServlet {
    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();
    private final Gson gson = new Gson();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String playerName1 = req.getParameter("playerName1");
        String playerName2 = req.getParameter("playerName2");

        CreateMatchRequestDto createMatchRequestDto = new CreateMatchRequestDto(
                StringUtil.removeExtraSpaces(playerName1), StringUtil.removeExtraSpaces(playerName2));
        Validation.validate(createMatchRequestDto);
        String createdMatchId = this.ongoingMatchService.createNewMatch(createMatchRequestDto);
        this.gson.toJson(createdMatchId, resp.getWriter());
    }
}
