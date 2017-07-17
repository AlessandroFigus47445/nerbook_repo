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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aless
 */
public class WallFactory {
    
    // Attributi
    private static WallFactory singleton;
    String connectionString;
    
    public static WallFactory getInstance() {
        if (singleton == null) {
            singleton = new WallFactory();
        }
        return singleton;
    }

    /* Costruttore */
    private WallFactory() {
       
    }
    
    public int getPersonalWallIdByUsername(String username)
    {
        int wallid=-1;
        try {
            
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
                                   
            Statement stmt = conn.createStatement();
            
            String queryRicerca = "SELECT * from walls where owner = '"+username+"' and name is null";
            
            ResultSet res = stmt.executeQuery(queryRicerca);
            
            while(res.next())
            {
                wallid = res.getInt("wallid");
            }
            stmt.close();
            conn.close();
            return wallid;             
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        return -1;
    }
    
    public ArrayList<Wall> getWallsByPartialName(String text) {
        
        ArrayList<Wall> list = new ArrayList<>();
        
        try {
            
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            String queryRicerca = "SELECT wallid, owner, name from walls where (owner like ? ) or (name like ? )";
            
            PreparedStatement stmt = conn.prepareStatement(queryRicerca);
            
            // Assegno i dati
            text = "%"+text+"%";
            stmt.setString(1, text);
            stmt.setString(2, text);
            
            // Eseguo la query
            ResultSet res = stmt.executeQuery();
            
            while(res.next()) {
                // creo un nuovo oggetto wall
                Wall temp = new Wall();
                
                // Lo valorizzo prendendo i dati dal resultSet della query
                temp.setWallid(res.getInt("wallid"));
                temp.setOwner(res.getString("owner"));
                temp.setName(res.getString("name"));
                
                // Aggiungo l'oggetto alla listaOggetti
                list.add(temp);
            }
            
            stmt.close();
            conn.close();
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Boolean wipeOutGroup(String actor, String owner, int wallid, Boolean isadmin) throws SQLException
    {
        //VERIFICO l'autorizzazione alla cancellazione del gruppo
        if(!isadmin)
            if(!actor.equals(owner))
                return false;
        
        Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
        
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;
        PreparedStatement stmt5 = null;
        
        try
        {
            conn.setAutoCommit(false);
            
            String findPostIDs = "select postid from publications where wallid = "+wallid;
            stmt = conn.prepareStatement(findPostIDs);
            ResultSet postids = stmt.executeQuery();
            
            while(postids.next())//per ogni post che era presente nel gruppo
            {
                int postid = postids.getInt("postid");
                
                //Elimino le pubblicazioni
                String deletePublications = "delete from publications where postid = "+postid;
                stmt2 = conn.prepareStatement(deletePublications);
                stmt2.executeUpdate();
                
                //Elimino il post stesso
                String deletePost = "delete from posts where postid = "+postid;
                stmt3 = conn.prepareStatement(deletePost);
                stmt3.executeUpdate();
            }
            String deleteSubscriptions = "delete from subscriptions where groupwall = "+wallid;
            stmt4 = conn.prepareStatement(deleteSubscriptions);
            stmt4.executeUpdate();
            
            String deleteWall = "delete from walls where wallid = "+wallid;
            stmt5 = conn.prepareStatement(deleteWall);
            stmt5.executeUpdate();
            
            return true;
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
        return false;
    }
    
    public Boolean createGroup(String owner, String name) throws SQLException
    {
        Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
        
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
                
        try
        {
            conn.setAutoCommit(false);
            
            String newWall = "insert into walls values (default,'"+owner+"','"+name+"')";
            stmt = conn.prepareStatement(newWall);
            stmt.executeUpdate();
            
            String ownerSubscription = "insert into subscriptions values ('"+owner+"',(select max(wallid) from walls))";
            stmt2 = conn.prepareStatement(ownerSubscription);
            stmt2.executeUpdate();
            
            return true;
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
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(stmt2 != null) stmt2.close();
            conn.setAutoCommit(true);
            conn.close();
        }
        return false;
    }
    
    // ConnectionString
    public void setConnectionString(String s){
	this.connectionString = s;
    }
    public String getConnectionString(){
	return this.connectionString;
    }   
    
}


