package lessurligneurs;
import lessurligneurs.DAO.* ; 
import java.util.*; 


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CommentaireDAO commentaires = new CommentaireDAO(); 
        
      
        List<Commentaire> a = commentaires.get_all_Commentaires() ; 

        for (Commentaire com  : a )
        {
            System.out.println(com.get_titre());
        }


        /*exemple dinsertiopn dans resnlp
        ResNLPDAO res = new ResNLPDAO(); 
        ResNLP objet = new ResNLP.Builder("exemple titre").lieu("exemple lieu trouvee ").personalite("exemple personalite trouvee").build(); 
        res.create(objet); 

        */

        System.out.println( "Hello World!" );
        
    }
}
