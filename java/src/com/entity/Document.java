package com.entity;

import com.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * I certify that this submission is my original work and meets the Faculty’s Expectations of Originality - 16 October 2017
 *
 * Representation of a document that is to be indexed
 *
 * @author Stephen Prizio - 40001739
 */
public class Document {
    private int documentID;
    private String title;
    private String body;
    private Tokenizer tokenizer;
    private List<Term> terms;

    /**
     * Regular constructor that builds a document with an id, title and some text
     *
     * @param docId - document id
     * @param title - document title
     * @param body - document body
     */
    public Document(int docId, String title, String body) {
        this.documentID = docId;
        this.title = title;
        this.body = body;
        this.tokenizer = new Tokenizer();
        this.terms = new ArrayList<>();
    }


    //  ACCESSORS

    /**
     * Returns the document id
     *
     * @return document id
     */
    public int getDocumentID() {
        return this.documentID;
    }

    /**
     * Returns a list of terms
     *
     * @return list of terms
     */
    public List<Term> getAllTerms() {
        List<String> titles = this.tokenizer.getTokens(this.title);
        List<String> bodies = this.tokenizer.getTokens(this.body);

        List<String> merged = new ArrayList<>();
        merged.addAll(titles);
        merged.addAll(bodies);

        merged = tokenizer.removeCaps(merged);
        merged = tokenizer.removePunctuation(merged);
        merged = tokenizer.stem(merged);
        merged = tokenizer.removeNumbers(merged);

        for (String s : merged) {
            Term term = new Term(s);
            term.addToPostingsList(this.documentID);
            this.terms.add(term);
        }

        return this.terms;
    }

}
