package lessurligneurs.DAO; 

public class ResNLP{
    private String titrecom ; 
    private String lieu  = "Aucun"; 
    private String personalite = "Aucun";
    private  String  entites;

   
    private ResNLP (Builder builder)
    {
		this.titrecom = builder.titrecom;
		this.lieu  = builder.lieu;
		this.personalite = builder.personalite ;
        this.entites = builder.entites;
	}

	public static class Builder{
            private  String titrecom; 
            private  String  lieu ; 
            private  String  personalite;
            private  String  entites;


            public Builder ( String  titre)
            {
                this.titrecom  = titre  ; 
            }

            public Builder lieu (String lieu ) 
            {
                this.lieu = lieu ; 
                return this;
            }

            public Builder  personalite (String personalite )
            {
                this.personalite = personalite ;  
                return this ; 
            } 

            public Builder entites(String entites){
                this.entites = entites;
                return this;
            }
            
            public ResNLP build()
            {
                return new ResNLP (this);
            }
        

    } 

    public String get_title () { return titrecom ;}
    public String get_lieu (){ return lieu; }
    public String get_personalite(){ return personalite ; }
    public String getEntites(){ return entites;}



}
