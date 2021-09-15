package lessurligneurs.DAO ; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResNLPDAO  extends DAO <ResNLP>
{
@Override 
public  void  create( ResNLP obj)
{
           
     try 
     {
                PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO resnlp (titrecomm, personalite , lieu, namedentities) VALUES(?, ?, ?,?)");
               
                prepare.setString(1, obj.get_title());
                prepare.setString(2, obj.get_personalite());
                prepare.setString(3, obj.get_lieu());
                prepare.setString(4, obj.getEntites());
                
                prepare.executeUpdate();   

                
        } catch (SQLException e) {
                e.printStackTrace();
        }
  
}

@Override
public ResNLP find(String title)
{
    ResNLP objet = null;
    try {
        ResultSet result = this .connect
                                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                                .executeQuery("SELECT * FROM resnlp  WHERE titrecom  like  " + title + "%");
        if(result.first())
                 objet  = new ResNLP.Builder(result.getString("titrecomm")).personalite(result.getString("personalite")).lieu(result.getString("lieu")).build();
        
        } catch (SQLException e) {
                e.printStackTrace();
        }
       return objet  ; 
}

@Override
public  void  update(ResNLP obj)
{
    try
    {        
        this .connect    
             .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
             .executeUpdate("UPDATE resnlp SET lieu = '" + obj.get_lieu() + "'"+ " WHERE titrecomm = " + obj.get_title() );}
    catch (SQLException e)
    {
            e.printStackTrace();
    }


    try 
    {            this .connect    
                 .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                 .executeUpdate( "UPDATE resnlp SET personalite = '" + obj.get_personalite() + "'"+" WHERE titrecomm = " + obj.get_title());}
    catch (SQLException e)
    {
                e.printStackTrace();
    }
    

}
@Override
public  void delete (ResNLP obj )
{
    try {
            this.connect
            .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
            .executeUpdate( "DELETE FROM resnlp WHERE titrecomm  = " + obj.get_title());
    
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

}
    
}
