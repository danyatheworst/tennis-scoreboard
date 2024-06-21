<%@ page import="com.danyatheworst.match.MatchesResponseDto" %>
<%@ page import="com.danyatheworst.match.Match" %>
<%@ page import="java.util.List" %>
<%@ page import="com.danyatheworst.player.Player" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Matches</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .winner {
            color: green;
            font-weight: bold;
        }
        .no-matches {
            font-size: 18px;
            color: red;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h2>Match List</h2>
<%
    MatchesResponseDto matchesResponseDto = (MatchesResponseDto) request.getAttribute("matches");
    List<Match> matches = matchesResponseDto.matches;

    if (matches != null) {
%>
<table>
    <thead>
    <tr>
        <th>Player 1</th>
        <th>Player 2</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Match match : matches) {
            Player player1 = match.player1;
            Player player2 = match.player2;
            Player winner = match.winner;
    %>
    <tr>
        <td>
            <%= player1.name %>
            <% if (player1.ID.equals(winner.ID)) { %>
            <span class="winner">✔</span>
            <% } %>
        </td>
        <td>
            <%= player2.name %>
            <% if (player2.ID.equals(winner.ID)) { %>
            <span class="winner">✔</span>
            <% } %>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<br/>
<br/>
<br/>
<br/>
<jsp:include page="pagination.jsp" />
<%
} else {
%>
<div class="no-matches">no matches</div>
<%
    }
%>
</body>
</html>
