/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airline.controllers;

import com.airline.beans.BankResponse;
import com.airline.beans.Bookings;
import com.airline.beans.Flight;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Phani Rahul
 */
public class Banking extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            System.out.println();
            BankResponse obj = new BankResponse();
            obj.setBill(Double.valueOf(request.getParameter("bill")));
            obj.setTransaction_status(Boolean.valueOf(request.getParameter("transaction_status")));
            obj.setSession_id(request.getParameter("session"));
            obj.setUsername(request.getParameter("username"));

            HttpSession session = getSession(obj.getSession_id());
            session.setAttribute("bank_response", obj);
            session.setAttribute("status", "true");

            /*Now to save the booking history*/          
            List list = (ArrayList) session.getAttribute("bookings");
            Iterator itr = list.iterator();
            while (itr.hasNext()) {
                Bookings b = (Bookings) itr.next();               
                    Flight.flightUpdateBookings(Integer.valueOf(b.getSeats()),
                            b.getFlightNumber(), (getServletContext()));
                    int bookingId = b.save();   
                    b.setBookingId(bookingId+"");
            }
           // list.clear();


        } catch (Exception e) {
            System.out.println("Exception : " + e);
        } finally {
            out.close();
        }
    }

    private HttpSession getSession(String sessionId) {
        ServletContext context = getServletContext();
        HttpSession session = (HttpSession) context.getAttribute(sessionId);
        return session;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
