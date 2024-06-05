package com.danyatheworst.match;

import com.danyatheworst.ErrorResponseDto;
import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.exceptions.InvalidParameterException;
import com.danyatheworst.utils.StringUtil;
import com.danyatheworst.utils.Validation;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "MatchServlet", urlPatterns = "/new-match")
public class NewMatchServlet extends HttpServlet {
    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doGet new match servlet");
        req.getRequestDispatcher("new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String playerName1 = req.getParameter("playerName1");
        String playerName2 = req.getParameter("playerName2");

        CreateMatchRequestDto createMatchRequestDto = new CreateMatchRequestDto(
                StringUtil.removeExtraSpaces(playerName1), StringUtil.removeExtraSpaces(playerName2));
        try {
            Validation.validate(createMatchRequestDto);

            String createdMatchUuid = this.ongoingMatchService.createNewMatch(createMatchRequestDto).toString();
            resp.sendRedirect("match-score?uuid=" + createdMatchUuid);
        } catch (InvalidParameterException | DatabaseOperationException e) {
            req.setAttribute("error", new ErrorResponseDto(e.getMessage()));
            req.getRequestDispatcher("new-match.jsp").forward(req, resp);
        }
    }
}
