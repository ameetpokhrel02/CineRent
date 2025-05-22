n<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/styles.css"/>
</head>
<body>

<form action="Register" method="POST" enctype="multipart/form-data">

<div class="form-group">
  <label for="id">ID:</label>
  <input type="number" id="id" name="id" required />
</div>

<div class="form-group">
  <label for="username">User name:</label>
  <input type="text" id="username" name="username" required />
</div>

<div class="form-group">
  <label for="email">Email:</label>
  <input type="email" id="email" name="email" required />
</div>

<div class="form-group">
  <label for="password">Password:</label>
  <input type="password" id="pw" name="password" required />
</div>

<div class="form-group">
  <label for="first_name">First Name:</label>
  <input type="text" id="first_name" name="first_name" required />
</div>

<div class="form-group">
  <label for="last_name">Last Name:</label>
  <input type="text" id="last_name" name="last_name" required />
</div>

<div class="form-group">
  <label for="tel">Phone Number:</label>
  <input type="tel" id="no" name="phone_number" required />
</div>

<div class="form-group">
  <label for="profile_image">Profile Picture:</label>
  <input type="file" id="profile" name="profile_image" required />
</div> 

<div class="form-group">
<label for="role">Select Role:</label>
<select id="role" name="role">
  <option value="">-- Select a Role --</option>
  <option value="admin">Admin</option>
  <option value="user">User</option>
</select>

</div>

<button type="submit">Submit</button>

<p>Already have an account? <a href="login.jsp">Login here</a></p>

</form>

<script>
document.querySelector("form").addEventListener("submit", function(e) {
    const pw = document.getElementById("pw").value;
    const error = document.getElementById("pw-error");
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;

    if (!regex.test(pw)) {
        e.preventDefault();
        error.textContent = "❌ Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.";
    } else {
        error.textContent = "";
    }
});

</script>


</body>
</html>
