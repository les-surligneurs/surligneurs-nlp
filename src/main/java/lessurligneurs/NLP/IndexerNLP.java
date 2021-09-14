package lessurligneurs.NLP;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexerNLP {
    private OpenNLPAnalyzer analyzer;
    private Directory dir;
    private IndexWriterConfig conf;
    private IndexWriter writer;

    public String getFieldname() {
        return fieldname;
    }

    private final String fieldname = "body";

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

    private void indexeDoc(String s){
        Document doc = new Document();
        //TODO: Indexer tous les champs de l'objet
        doc.add(new TextField(fieldname,s, Field.Store.YES));
        try {
            writer.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indexeDocs(List<String> strs){
        if(strs.size() == 0) closeWriter();
        for (String s: strs) {
            indexeDoc(s);
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
