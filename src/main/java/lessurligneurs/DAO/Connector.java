package lessurligneurs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//

public class Connector{

    /**
     * URL de connexion
     */
    
    private static String url = "jdbc:postgresql://localhost:5432/lessurligneurs";
    /**
     * Nom du user
     */
    private static String user = "postgres";
    /**
     * Mot de passe du user
     */
    private static String passwd = "postgres";
    /**
     * Objet Connexion
     */
    private static Connection connect = null;
    
    /**
     * Méthode qui va nous retourner notre instance
     * et la créer si elle n'existe pas...
     * @return
     */
    public static Connection getInstance(){
        System.out.println ("Tentative de connexion :  ");
         
        if(connect == null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
                System.out.println ("connexion reussie ! "); 
                } 
            catch (SQLException e) {
                System.out.println ("echec de la connexion ! "); 
                e.printStackTrace();
               
            }
        }        
        return connect;    
    }    
}