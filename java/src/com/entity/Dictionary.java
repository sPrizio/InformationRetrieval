package com.entity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * A dictionary object holding terms and their postings lists
 *
 * @author Stephen Prizio
 */
public class Dictionary {
    private Hashtable<Term, Set<Integer>> termDictionary;
    private Set<Term> terms;

    /**
     * Default constructor
     */
    public Dictionary() {
        this.termDictionary = new Hashtable<>();
    }


    //  ACCESSORS

    public Set<Term> getTerms() {
        return this.terms;
    }


    //  METHODS

    /**
     * Builds an inverted index of terms containing correctly updates postings lists
     *
     * @param documents - collection of documents to be indexed
     * @return inverted index of terms with postings lists reflecting containing documents
     */
    public Set<Term> index(List<Document> documents) {
        List<Term> docTerms = new ArrayList<>();

        for (Document document : documents) {
            docTerms.addAll(document.getAllTerms());
        }

        for (Term t : docTerms) {
            this.termDictionary.put(t, t.getPostingsList());
        }

        docTerms.clear();

        this.terms = this.termDictionary.keySet();

        return this.terms;
    }
}
