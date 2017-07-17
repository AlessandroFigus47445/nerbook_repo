package amm.servlet;

import amm.model.Wall;
import amm.model.WallFactory;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 
 * Filter è la servlet che gestisce le richieste asincrone inviate dal jquery tramite ajax.
 * Si occupa di effettuare una query sul db per ogni tasto premuto dall'utente: infatti, ad ogni tasto rilasciato
 * il contenuto della textbox viene prelevato dal jquery e inviato tramite richiesta asincrona con ajax a questa servlet
 * che esegue il metodo getItemsByPartialName passando appunto come parametro il testo prelevato dalla textbox.
 * Il risultato di questo metodo è una lista di oggetti (anche vuota) i cui nomi contengono la stringa ricercata
 * dall'utente.
 * Viene salvata in richiesta la listaOggetti, settate alcune informazioni sulla risposta (ad esempio il formato json)
 * e poi viene eseguito un reindirizzamento sulla pagina listaOggettiJson che si occupa di creare un array di oggetti
 * json (ciascuno con delle proprietà quali nome, URL immagine, descrizione ecc) costituito dagli oggetti
 * presenti nella listaOggetti salvata in richiesta da questa servlet.
 * Questo array viene poi letto dalla funzione javascript che si occupa di aggiornare la tabella, aggiornaListaOggetti.
 */
@WebServlet(name = "Filter", urlPatterns = {"/filter.json"})
public class Filter extends HttpServlet {

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
        
        response.setContentType("text/html;charset=UTF-8");
        
        //HttpSession session = request.getSession(false); //recupero la sessione supponendo che ne esista una
        
        String command = request.getParameter("cmd");
        
        if (command != null) 
        {
            // Verifica che command e id siano stati impostati
            if (command.equals("search")) 
            {
                
                // Esegue la ricerca
                ArrayList<Wall> wallsList = WallFactory.getInstance().getWallsByPartialName(request.getParameter("text"));
                // Imposto la lista come attributo della request, come facevamo per l'HTML
                request.setAttribute("wallsList", wallsList);
                
                // Quando si restituisce del json e' importante segnalarlo ed evitare il caching
                response.setContentType("application/json");
                response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
                response.setHeader("Cache-Control", "no-store, no-cache, "
                        + "must-revalidate");
                // Genero il json con una jsp
                request.getRequestDispatcher("wallsListJson.jsp").
                        forward(request, response);
                
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
