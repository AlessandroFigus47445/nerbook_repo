/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostFactory {
    
    private static PostFactory singleton;
    String connectionString;
    
    public static PostFactory getInstance() {
        if (singleton == null) {
            singleton = new PostFactory();
        }
        return singleton;
    }
       
    /* Costruttore */
    private PostFactory() {
    
    }
    
    public ArrayList<Post> getPostListFromUsername(String username) {
        // Lista Oggetti
        ArrayList<Post> listaOggetti = new ArrayList<Post>();
        try {
            // Mi connetto al database
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            // Preparo la query
            String queryRicerca = "select * from posts where postid in (select postid from publications where wallid = (select wallid from walls where owner= ? and (name is null)))";
            
            PreparedStatement stmt = conn.prepareStatement(queryRicerca);
            
            //Specifico il parametro della query
            stmt.setString(1,username);      
            
            // Eseguo la query
            ResultSet res = stmt.executeQuery();
            // Se res è diverso da null, scorro ogni suo elemento creo di volta in volta un nuovo Post e lo aggiungo a listaOggetti
            while(res.next()) {
                //Creo un nuovo oggetto Post
                Post temp = new Post();
                // Lo valorizzo prendendo i dati dal resultSet della query
                temp.setPostid(res.getInt("postid"));
                temp.setAuthor(res.getString("author"));
                temp.setMsg(res.getString("msg"));
                temp.setUrl(res.getString("url"));
                temp.setDated(res.getString("dated"));
                
                // Aggiungo l'oggetto alla listaOggetti
                listaOggetti.add(temp);
            }          
            //Chiudo statement e connessione
            stmt.close();
            conn.close();
            
            Collections.reverse(listaOggetti); //Rivolto l'array list altrimenti i post più recenti appariranno in bacheca per ultimi
            return listaOggetti;
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return listaOggetti;
    }
    
    /*************/
    public ArrayList<Post> getPostListFromWallID(String wallid) {
        // Lista Oggetti
        ArrayList<Post> listaOggetti = new ArrayList<Post>();
        try {
            // Mi connetto al database
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            // Preparo la query
            String queryRicerca = "select * from posts where postid in (select postid from publications where wallid = "+wallid+")";
            
            PreparedStatement stmt = conn.prepareStatement(queryRicerca);
            
            // Eseguo la query
            ResultSet res = stmt.executeQuery();
            // Se res è diverso da null, scorro ogni suo elemento creo di volta in volta un nuovo Post e lo aggiungo a listaOggetti
            while(res.next()) {
                //Creo un nuovo oggetto Post
                Post temp = new Post();
                // Lo valorizzo prendendo i dati dal resultSet della query
                temp.setPostid(res.getInt("postid"));
                temp.setAuthor(res.getString("author"));
                temp.setMsg(res.getString("msg"));
                temp.setUrl(res.getString("url"));
                temp.setDated(res.getString("dated"));
                
                // Aggiungo l'oggetto alla listaOggetti
                listaOggetti.add(temp);
            }          
            
            stmt.close();
            conn.close();
            
            Collections.reverse(listaOggetti);
            return listaOggetti;
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        
        return listaOggetti;
    }
    /*************/
    
    
    
    public void addPost(String author, String msg, String url, String dated, int wallid, Boolean isgroup) throws SQLException
    {
        Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
        
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;
                    
        try {
            conn.setAutoCommit(false);
            
            // Preparo la query per la tabella post
            String queryPost = "insert into posts values (default,'"+author+"','"+msg+"','"+url+"','"+dated+"')"; //Aggiunta del post nel database
            stmt = conn.prepareStatement(queryPost);
            // Inserisco il post nel db
            int rows = stmt.executeUpdate();
            if(rows != 1) conn.rollback();
            
            // Preparo la query per la tabella publications
            String queryPublish = "insert into publications values ((SELECT MAX(postid) FROM posts),"+wallid+")";
            stmt2 = conn.prepareStatement(queryPublish);
            // Inserisco il post nel db
            rows = stmt2.executeUpdate();
            if(rows != 1) conn.rollback();
            
            if(isgroup) //se la bacheca è un gruppo propago il post nelle bacheche degli iscritti
            {
                String findSubscribers = "select wallid from walls where owner in (select subscriber from subscriptions where groupwall = "+wallid+") and name is null";
                stmt3 = conn.prepareStatement(findSubscribers);
                ResultSet subscribers = stmt3.executeQuery();                
                
                while(subscribers.next())
                {
                  String propagatePublish = "insert into publications values ((SELECT MAX(postid) FROM posts),"+subscribers.getInt("wallid")+")";
                  stmt4 = conn.prepareStatement(propagatePublish);
                  stmt4.executeUpdate();
                }
            }   
            conn.commit();
        } 
        catch(SQLException e) 
        {
            try
            {   
                conn.rollback();                
            }
            catch (SQLException e2)
            {
                Logger.getLogger(PostFactory.class.getName()).log(Level.SEVERE, null, e2);
            }
            finally
            {
                if(stmt != null) stmt.close();
                if(stmt2 != null) stmt2.close();
                if(stmt3 != null) stmt3.close();
                if(stmt4 != null) stmt4.close();
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(stmt2 != null) stmt2.close();
            if(stmt3 != null) stmt3.close();
            if(stmt4 != null) stmt4.close();
            conn.setAutoCommit(true);
            conn.close();
        }
    }
    
    public void deletePost(String actor, int postid, int wallid, Boolean isgroup, Boolean isadmin, Boolean wallproperty) throws SQLException
    {
        Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
        
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;
        
        try
        {
            // CONTROLLO AUTORIZZAZIONI ALL'ELIMINAZIONE: Procedo alla cancellazione se l'utente è 
            if(!isadmin) // 1)admin
            {
                String postAuthorQuery = "select author from posts where postid = "+postid;
                stmt = conn.prepareStatement(postAuthorQuery);
                ResultSet postAuthor = stmt.executeQuery();
                String author = "";
                while(postAuthor.next())
                    author = postAuthor.getString("author");
                
                if(!author.equals(actor)) // 2)proprietario del post
                  if(!wallproperty)       // 3)proprietario della bacheca 
                      return;
            }//Superato questo blocco la cancellazione del post è autorizzata
            
            conn.setAutoCommit(false);
            if(isgroup) //Se sto eliminando un post in un gruppo devo eliminarlo anche dalla bacheca personale degli iscritti
            {
                String deletePublications = "delete from publications where postid = "+postid; //Cancello tutte le pubblicazioni del post
                stmt2 = conn.prepareStatement(deletePublications);
                stmt2.executeUpdate();
                
                String deletePost = "delete from posts where postid = "+postid; //Cancello il post
                stmt3 = conn.prepareStatement(deletePost);
                stmt3.executeUpdate();
            }
            else //Se invece sto eliminando un post da una bacheca utente devo distinguere i casi in cui l'istanza è unica oppure "sto eliminando un post di un gruppo da una bacheca" dove risulta che devo eliminare solo quell'istanza e lasciare inalterate le altre
            {
                String postCount = "select count(postid) as count from publications where postid = "+postid; //Conto quante pubblicazioni del post vi sono
                stmt2 = conn.prepareStatement(postCount);
                ResultSet istances = stmt2.executeQuery();                
                
                int postcount = 0;
                while(istances.next())
                    postcount = istances.getInt("count");
                                
                if(postcount>1) //Se il post esiste in più posti allora è un post di un gruppo ma lo devo eliminare solo dalla bacheca in questione
                {
                    String deletePublications = "delete from publications where postid = "+postid+" and wallid = "+wallid;
                    stmt3 = conn.prepareStatement(deletePublications);
                    stmt3.executeUpdate();                    
                }
                else //Altrimenti il post che sto eliminando è unico e mi comporto come nel caso del gruppo
                {
                    String deletePublications = "delete from publications where postid = "+postid;
                    stmt3 = conn.prepareStatement(deletePublications);
                    stmt3.executeUpdate();
                    
                    String deletePost = "delete from posts where postid = "+postid;
                    stmt4 = conn.prepareStatement(deletePost);
                    stmt4.executeUpdate();
                }
            }
        }
        catch(SQLException e)
        {
            try
            {   
                conn.rollback();    
            }
            catch (SQLException e2)
            {
                Logger.getLogger(PostFactory.class.getName()).log(Level.SEVERE, null, e2);
            }
            finally
            {
                if(stmt != null) stmt.close();
                if(stmt2 != null) stmt2.close();
                if(stmt3 != null) stmt3.close();
                if(stmt4 != null) stmt4.close();
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(stmt2 != null) stmt2.close();
            if(stmt3 != null) stmt3.close();
            if(stmt4 != null) stmt4.close();
            conn.setAutoCommit(true);
            conn.close();
        }
    }
    
    public void setConnectionString(String s){
	this.connectionString = s;
    }
    public String getConnectionString(){
	return this.connectionString;
    }
}
