<%@ page import="com.danyatheworst.match.MatchScoreViewDto" %>
<%@ page import="com.danyatheworst.player.PlayerScoreDto" %>
<%@ page import="com.danyatheworst.ErrorResponseDto" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String uuid = request.getParameter("uuid");
    MatchScoreViewDto matchScore = (MatchScoreViewDto) request.getAttribute("matchScore");
    ErrorResponseDto error = (ErrorResponseDto) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Match Score</title>
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/match-score.css">
    <link rel="stylesheet" href="css/error.css">
</head>
<body>
<% if (error != null) { %>
<div class="error-message">
    <p><%= error.message %></p>
</div>
<% } else { %>
<div class="scoreboard">
    <% for (PlayerScoreDto player : matchScore.players) { %>
    <div class="player-row">
        <% for (String set : player.previousSets) { %>
        <div class="score-box"><%= set %></div>
        <% } %>
        <div class="player-name"><%= player.name %></div>
        <div class="score-box"><%= player.currentGame %></div>
        <div class="score-box"><%= player.currentPoint %></div>
        <form action="match-score" method="post">
            <input type="hidden" name="uuid" value="<%= uuid %>">
            <input type="hidden" name="playerId" value="<%= player.id %>">
            <input type="submit" class="point-button" value="+" <%= (matchScore.winnerId != null ? "disabled" : "") %>>
        </form>
    </div>
    <% } %>
</div>
<% } %>
</body>
</html>
