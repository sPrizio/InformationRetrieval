package com.ranking;

import com.entity.Dictionary;
import com.entity.Document;
import com.entity.Score;
import com.entity.Term;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Ranking class responsible for ranking documents according to their relevancy
 *
 * @author Stephen Prizio
 */
public class Ranker {
    private double k;
    private double b;
    private List<Document> documents;
    private Dictionary dictionary;

    /**
     * Default constructor
     */
    public Ranker() {
        this.k = 0;
        this.b = 0;
        this.documents = new ArrayList<>();
    }

    /**
     * Regular constructor taking a list of documents and a query
     *
     * @param d - list of documents in collection
     * @param dict - inverted index
     */
    public Ranker(List<Document> d, Dictionary dict) {
        this.k = 0;
        this.b = 0;
        this.documents = d;
        this.dictionary = dict;
    }


    //  METHODS

    /**
     * Okapi BM25 ranking algorithm implementation that returns the result of containing documents sorted by their relevance score
     *
     * @param queryTerms - user query
     * @param containingSet - postings list for the query
     * @param documents - collection
     * @return postings list sorted by score
     */
    public Set<Integer> okapiBM25(List<Term> queryTerms, Set<Integer> containingSet, List<Document> documents) {
        this.b = 0.75;
        this.k = 1.6;
        HashMap<Integer, Double> scores = new HashMap<>();
        List<Score> scoresList = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();

        if (containingSet.isEmpty()) {
            return new HashSet<>();
        }

        for (Integer integer : containingSet) {
            scores.put(integer, (double) 0);
        }


        for (Term term : queryTerms) {
            Document document = new Document();
            double score;

            for (Integer integer : containingSet) {
                for (Document d : documents) {
                    if (d.getDocumentID() == integer) {
                        document = d;
                        break;
                    }
                }

                double numerator = (document.getTermFrequency(term) * (this.k + 1));
                double divisor = (this.k * ((1 - this.b) + b * (document.getDocumentLength() / averageDocumentLength()))) + document.getTermFrequency(term);

                score = (idf(term) * (numerator / divisor));

                double temp = scores.get(integer);

                temp += score;

                scores.put(integer, temp);
            }
        }

        for (Map.Entry<Integer, Double> entry : scores.entrySet()) {
            scoresList.add(new Score(entry.getKey(), entry.getValue()));
        }

        scoresList.sort(Comparator.reverseOrder());

        for (Score s : scoresList) {
            set.add(s.getId());
        }

        return set;
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
            return 1;
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
        double log = (((double) this.documents.size()) / ((double) this.dictionary.getPostingList(term).size()));
        return (Math.log(log));
    }
}
