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
    private ArrayList<String> postingsList;
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

    public String getValue() {
        return this.value;
    }

    public List<String> getPostingsList() {
        return this.postingsList;
    }

    public int getTermId() {
        return termId;
    }
}
