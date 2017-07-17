package amm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UtenteFactory {
    
    // Attributi
    private static UtenteFactory singleton;
    String connectionString;
    
    public static UtenteFactory getInstance() {
        if (singleton == null) {
            singleton = new UtenteFactory();
        }
        return singleton;
    }
        
    /* Costruttore */
    private UtenteFactory() {
       
    }
    
    // Restituisce il cliente relativo ai dati usr e psw, se esiste
    public Utente logUser(String usr, String psw)
    {
        try {
            // Mi connetto al database
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            // Preparo la query con cui ricerco tutti i clienti e filtro per usr e psw
            String queryRicerca = "select * from users where "
                    + "username = ? and password = ?";
            PreparedStatement stmt = conn.prepareStatement(queryRicerca);
            
            // Setto le due stringhe ? dello statement coni valori passati al metodo
            stmt.setString(1, usr);
            stmt.setString(2, psw);
            
            // Mando in esecuzione la query
            ResultSet set = stmt.executeQuery();
            
            // In set ci deve essere un solo elemento (cliente) associato a quell'username e password. Mi assicuro che ci sia
            if(set.next()) {
                // Creo un nuovo Cliente, da valorizzare e poi restituire
                Utente u = new Utente();
                u.userid = set.getInt("userid");
                u.username = set.getString("username");
                u.password = set.getString("password");
                u.firstname = set.getString("firstname");
                u.lastname = set.getString("lastname");
                u.birthday = set.getString("birthday");
                u.motto = set.getString("motto");
                u.isadmin = set.getBoolean("isadmin");
                // Chiudo statement e connessione
                stmt.close();
                conn.close();
   
                return u; 
            }
            
            // Chiudo statement e connessione
            stmt.close();
            conn.close();
            
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public Boolean newUser(String username, String password1, String password2, String firstname, String lastname, String birthday, String motto) throws SQLException
    {
        if(!(password1.equals(password2)))
              return false;
        
        // Mi connetto al database
        Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
        int rows;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        
        try 
        {
            conn.setAutoCommit(false);
            
            String insertNewUser = "insert into users values (default,'"+username+"','"+password1+"','"+firstname+"','"+lastname+"','"+motto+"','"+birthday+"', FALSE)";
            stmt = conn.prepareStatement(insertNewUser);
            rows = stmt.executeUpdate();
            if(rows != 1)
            {
                conn.rollback();
                return false;
            }            
            
            String insertNewWall = "insert into walls values (default,'"+username+"',null)";
            stmt2 = conn.prepareStatement(insertNewWall);
            rows = stmt2.executeUpdate();
            if(rows != 1)
            {
                conn.rollback();
                return false;
            }
            
            conn.commit();
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
    
    public Boolean updateUser(int userid, String username, String password1, String password2, String firstname, String lastname, String birthday, String motto, Boolean isadmin)
    {
        if(!(password1.equals(password2)))
              return false;
        
        try {
                // Mi connetto al database
                Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
                Statement stmt = conn.createStatement();
                
                // Preparo la query con cui aggiornare la tupla utente interessata
                String queryUpdate = "UPDATE users SET"
                        +" userid = "+ userid
                        +", username = '"+ username
                        +"', password = '"+ password1
                        +"', firstname = '"+ firstname
                        +"', lastname = '"+ lastname
                        +"', birthday = '"+ birthday
                        +"', motto = '"+ motto
                        +"', isadmin = "+ Boolean.toString(isadmin)
                        +" WHERE userid = "+ userid ;
            
                if (stmt.executeUpdate(queryUpdate)==1)
                {
                    stmt.close();
                    conn.close();
                    return true;
                }
                else
                {
                    stmt.close();
                    conn.close();
                    return false;
                }
            }
            catch(SQLException e) 
            {
                e.printStackTrace();  
            }        
        return false;
    }
    
    public Utente fetchUserDataFromWallID(String wallid)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            String queryRicerca = "select * from users where username = (select owner from walls where wallid = ? )";
            PreparedStatement stmt = conn.prepareStatement(queryRicerca);
            
            stmt.setInt(1, Integer.parseInt(wallid));
            
            ResultSet set = stmt.executeQuery();
            
            if(set.next()) {
                // Creo un nuovo Cliente, da valorizzare e poi restituire
                Utente u = new Utente();
                u.userid = set.getInt("userid");
                u.username = set.getString("username");
                u.password = set.getString("password");
                u.firstname = set.getString("firstname");
                u.lastname = set.getString("lastname");
                u.birthday = set.getString("birthday");
                u.motto = set.getString("motto");
                u.isadmin = set.getBoolean("isadmin");
                // Chiudo statement e connessione
                stmt.close();
                conn.close();
   
                return u; 
            }
                
            }
            catch(SQLException e) 
            {
                e.printStackTrace();  
            }
        return null;
    }
    
    public Boolean checkFriendship(String friend1, String friend2)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            String queryRicerca = "select * from friendships "
                    + "where (friend1 = '"+friend1+"' and friend2 = '"+friend2+"')"
                    + "or (friend1 = '"+friend2+"' and friend2 = '"+friend1+"')";
            PreparedStatement stmt = conn.prepareStatement(queryRicerca);
            
            ResultSet set = stmt.executeQuery();
            
            if(set.next()) 
            {
                stmt.close();
                conn.close();
                return true; 
            }   
            stmt.close();
            conn.close();
            return false;
        }
        catch(SQLException e) 
        {
            e.printStackTrace();  
        }        
        return null;
    }
    
    public Boolean checkSubscription(String username, String wallid)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            String queryRicerca = "select * from subscriptions where subscriber = '"+username+"' and groupwall = "+wallid+" ";
            PreparedStatement stmt = conn.prepareStatement(queryRicerca);
            
            ResultSet set = stmt.executeQuery();
            
            if(set.next()) 
            {
                stmt.close();
                conn.close();
                return true; 
            }
            stmt.close();
            conn.close();
            return false; 
        }
        catch(SQLException e) 
        {
            e.printStackTrace();  
        }        
        return null;
    }
    
    public Boolean checkProperty(String username, String wallid)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            String queryRicerca = "select * from walls where owner = '"+username+"' and wallid = "+wallid;
            PreparedStatement stmt = conn.prepareStatement(queryRicerca);
            
            ResultSet set = stmt.executeQuery();
            
            if(set.next()) 
            {
                stmt.close();
                conn.close();
                return true; 
            }
            stmt.close();
            conn.close();
            return false; 
        }
        catch(SQLException e) 
        {
            e.printStackTrace();  
        }        
        return null;
    }
    
    public Boolean makeFriendship (String user1, String user2)
    {
      try 
        {
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            Statement stmt = conn.createStatement();
         
            String queryUpdate = "insert into friendships values ('"+user1+"','"+user2+"')" ;
            
            if (stmt.executeUpdate(queryUpdate)==1)
            {
                stmt.close();
                conn.close();
                return true;
            }
            else
            {
                stmt.close();
                conn.close();
                return false;
            }
        }
        catch(SQLException e) 
        {
            e.printStackTrace();  
        }        
      return false;
    }
    
    public Boolean breakFriendship (String user1, String user2)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            Statement stmt = conn.createStatement();
               
            String queryUpdate = "delete from friendships "
                    + "where (friend1 = '"+user1+"' and friend2 = '"+user2+"')" 
                    + "or (friend1 = '"+user2+"' and friend2 = '"+user1+"')" ;
            
            if (stmt.executeUpdate(queryUpdate)==1)
            {
                stmt.close();
                conn.close();
                return true;
            }
            else
            {
                stmt.close();
                conn.close();
                return false;
            }
        }
        catch(SQLException e) 
        {
            e.printStackTrace();  
        }        
        return false;
    }
    
    public Boolean subscribe (String user, int groupid)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            Statement stmt = conn.createStatement();
               
            String queryUpdate = "insert into subscriptions values ('"+user+"',"+groupid+")" ;
            
            if (stmt.executeUpdate(queryUpdate)==1)
            {
                stmt.close();
                conn.close();
                return true;
            }
            else
            {
                stmt.close();
                conn.close();
                return false;
            }
        }
        catch(SQLException e) 
        {
            e.printStackTrace();  
        }        
        return false;
    }
    
    public Boolean unsubscribe (String user, int groupid)
    {
        try 
        {
            Connection conn = DriverManager.getConnection(connectionString, "nbdb", "nbdb");
            
            Statement stmt = conn.createStatement();
               
            String queryUpdate = "delete from subscriptions where subscriber = '"+user+"' and groupwall = "+groupid;
            
            if (stmt.executeUpdate(queryUpdate)==1)
            {
                stmt.close();
                conn.close();
                return true;
            }
            else
            {
                stmt.close();
                conn.close();
                return false;
            }
        }
        catch(SQLException e) 
        {
            e.printStackTrace();  
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