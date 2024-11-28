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

/**
 *
 * @author sarai
 */
public class SvInformation extends HttpServlet {

    private static final String SUCCESS_PAGE = "userInfo.jsp";
    private static final String ERROR_PAGE = "userInfo.jsp";
    private static final String TOAST_MESSAGE = "toastMessage";
    private static final String TOAST_TYPE = "toastType";

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            // Campos a updatear
            String name = request.getParameter("fullname");
            String password = request.getParameter("password");

            // Campos necesarios para el update
            String username = request.getParameter("username");
            String email = request.getParameter("email");

            String id = userRepo.getUserID(username);

            userRepo.updateUser(id, name, password);

            User updatedUser = new User(name, email, username, password);
            request.setAttribute("user", updatedUser);

            sendSuccess(request, response, "User successfully updated!");

        } catch (IllegalArgumentException e) {
            sendError(request, response, "We couldn't update your user, try again D:");
        }
    }

    private void sendSuccess(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        sendResponse(request, response, message, "success", SUCCESS_PAGE);
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        sendResponse(request, response, message, "error", ERROR_PAGE);
    }

    private void sendResponse(HttpServletRequest request, HttpServletResponse response, String message, String type, String page) throws ServletException, IOException {
        request.setAttribute(TOAST_MESSAGE, message);
        request.setAttribute(TOAST_TYPE, type);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
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
