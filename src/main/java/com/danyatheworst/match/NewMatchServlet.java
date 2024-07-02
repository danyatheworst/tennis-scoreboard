package com.danyatheworst.match;

import com.danyatheworst.common.ErrorResponseDto;
import com.danyatheworst.common.ThymeleafRenderer;
import com.danyatheworst.exceptions.InvalidParameterException;
import com.danyatheworst.match.dto.CreateMatchRequestDto;
import com.danyatheworst.match.score.Format;
import com.danyatheworst.utils.StringUtil;
import com.danyatheworst.utils.Validation;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@WebServlet(name = "MatchServlet", urlPatterns = "/new-match")
public class NewMatchServlet extends HttpServlet {
    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ThymeleafRenderer.renderFromRequest("new-match", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String playerName1 = req.getParameter("playerName1");
        String playerName2 = req.getParameter("playerName2");
        String format = req.getParameter("format");
        CreateMatchRequestDto createMatchRequestDto = new CreateMatchRequestDto(
                StringUtil.removeExtraSpaces(playerName1),
                StringUtil.removeExtraSpaces(playerName2),
                this.parse(format));
        try {
            Validation.validate(createMatchRequestDto);

            String createdMatchUuid = this.ongoingMatchService.createNewMatch(createMatchRequestDto).toString();
            resp.sendRedirect("match-score?uuid=" + createdMatchUuid);
        } catch (InvalidParameterException e) {
            resp.setStatus(SC_BAD_REQUEST);
            ThymeleafRenderer.fromRequest(req, resp)
                    .addVariableToContext("errorResponseDto", new ErrorResponseDto(e.getMessage()))
                    .build()
                    .render("new-match");
        }
    }

    protected Format parse(String format) {
        try {
            return Format.valueOf(format);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Format parameter can be 'BEST_OF_THREE' OR 'BEST_OF_FIVE'");
        }
    }
}
