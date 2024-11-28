<%-- 
    Document   : index
    Created on : 25/11/2024, 11:02:23 a. m.
    Author     : sarai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Styles/toast.css">
        <link rel="stylesheet" href="Styles/index.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Jost:wght@500display=swap">
    </head>
    <body>
        <div class="main">
            <input type="checkbox" id="chk" aria-hidden="true">

            <div class="signup">
                <form action="SvLogin" method="post">
                    <label for="chk" aria-hidden="true">Sign up</label>
                    <input type="text" name="name" placeholder="Full Name" required="">
                    <input type="email" name="email" placeholder="Email" required="">
                    <input type="txt" name="registerUser" placeholder="Username" required="">
                    <input type="password" name="pass" placeholder="Password" required="">
                    <input type="password" name="confirmPass" placeholder="Re-enter password" required="">
                    <button name = "signUp">Sign up</button>
                </form>
            </div>

            <div class="login">
                <form action="SvLogin" method="post">
                    <label for="chk" aria-hidden="true">Login</label>
                    <input type="text" name="loginUser" placeholder="Email" required="">
                    <input type="password" name="pass" placeholder="Password" required="">
                    <button name="login">Login</button>
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
    </body>
</html>
