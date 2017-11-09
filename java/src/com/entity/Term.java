package com.entity;

import java.util.*;

/**
 * I certify that this submission is my original work and meets the Faculty’s Expectations of Originality - 16 October 2017
 *
 * Represents a term taken from a textual document
 *
 * @author Stephen Prizio - 40001739
 */
public class Term implements Comparable<Term> {
    private String value;
    private Set<Integer> postingsList;

    /**
     * Regular constructor taking a value for this term's string
     *
     * @param v string value for term
     */
    public Term(String v) {
        this.value = v;
        this.postingsList = new TreeSet<>();
    }


    //  ACCESSORS

    /**
     * Returns the string value of this term
     *
     * @return term's string
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns the list of documents in which this term occurs
     *
     * @return list of document id's
     */
    Set<Integer> getPostingsList() {
        return this.postingsList;
    }


    //  METHODS

    /**
     * Adds a document id to this term's postings list
     *
     * @param docId - document id to be added to the postings list
     */
    void addToPostingsList(int docId) {
        this.postingsList.add(docId);
    }

    public void addToPostingsList(Set<Integer> docIds) {
        this.postingsList.addAll(docIds);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object t) {
        if (t instanceof Term && this.value.equals(((Term) t).value)) {
            this.addToPostingsList(((Term) t).getPostingsList());
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return (this.value + " - " + this.postingsList);
    }

    @Override
    public int compareTo(Term term) {
        return this.value.compareToIgnoreCase(term.getValue());
    }

    public int getDocumentFrequency() {
        return this.postingsList.size();
    }
}
