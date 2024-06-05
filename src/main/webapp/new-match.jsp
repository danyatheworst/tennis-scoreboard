<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <form id="new-match" action="new-match" method="post">
    <label for="playerName1">player name 1:</label>
    <input type="text" id="playerName1" name="playerName1" required><br><br>

    <label for="playerName2">player name 2:</label>
    <input type="text" id="playerName2" name="playerName2" required><br><br>

    <button type="submit">Submit</button>
    <p>${error.message}</p>
  </form>
  </body>
</html>

