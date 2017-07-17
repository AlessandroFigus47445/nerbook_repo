/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import amm.model.UtenteFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author aless
 * Tramite un parametro nascosto nel form seleziono quale metodo richiamare per gestire amicizie e iscrizioni
 */
@WebServlet(name = "FriendScription", urlPatterns = {"/friendscription.html"}, loadOnStartup = 0)
public class FriendScription extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession(false); //recupero la sessione supponendo che ne esista una
        
        switch(request.getParameter("command"))
        {
            case "makefriendship": 
                if( UtenteFactory.getInstance().makeFriendship((String)session.getAttribute("loggedUsername"),request.getParameter("visitedUser")) )
                    session.setAttribute("friendship",true);
                break;
            case "breakfriendship": 
                if( UtenteFactory.getInstance().breakFriendship((String)session.getAttribute("loggedUsername"),request.getParameter("visitedUser")) )
                    session.setAttribute("friendship",false);
                break;
            case "subscribe":
                if( UtenteFactory.getInstance().subscribe((String)session.getAttribute("loggedUsername"),Integer.parseInt(request.getParameter("visitedWallid"))) )
                    session.setAttribute("subscription",true);
                break;
            case "unsubscribe":
                if( UtenteFactory.getInstance().unsubscribe((String)session.getAttribute("loggedUsername"),Integer.parseInt(request.getParameter("visitedWallid"))) )
                    session.setAttribute("subscription",false);
                break;
        }
        
        response.sendRedirect("bacheca.jsp");
        
    }
    

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
        processRequest(request, response);
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
