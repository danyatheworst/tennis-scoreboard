<%@ page import="com.danyatheworst.match.MatchesResponseDto" %><%
    MatchesResponseDto matchesResponseDto = (MatchesResponseDto) request.getAttribute("matches");
    long totalCount = matchesResponseDto.totalCount;
    int currentPage = matchesResponseDto.currentPage;
    int pageSize = matchesResponseDto.pageSize;
    int lastPage = (int) ((totalCount + pageSize - 1) / pageSize);

    int leftMost = currentPage - 2;
    boolean leftDots = false;

    int rightMost = currentPage + 2;
    boolean rightDots = false;

    if (leftMost < 1) {
        leftMost = 1;
    }

    if (leftMost - 1 > 1) {
        leftDots = true;
    }
    if (lastPage - rightMost > 1) {
        rightDots = true;
    }

    if (rightMost > lastPage) {
        rightMost = lastPage;
    }

    String href = "?page=";
%>
<div class="pagination">
    <% if (currentPage != 1) { %>
    <a href="<%= href + (currentPage - 1) %>">ARROW LEFT</a>
    <% } %>
    <% if (leftMost != 1) { %>
    <a href="<%= href + 1 %>">1</a>
   <% } %>
    <% if (leftDots) { %>
    <span>left-dots</span>
    <% } %>
    <%
        for (int i = leftMost; i < currentPage; i++) {
    %>
    <a href="<%= href + i %>"><%= i %></a>
    <% } %>
    <a class="active" style="background: cornflowerblue"><%= currentPage %></a>
    <%
        for (int i = currentPage + 1; i <= rightMost; i++) {
    %>
    <a href="<%=href + i%>"><%= i %></a>
    <% } %>
    <% if (rightDots) { %>
    <span>right-dots</span>
    <% } %>
    <% if (rightMost != lastPage) { %>
    <a href="<%= href + lastPage %>"><%= lastPage %></a>
    <% } %>
    <% if (currentPage != lastPage) { %>
    <a href="<%= href + (currentPage + 1) %>">ARROW RIGHT</a>
    <% } %>
</div>
