/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.crudlogin;

import Entities.User;
import Persistance.UsersRepository;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;

/**
 *
 * @author sarai
 */
public class SvLogin extends HttpServlet {

    private static final String ERROR_MESSAGE = "toastMessage";
    private static final String ERROR_TYPE = "toastType";
    private static final String ERROR_PAGE = "index.jsp";
    private static final String SUCCESS_PAGE = "userInfo.jsp";

    private final UsersRepository userRepo = new UsersRepository();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String signUp = request.getParameter("signUp");
        String login = request.getParameter("login");

        if (signUp != null) {
            handleSignUp(request, response);
        } else if (login != null) {
            handleLogin(request, response);
        } else {
            sendError(request, response, "Invalid action! D:", "error");
        }

    }

    private void handleSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String username = request.getParameter("registerUser");
            String password = request.getParameter("pass");
            String confirmPassword = request.getParameter("confirmPass");

            if (!password.equals(confirmPassword)) {
                sendError(request, response, "Passwords do not match! D:", "error");
            }

            if (userRepo.getUserID(email) != null || userRepo.getUserID(username) != null) {
                sendError(request, response, "Email or username already exists! D:", "error");
            }

            User newUser = new User(name, email, username, password);
            userRepo.addUser(newUser);

            request.setAttribute("user", newUser);
            forwardToPage(request, response, SUCCESS_PAGE);

        } catch (IllegalArgumentException e) {
            sendError(request, response, "Email or username already exists!", "error");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String loginUser = request.getParameter("loginUser");
            String password = request.getParameter("pass");

            Document user = userRepo.login(loginUser, password);

            if (user == null || user.getObjectId("_id") == null) {
                sendError(request, response, "Invalid email/username or password!", "error");
            }

            User loggedUser = new User(
                    user.getString("fullname"),
                    user.getString("email"),
                    user.getString("username"),
                    user.getString("password")
            );

            request.setAttribute("user", loggedUser);
            forwardToPage(request, response, SUCCESS_PAGE);

        } catch (IllegalArgumentException e) {
            sendError(request, response, "Invalid username/email or password!", "error");
        }
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response, String message, String type) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, message);
        request.setAttribute(ERROR_TYPE, type);
        forwardToPage(request, response, ERROR_PAGE);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
