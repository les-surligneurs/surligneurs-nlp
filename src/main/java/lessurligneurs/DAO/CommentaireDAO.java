package lessurligneurs.DAO ; 
import java.util.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CommentaireDAO extends DAO<Commentaire> {

    private List all_commentaires  = null ; 


    @Override
    public  void  create(Commentaire  obj){}
    
    
    @Override
    public Commentaire find(String title)
    {
        return null ; 
    }
    
    @Override
    public  void  update(Commentaire obj ){}
    
    @Override
    public void delete (Commentaire  obj ){}

    public List<Commentaire>  get_all_Commentaires()
    {
        all_commentaires  = new ArrayList<Commentaire>(); 
        Commentaire  com= null; 
        try {
            ResultSet    result = this.connect
                                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                                    .executeQuery("SELECT * FROM Commentaire");
            while (result.next()){ 
                   
                  com = new Commentaire(
                                            result.getString("titre")
                                            ,result.getString("corps")
                                            ,result.getString("resume")
                                            ,result.getString("tag")
                                            ,result.getString("url")
                                            ,result.getString("source_url"));
                    

                all_commentaires.add(com); 
            }
    
    } catch (SQLException e)
            {
                    e.printStackTrace();
            }



            return all_commentaires; 
    }
    
}
