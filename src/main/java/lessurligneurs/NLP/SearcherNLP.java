package lessurligneurs.NLP;

import lessurligneurs.NLP.IndexerNLP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.*;
import org.apache.lucene.util.QueryBuilder;


public class SearcherNLP {
    private DirectoryReader dir;
    private IndexSearcher searcher;
    private QueryBuilder qbuilder;
    private List<Query> queryList;
    private BooleanQuery tmp,finalquery;
    private int topN;

    public SearcherNLP(IndexerNLP indexer, OpenNLPAnalyzer analyzer, int n) {
        try {
            this.dir = DirectoryReader.open(indexer.getDir());
            this.searcher = new IndexSearcher(this.dir);
            this.qbuilder = new QueryBuilder(analyzer);
            this.queryList = new ArrayList<>();
            this.queryList.add(qbuilder.createPhraseQuery(indexer.getFieldname(),"NPP"));
            this.queryList.add(qbuilder.createPhraseQuery(indexer.getFieldname(),"NPP NPP"));
            this.queryList.add(qbuilder.createPhraseQuery(indexer.getFieldname(),"NC NPP"));
            //this.queryList.add(qbuilder.createPhraseQuery(indexer.getFieldname(),"NC NPP NPP"));
            this.tmp = new BooleanQuery.Builder()
                    .add(queryList.get(0),BooleanClause.Occur.SHOULD)
                    .add(queryList.get(1),BooleanClause.Occur.SHOULD)
                    .add(queryList.get(2),BooleanClause.Occur.SHOULD)
              //      .add(queryList.get(3),BooleanClause.Occur.SHOULD)
                    .build();
            this.finalquery = new BooleanQuery.Builder()
                    .add(tmp, BooleanClause.Occur.MUST)
                    .build();
            this.topN = n;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public DirectoryReader getDir() {
        return dir;
    }

    public void setDir(DirectoryReader dir) {
        this.dir = dir;
    }

    public IndexSearcher getSearcher() {
        return searcher;
    }

    public void setSearcher(IndexSearcher searcher) {
        this.searcher = searcher;
    }

    public QueryBuilder getQbuilder() {
        return qbuilder;
    }

    public void setQbuilder(QueryBuilder qbuilder) {
        this.qbuilder = qbuilder;
    }

    public List<Query> getQueryList() {
        return queryList;
    }

    public void setQueryList(List<Query> queryList) {
        this.queryList = queryList;
    }

    public BooleanQuery getTmp() {
        return tmp;
    }

    public void setTmp(BooleanQuery tmp) {
        this.tmp = tmp;
    }

    public BooleanQuery getFinalquery() {
        return finalquery;
    }

    public void setFinalquery(BooleanQuery finalquery) {
        this.finalquery = finalquery;
    }

    public TopDocs doSearch(){
        try {
            return this.searcher.search(finalquery,topN);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TopDocs(0,new ScoreDoc[]{},0);
    }

}
