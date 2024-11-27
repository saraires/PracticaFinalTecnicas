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
public class SvInformation extends HttpServlet {

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

        RequestDispatcher dispatcher;

        try {

            // Campos a updatear
            String name = request.getParameter("fullname");
            String password = request.getParameter("password");

            // Campos necesarios para el update
            String username = request.getParameter("username");
            String email = request.getParameter("email");

            UsersRepository userRepo = new UsersRepository();
            String id = userRepo.getUserID(username);

            userRepo.updateUser(id, name, password);

            request.setAttribute("user", new User(
                    name,
                    email,
                    username,
                    password
            ));
          
            request.setAttribute("toastMessage", "User successfully updated!");
            request.setAttribute("toastType", "success");
            dispatcher = request.getRequestDispatcher("userInfo.jsp");
            dispatcher.forward(request, response);

        } catch (IOException e) {
            request.setAttribute("toastMessage", "We couldn't update your user, try again D:");
            request.setAttribute("toastType", "error");
            dispatcher = request.getRequestDispatcher("userInfo.jsp");
            dispatcher.forward(request, response);
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
