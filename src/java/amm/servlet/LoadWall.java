/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import amm.model.Post;
import amm.model.PostFactory;
import amm.model.Utente;
import amm.model.UtenteFactory;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoadWall", urlPatterns = {"/loadwall.html"}, loadOnStartup=0)

public class LoadWall extends HttpServlet 
{
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); //recupero la sessione supponendo che ne esista una
        
        if(request.getParameter("Submit") != null)
        {
            //hidden info passate dal form di navigazione in javascript
            session.setAttribute("visitedWallid",request.getParameter("wallid"));
            session.setAttribute("visitedOwner",request.getParameter("owner"));
            session.setAttribute("visitedName",request.getParameter("name"));
            session.setAttribute("visitedIsgroup",request.getParameter("isgroup"));
            
            //recupero info sul proprietario della bacheca esplorata
            Utente visited = UtenteFactory.getInstance().fetchUserDataFromWallID(request.getParameter("wallid"));
            
            session.setAttribute("visitedUserid", visited.getUserid());
            session.setAttribute("visitedUsername", visited.getUsername()); 
            session.setAttribute("visitedFirstname", visited.getFirstname());
            session.setAttribute("visitedLastname", visited.getLastname());
            session.setAttribute("visitedBirthday", visited.getBirthday());
            session.setAttribute("visitedMotto", visited.getMotto());
            session.setAttribute("visitedIsadmin", visited.getIsadmin());
            
            /* Recupero la lista dei post nella bacheca dal database */
            ArrayList<Post> listaPost = PostFactory.getInstance().getPostListFromWallID(request.getParameter("wallid"));
            session.setAttribute("listaPost", listaPost);
            
            /* CONTROLLO PROPRIETÁ/AMICIZIA/ISCRIZIONE e imposto tre attributi di sessione */
            if( UtenteFactory.getInstance().checkProperty((String)session.getAttribute("loggedUsername"), ((String)session.getAttribute("visitedWallid"))))
            {
                session.setAttribute("property", true);
            }
            else
            {
                session.setAttribute("property", false);    
                if ( Boolean.parseBoolean((String)session.getAttribute("visitedIsgroup"))==true )
                {
                    session.setAttribute("subscription", UtenteFactory.getInstance().checkSubscription(((String)session.getAttribute("loggedUsername")), ((String)session.getAttribute("visitedWallid"))));
                    session.setAttribute("friendship",false);
                }
                else
                {
                    session.setAttribute("friendship", UtenteFactory.getInstance().checkFriendship(((String)session.getAttribute("loggedUsername")), ((String)session.getAttribute("visitedUsername"))));
                    session.setAttribute("subscription", false);
                }
            }
            response.sendRedirect("bacheca.jsp");
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
