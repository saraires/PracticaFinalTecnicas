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
import org.bson.types.ObjectId;

/**
 *
 * @author sarai
 */
public class SvLogin extends HttpServlet {

    RequestDispatcher dispatcher;

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
        }
        if (login != null) {
            handleLogin(request, response);
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
                throw new IllegalArgumentException("Passwords do not match! D:");
            }

            UsersRepository userRepo = new UsersRepository();
            if (userRepo.getUserID(email) != null || userRepo.getUserID(username) != null) {
                throw new IllegalArgumentException("Email or username already exists! D:");
            }

            User newUser = new User(name, email, username, password);
            userRepo.addUser(newUser);

            request.setAttribute("user", newUser);
            dispatcher = request.getRequestDispatcher("userInfo.jsp");
            dispatcher.forward(request, response);

        } catch (IllegalArgumentException e) {

            request.setAttribute("toastMessage", "El correo y/o usuario ya estan registrados, intenta otros");
            request.setAttribute("toastType", "error");
            dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String loginUser = request.getParameter("loginUser");
            String password = request.getParameter("pass");

            UsersRepository userRepo = new UsersRepository();
            Document user = userRepo.login(loginUser, password);

            if (user.getObjectId("_id") == null) {
                throw new IllegalArgumentException("Invalid email/username or password!");
            }

            request.setAttribute("user", new User(
                    user.getString("fullname"),
                    user.getString("email"),
                    user.getString("username"),
                    user.getString("password")
            ));
            System.out.println("Usuario: " + user);
            dispatcher = request.getRequestDispatcher("userInfo.jsp");
            dispatcher.forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("toastMessage", "IllegalArgumentException");
            request.setAttribute("toastType", "error");
            dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            request.setAttribute("toastMessage", "ServletException");
            request.setAttribute("toastType", "error");
        } catch (IOException e) {
            request.setAttribute("toastMessage", "IOException");
            request.setAttribute("toastType", "error");
        }
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
