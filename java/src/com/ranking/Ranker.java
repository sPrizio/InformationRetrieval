package com.ranking;

import com.entity.Document;
import com.entity.Term;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Ranking class responsible for ranking documents according to their relevancy
 *
 * @author Stephen Prizio
 */
public class Ranker {
    private int k;
    private int b;
    private List<Document> documents;
    private String[] query;

    /**
     * Default constructor
     */
    public Ranker() {
        this.k = 0;
        this.b = 0;
        this.documents = new ArrayList<>();
        this.query = null;
    }

    /**
     * Regular constructor taking a list of documents and a query
     *
     * @param d - list of documents in collection
     * @param params - user query
     */
    public Ranker(List<Document> d, String[] params) {
        this.k = 0;
        this.b = 0;
        this.documents = d;
        this.query = params;
    }


    //  METHODS

    public double okapiBM25() {


        return 0;
    }


    //  HELPERS

    /**
     * Calculates the average length of the documents in a collection
     *
     * @return average length of all documents
     */
    private double averageDocumentLength() {
        DecimalFormat decimalFormat = new DecimalFormat("#####.00");

        if (this.documents.isEmpty()) {
            return 0;
        }

        double sum = 0;

        for (Document d : this.documents) {
            sum += d.getDocumentLength();
        }

        return Double.parseDouble(decimalFormat.format(sum / (double) this.documents.size()));
    }

    /**
     * Returns the inverse document frequency for a term
     *
     * @param term - query terms
     * @return inverse document frequency
     */
    private double idf(Term term) {
        return (Math.log(((double) this.documents.size()  / ((double) term.getDocumentFrequency()))));
    }
}
