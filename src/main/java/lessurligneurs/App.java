package lessurligneurs;


import lessurligneurs.DAO.* ; 
import lessurligneurs.NLP.OpenNLPAnalyzer;
import lessurligneurs.NLP.IndexerNLP;
import lessurligneurs.NLP.SearcherNLP;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.QueryBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public enum App{
    ENVIRONEMENT;

    private static Boolean VerificationFonction(CharTermAttribute termAtt, TypeAttribute typeAtt) {
        List<Character> AccentList = new ArrayList<>();
        AccentList.add('\'');
        AccentList.add('`');
        AccentList.add('’');
        final boolean upperCase = Character.isUpperCase(termAtt.toString().toCharArray()[0]);
        if(termAtt.length() >= 3) {
            return ((typeAtt.type().equals("NPP") || typeAtt.type().equals("NC"))
                    && !termAtt.toString().equals("NPP") && !termAtt.toString().equals("NC") &&
                    (upperCase && !AccentList.contains(termAtt.toString().toCharArray()[1])
                            || AccentList.contains(termAtt.toString().toCharArray()[1]) && Character.isUpperCase(termAtt.toString().toCharArray()[2])));
        }
        return false;
    }

    private void run(String[] args) throws IOException {
        final String filename = "resultats";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename),1);
        CommentaireDAO commentaires = new CommentaireDAO();
        List<Commentaire> a = commentaires.get_all_Commentaires();
        OpenNLPAnalyzer analyzer = new OpenNLPAnalyzer();
        IndexerNLP indexer = new IndexerNLP(analyzer);
        indexer.indexeDocs(a);
        SearcherNLP searcher = new SearcherNLP(indexer,analyzer,10);
        TopDocs td = searcher.doSearch();
        System.out.printf("<Number of hits> %d hits\n", td.totalHits);
        for(ScoreDoc scoreDoc: td.scoreDocs){
            Document doc = searcher.getSearcher().doc(scoreDoc.doc);
            System.out.println();
            TokenStream stream = analyzer.tokenStream("field", new StringReader(Arrays.toString(doc.getValues("body"))));
            try (stream) {
                CharTermAttribute termAtt = stream.addAttribute(CharTermAttribute.class);
                TypeAttribute typeAtt = stream.addAttribute(TypeAttribute.class);
                stream.reset();
                while (stream.incrementToken()) {
                   if(VerificationFonction(termAtt,typeAtt))
                       //System.out.println(termAtt.toString() + ": " + typeAtt.type());

                       writer.append(termAtt.toString()).append(": ").append(typeAtt.type()).append("\n");
                }
            }
        }
        writer.close();
    }

    public static void main( String[] args ) {
        try {
            ENVIRONEMENT.run(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
