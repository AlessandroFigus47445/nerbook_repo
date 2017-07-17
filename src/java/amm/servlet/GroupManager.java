/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import amm.model.WallFactory;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aless
 */
@WebServlet(name = "GroupManager", urlPatterns = {"/groupmanager.html"})
public class GroupManager extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        switch (request.getParameter("command"))
        {
            case "delete":  try
                            {
                                if( WallFactory.getInstance().wipeOutGroup(request.getParameter("actor"), request.getParameter("owner"), Integer.parseInt(request.getParameter("wallid")), Boolean.parseBoolean(request.getParameter("isadmin"))) )
                                    response.sendRedirect("profilo.jsp");
                                else
                                    response.sendRedirect("bacheca.jsp");
                            }catch(SQLException e){}break;
            case "create":  try
                            {
                                if( WallFactory.getInstance().createGroup(request.getParameter("owner"),request.getParameter("name")) )
                                    response.sendRedirect("profilo.jsp?GruppoCreato=true");
                                else
                                    response.sendRedirect("profilo.jsp?GruppoCreato=false");
                            }catch(SQLException e){}break;
        }
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
