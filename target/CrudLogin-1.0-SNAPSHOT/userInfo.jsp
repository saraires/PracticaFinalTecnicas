
<%-- 
    Document   : userInfo
    Created on : 25/11/2024, 1:49:33 p. m.
    Author     : sarai
--%>

<%@page import="Entities.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Info Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Styles/toast.css">
        <link rel="stylesheet" href="Styles/userInfo.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Jost:wght@500display=swap">
    </head>
    <body>
        <%
            User user = (User) request.getAttribute("user");
        %>
        <div class="main">
            <div class="userInfo">
                <h2>User Information</h2>
                <form action="SvInformation" method="post">
                    <div class="input-group">
                        <label for="fullname">Full Name</label>
                        <input type="text" id="fullname" name="fullname" value="<%= user.getFullname()%>" required>
                    </div>
                    <div class="input-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" value="<%= user.getEmail()%>" readonly>
                    </div>
                    <div class="input-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" value="<%= user.getUsername()%>" readonly>
                    </div>
                    <div class="input-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" value="<%= user.getPassword()%>" required>
                    </div>
                    <button type="submit" name="save-changes" class="save-changes">Save Changes</button>
                </form>
            </div>
        </div>
        <div id="toast-container" style="position: fixed; bottom: 20px; right: 20px; z-index: 1000;"></div>

        <script>
            function showToast(message, type = "success") {
                const toastContainer = document.getElementById("toast-container");

                const toast = document.createElement("div");
                toast.className = `toast ${type}`;
                toast.textContent = message;

                toastContainer.appendChild(toast);

                setTimeout(() => {
                    toast.remove();
                }, 3500);
            }
        </script>
        <%
            String toastMessage = (String) request.getAttribute("toastMessage");
            String toastType = (String) request.getAttribute("toastType");
            if (toastMessage != null && toastType != null) {
        %>
        <script>
            showToast("<%= toastMessage%>", "<%= toastType%>");
        </script>
        <%
            }
        %>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const fullnameInput = document.getElementById("fullname");
                const passwordInput = document.getElementById("password");
                const saveButton = document.querySelector(".save-changes");

                saveButton.disabled = true;

                function checkChanges() {
                    const originalFullname = "<%= user.getFullname()%>";
                    const originalPassword = "<%= user.getPassword()%>";

                    if (fullnameInput.value !== originalFullname || passwordInput.value !== originalPassword) {
                        saveButton.disabled = false;
                    } else {
                        saveButton.disabled = true;
                    }
                }

                fullnameInput.addEventListener("input", checkChanges);
                passwordInput.addEventListener("input", checkChanges);
            });
        </script>
    </body>
</html>