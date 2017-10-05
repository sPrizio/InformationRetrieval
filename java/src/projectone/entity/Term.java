package projectone.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a term taken from a textual document
 *
 * @author Stephen Prizio
 */
public class Term {
    private String value;
    private ArrayList<Integer> postingsList;
    private int termId;

    /**
     * Regular constructor taking a value for this term's string
     *
     * @param v string value for term
     */
    public Term(String v) {
        this.value = v;
        this.postingsList = new ArrayList<>();
        this.termId = v.hashCode();
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
    public List<Integer> getPostingsList() {
        return this.postingsList;
    }

    /**
     * Returns the id of this term
     *
     * @return term id
     */
    public int getTermId() {
        return this.termId;
    }


    //  METHODS

    /**
     * Adds a document id to this term's postings list
     *
     * @param docId - document id to be added to the postings list
     */
    public void addToPostingsList(int docId) {
        this.postingsList.add(docId);
    }

    public void addToPostingsList(List<Integer> docIds) {
        this.postingsList.addAll(docIds);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

   @Override
    public boolean equals(Object t) {
        if (t instanceof Term && this.value.equalsIgnoreCase(((Term) t).value)) {
            this.addToPostingsList(((Term) t).getPostingsList());
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return (this.value + " - " + this.postingsList);
    }
}
