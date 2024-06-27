package com.danyatheworst.filters;

import com.danyatheworst.common.ErrorResponseDto;
import com.danyatheworst.common.ThymeleafRenderer;
import com.danyatheworst.exceptions.DatabaseOperationException;
import com.danyatheworst.exceptions.NotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebFilter(urlPatterns = "/*")
public class ExceptionHandlingFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            super.doFilter(req, res, filterChain);
        } catch (NotFoundException e) {
            res.setStatus(SC_NOT_FOUND);
            ThymeleafRenderer.fromRequest(req, res)
                    .addVariableToContext("errorResponseDto", new ErrorResponseDto(e.getMessage()))
                    .build()
                    .render("not-found");
        } catch (DatabaseOperationException e) {
            res.setStatus(SC_INTERNAL_SERVER_ERROR);
            ThymeleafRenderer.fromRequest(req, res)
                    .addVariableToContext("errorResponseDto", new ErrorResponseDto(e.getMessage()))
                    .build()
                    .render("internal-server");
        }
    }
}