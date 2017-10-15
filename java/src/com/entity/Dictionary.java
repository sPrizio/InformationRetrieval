package com.entity;

import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

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

    public Set<Term> getKeySet() {
        return this.termDictionary.keySet();
    }

    public Set<Integer> getUniversalSet() {
        Set<Integer> set = new TreeSet<>();

        for (int i = 1; i <= 21578; ++i) {
            set.add(i);
        }

        return set;
    }
    //  METHODS

    /**
     * Adds a term to this dictionary
     *
     * @param term - term to be added
     */
    public void addToDictionary(Term term) {
        this.termDictionary.put(term, term.getPostingsList());
    }

    /**
     * Clears this dictionary of entries
     */
    public void clear() {
        this.termDictionary.clear();
    }

    public Set<Integer> getPostingList(Term key) {
        return this.termDictionary.get(key);
    }

}
