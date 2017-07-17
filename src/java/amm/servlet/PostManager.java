/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import amm.model.Post;
import amm.model.PostFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aless
 */
@WebServlet(name = "PostManager", urlPatterns = {"/postmanager.html"})
public class PostManager extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); //recupero la sessione supponendo che ne esista una
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String dated = now.toString();
               
        try
        {
            switch (request.getParameter("command"))
            {
                case "add": String url = (String)request.getParameter("url");
                            if ( url.equals("Vuoi allegare un link?") )
                                url="";
                            PostFactory.getInstance().addPost(
                            (String)session.getAttribute("loggedUsername"),
                            (String)request.getParameter("msg"), 
                            url, 
                            dated, 
                            Integer.parseInt(request.getParameter("wallid")),
                            Boolean.parseBoolean(request.getParameter("isgroup"))
                            );
                            break;
            
                case "delete":  PostFactory.getInstance().deletePost(
                                (String)session.getAttribute("loggedUsername"),
                                Integer.parseInt(request.getParameter("postid")),
                                Integer.parseInt(request.getParameter("wallid")),
                                Boolean.parseBoolean(request.getParameter("isgroup")),
                                Boolean.parseBoolean(request.getParameter("isadmin")),
                                Boolean.parseBoolean(request.getParameter("property"))
                                );
                                break;
            }
        }
        catch(SQLException e)
        {
            System.out.println("SoMeThInG gOnE wRoNg");
        }    
            
        //Ricarico in sessione la nuova lista Post
        ArrayList<Post> listaPost = PostFactory.getInstance().getPostListFromWallID(request.getParameter("wallid"));
        session.setAttribute("listaPost", listaPost);
        
        //request.getParameter("isgroup"));
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
