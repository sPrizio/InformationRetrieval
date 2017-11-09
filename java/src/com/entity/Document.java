package com.entity;

import com.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a document that is to be indexed
 *
 * @author Stephen Prizio
 */
public class Document {
    private int documentID;
    private String title;
    private String body;
    private Tokenizer tokenizer;
    private List<Term> terms;

    public Document() {
        this.documentID = 0;
        this.title = "";
        this.body = "";
    }

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

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }


    //  METHODS

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

    /**
     * Returns length of document (number of counted terms)
     *
     * @return length of document
     */
    public int getDocumentLength() {
        if (this.terms.isEmpty()) {
            return getAllTerms().size();
        }

        return this.terms.size();
    }

    /**
     * Returns the number of times a term appears in a document
     *
     * @param term - queried term
     * @return occurrences of this term in the document
     */
    public int getTermFrequency(Term term) {
        Tokenizer t = new Tokenizer();
        int count = 0;
        Term norm = normalizeTerm(term.getValue());

        List<String> strings = new ArrayList<>();
        strings.addAll(t.getTokens(this.title));
        strings.addAll(t.getTokens(this.body));

        strings = t.removeCaps(strings);
        strings = t.removeNumbers(strings);
        strings = t.removePunctuation(strings);
        strings = t.stem(strings);

        for (String s : strings) {
            if (s.equalsIgnoreCase(norm.getValue().toLowerCase().trim())) {
                ++count;
            }
        }

        return count;
    }


    //  HELPERS

    /**
     * Normalizes term into root form
     *
     * @param s - term string value
     * @return normalized term
     */
    private Term normalizeTerm(String s) {
        List<String> merged = new ArrayList<>();
        merged.add(s);

        merged = tokenizer.removeCaps(merged);
        merged = tokenizer.removePunctuation(merged);
        merged = tokenizer.stem(merged);
        merged = tokenizer.removeNumbers(merged);

        return new Term(merged.get(0));
    }

}
