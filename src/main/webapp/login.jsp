<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>

<form action="Login" method="POST">
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required />
    </div>

    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required />
    </div>

    <button type="submit">Login</button>
</form>
<% 
    String error = (String) request.getAttribute("errorMessage");
    if (error != null) {
%>
    <div style="color: red; font-weight: bold;"><%= error %></div>
<% 
    } 
%>

</body>
</html>