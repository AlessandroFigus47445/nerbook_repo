/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.servlet;

import amm.model.Wall;
import amm.model.WallFactory;
import amm.model.Utente;
import amm.model.UtenteFactory;
import amm.model.Post;
import amm.model.PostFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "Login", urlPatterns = {"/login.html"}, loadOnStartup = 0)
public class Login extends HttpServlet 
{
    
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CLEAN_PATH = "../../web/WEB-INF/db/nbdb";
    private static final String DB_BUILD_PATH = "WEB-INF/db/nbdb";
    
    @Override 
    public void init()
    {
        String dbConnection = "jdbc:derby:" + this.getServletContext().getRealPath("/") + DB_BUILD_PATH;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        UtenteFactory.getInstance().setConnectionString(dbConnection);
        PostFactory.getInstance().setConnectionString(dbConnection);
        WallFactory.getInstance().setConnectionString(dbConnection);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true); //creo una nuova sessione
        
        //controllo se un utente è già loggato
        if(session.getAttribute("utenteLoggedIn")!=null && (boolean)session.getAttribute("utenteLoggedIn") == true) 
        {
            //allora un utente è loggato e reindirizzo alla sua pagina
            response.sendRedirect("profilo.jsp");
        }
        else 
        {
            Boolean autenticazioneRiuscita = false; //setto inizialmente autenticazioneRiuscia a false perché non ho ancora autenticato nessuno

            if(request.getParameter("Submit") != null) //Se mi arrivano dati dal form di login
            {
            String username = request.getParameter("Username"); //recupero username
            String password = request.getParameter("Password"); //recupero password
            
            /* Recupero la lista dei post nella bacheca dell'utente dal database */
            //ArrayList<Post> listaPost = PostFactory.getInstance().getPostListFromUsername(username);
            
            // Vado a recuperare il cliente con username e password che mi sono arrivati dal form di login
            Utente u = UtenteFactory.getInstance().logUser(username, password);       
            
            // se c è diverso da null significa che sono riuscito ad identificare il cliente
            if(u != null ) 
            {
                autenticazioneRiuscita = true;
                session.setAttribute("loggedIn", true);
                session.setAttribute("utenteLoggedIn", true);
                session.setAttribute("loggedUserid", u.getUserid());
                session.setAttribute("loggedUsername", u.getUsername()); 
                session.setAttribute("loggedPassword", u.getPassword());
                session.setAttribute("loggedFirstname", u.getFirstname());
                session.setAttribute("loggedLastname", u.getLastname());
                session.setAttribute("loggedBirthday", u.getBirthday());
                session.setAttribute("loggedMotto", u.getMotto());
                session.setAttribute("loggedIsadmin", u.getIsadmin());
                
                session.setAttribute("loggedWallid", WallFactory.getInstance().getPersonalWallIdByUsername(u.getUsername()));
                //session.setAttribute("listaPost", listaPost);                
                                
                response.sendRedirect("profilo.jsp"); //reindirizzo a profilo.jsp
            }
            
        /* Se autenticazioneRiuscita è ancora a false, allora significa che non sono riuscito a trovare una corrispondenza tra i dati che sono arrivati
            via form e le credenziali dell'utente
            */
        if(autenticazioneRiuscita == false){
            /* Setto utenteLoggedIn a false dato che non ho autenticato e metto pure loggedIn a false 
            mi servono nella profilo.jsp per decidere cosa stampare nalla pagina
            */
            session.setAttribute("utenteLoggedIn", false);                  
            session.setAttribute("loggedIn", false);
            
            /* Rimando a login.jsp con un parametro inviato nell'URL autenticazioneFallita = true per dire che non è stato possibile autenticare con i dati
            inviati nell'url
            */
            request.getRequestDispatcher("login.jsp?autenticazioneFallita=true").forward(request, response);
        }
      } 
      else  
            request.getRequestDispatcher("login.jsp").forward(request, response);  
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