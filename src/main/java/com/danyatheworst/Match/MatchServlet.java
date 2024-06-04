package com.danyatheworst.Match;

import com.danyatheworst.utils.StringUtil;
import com.danyatheworst.utils.Validation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "MatchServlet", urlPatterns = "/matches")
public class MatchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName1 = req.getParameter("playerName1");
        String playerName2 = req.getParameter("playerName2");

        CreateMatchRequestDto createMatchRequestDto = new CreateMatchRequestDto(
                StringUtil.removeExtraSpaces(playerName1), StringUtil.removeExtraSpaces(playerName2));
        Validation.validate(createMatchRequestDto);
        
    }
}
