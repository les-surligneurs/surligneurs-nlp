package lessurligneurs;

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

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum App{
    ENVIRONEMENT;

    private static Boolean VerificationFonction(CharTermAttribute termAtt, TypeAttribute typeAtt) {
        List<Character> AccentList = new ArrayList<>();
        AccentList.add('\'');
        AccentList.add('`');
        AccentList.add('’');
        return ((typeAtt.type().equals("NPP") || typeAtt.type().equals("NC"))
                && !termAtt.toString().equals("NPP") && !termAtt.toString().equals("NC") &&
                (Character.isUpperCase(termAtt.toString().toCharArray()[0]) && !AccentList.contains(termAtt.toString().toCharArray()[1])
                        ||AccentList.contains(termAtt.toString().toCharArray()[1]) && Character.isUpperCase(termAtt.toString().toCharArray()[2])));
    }

    private void run(String[] args) throws IOException {
        final String text = "  Selon Xavier Bertrand,“aujourd’hui, quand une personne est condamnée à moins de deux ans d’emprisonnement, elle ne va pas en prison”\n";
        final String text2 = " Belgique : Tom Van Grieken promet de “présenter la facture à tous ces enseignants de gauche”";
        final String text3 = " Vladimir Poutine craint que des terroristes ne se cachent parmi les afghans réfugiés";
        final String text4 = " l’Allemagne Jean-Pierre Joseph a tort de dire que “la vaccination n’est obligatoire pour personne”";
        final String text5 = " Réfugiés afghans : Ce n’est pas parce que les régions d’un pays sont dangereuses» qu’on a «automatiquement » droit à l’asile";
        final String text6 = " Francis Lalanne demande au peuple de destituer Emmanuel Macron : des poursuites pénales pour appel à l’insurrection peu probables";
        List<String> lst = new ArrayList<String>();
        lst.add(text);
        lst.add(text2);
        lst.add(text3);
        lst.add(text4);
        lst.add(text5);
        lst.add(text6);
        OpenNLPAnalyzer analyzer = new OpenNLPAnalyzer();
        IndexerNLP indexer = new IndexerNLP(analyzer);
        indexer.indexeDocs(lst);
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
                       System.out.println(termAtt.toString() + ": " + typeAtt.type());
                }
                stream.end();
            }
        }
    }

    public static void main( String[] args ) {
        try {
            ENVIRONEMENT.run(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
