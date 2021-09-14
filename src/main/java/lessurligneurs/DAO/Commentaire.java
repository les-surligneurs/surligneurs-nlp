package lessurligneurs.DAO; 

public class Commentaire
{

    private String titre ; 
    private String corps ; 
    private String resume; 
    private String tag ; 
    private String url ; 
    private String source_url ; 

    public Commentaire  (String titre ,  String corps , String resume , String tag  , String  url ,  String source_url )
    {
		
        this .titre= titre;
        this.corps = corps; 
        this.resume = resume; 
        this.tag = resume; 
        this.url = url ; 
        this.source_url = source_url; 

    }



    public  String get_titre () { return titre ;}
    public  String get_corps(){ return corps ;}
    public  String get_tag (){ return tag ; }
    public  String get_resume (){ return resume ; }
    public  String get_url (){ return url ;}
    public  String get_urlsource(){ return source_url ; }


}
