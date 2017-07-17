package amm.servlet;

import amm.model.UtenteFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EditProfile", urlPatterns = {"/editprofile.html"}, loadOnStartup=0)

public class EditProfile extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); //recupero la sessione supponendo che ne esista una
        
        if(request.getParameter("Submit") != null)
        {
            int userid = Integer.parseInt(request.getParameter("Userid"));
            String username = request.getParameter("Username");
            String password1 = request.getParameter("Password1");
            String password2 = request.getParameter("Password2");
            String firstname = request.getParameter("Firstname");
            String lastname = request.getParameter("Lastname");
            String birthday = request.getParameter("Birthday");
            String motto = request.getParameter("Motto");
          
            Boolean isadmin;
            if(request.getParameter("Isadmin")==null)
                isadmin=false;
            else isadmin=true;
          
            if(UtenteFactory.getInstance().updateUser(userid, username, password1, password2, firstname, lastname, birthday, motto, isadmin))
            {
                session.setAttribute("loggedUserid", userid);
                session.setAttribute("loggedUsername", username); 
                session.setAttribute("loggedPassword", password1);
                session.setAttribute("loggedFirstname", firstname);
                session.setAttribute("loggedLastname", lastname);
                session.setAttribute("loggedBirthday", birthday);
                session.setAttribute("loggedMotto", motto);
                session.setAttribute("loggedIsadmin", isadmin);
                response.sendRedirect("profilo.jsp?profiloModificato=true"); //reindirizzo a profilo.jsp
            }
            else
            {
                response.sendRedirect("profilo.jsp?profiloModificato=false");
            }
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
