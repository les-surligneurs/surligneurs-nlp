package lessurligneurs.NLP;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import lessurligneurs.DAO.Commentaire;
import java.io.IOException;
import java.util.List;

public class IndexerNLP {
    private OpenNLPAnalyzer analyzer;
    private Directory dir;
    private IndexWriterConfig conf;
    private IndexWriter writer;

    private final String bodyfieldname = "corps";
    private final String titlefieldname = "titre";


    public IndexerNLP(OpenNLPAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.dir = new RAMDirectory();
        this.conf = new IndexWriterConfig(analyzer);
        try {
            this.writer = new IndexWriter(dir,conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public OpenNLPAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(OpenNLPAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public Directory getDir() {
        return dir;
    }

    public void setDir(Directory dir) {
        this.dir = dir;
    }

    public IndexWriterConfig getConf() {
        return conf;
    }

    public void setConf(IndexWriterConfig conf) {
        this.conf = conf;
    }

    public IndexWriter getWriter() {
        return writer;
    }

    public void setWriter(IndexWriter writer) {
        this.writer = writer;
    }

    public String getTitlefieldname() {return titlefieldname;}

    public String getBodyfieldname() {return bodyfieldname;}

    private void indexeDoc(String titre, String corps){
        Document doc = new Document();
        doc.add(new TextField(titlefieldname,titre, Field.Store.YES));
        doc.add(new TextField(bodyfieldname,corps, Field.Store.YES));
        try {
            writer.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indexeDocs(List<Commentaire> strs){
        if(strs.size() == 0) closeWriter();
        for (Commentaire s: strs) {
            indexeDoc(s.get_titre(),s.get_titre() + s.get_resume() + s.get_corps());
        }
        closeWriter();
    }

    public void closeWriter(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
