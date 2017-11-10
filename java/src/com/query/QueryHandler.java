package com.query;

import com.entity.Dictionary;
import com.entity.Document;
import com.entity.Term;
import com.ranking.Ranker;
import org.apache.log4j.Logger;
import org.tartarus.martin.Stemmer;

import java.util.*;

/**
 * I certify that this submission is my original work and meets the Faculty’s Expectations of Originality - 16 October 2017
 *
 * Handles queries passed by the user
 *
 * @author Stephen Prizio - 40001739
 */
public class QueryHandler {
    private Dictionary invertedIndex;
    private static final Scanner keyIn = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(QueryHandler.class);
    private String input;
    private Stemmer stemmer;
    private Ranker ranker;
    private List<Document> documents;

    /**
     * Regular constructor that takes an inverted index on which to perform lookups
     *
     * @param dictionary - inverted index
     */
    public QueryHandler(Dictionary dictionary, List<Document> d) {
        this.invertedIndex = dictionary;
        this.input = "";
        this.stemmer = new Stemmer();
        this.ranker = new Ranker(d, dictionary);
        this.documents = d;
    }

    /**
     * Runs the query handler and accepts user input
     */
    public void run() {
        logger.info("Welcome to Speemer! Type in a query to find some cool information! Type quit to exit");
        String[] inputParameters;

        do {
            this.input = keyIn.nextLine();  //  gets the command
            inputParameters = this.input.trim().split("\\s+");  //  converts query to array of parameters
            control(inputParameters);
        } while (!this.input.equalsIgnoreCase("quit"));

        logger.info("Goodbye.");
    }

    /**
     * Handles queries and finds and displays appropriate matching postings
     *
     * @param params - each part of the query
     */
    private void control(String[] params) {
        if (params.length == 1) {
            String[] cleaned = cleanInput(params);

            printResults(queryMatchNoOperators(cleaned));
        } else {
            String[] cleaned = cleanInput(params);

            printResults(queryMatchNoOperators(cleaned));
        }
    }

    /**
     * Prints the results of the lookup
     *
     * @param set - intersection set containing the matching documents
     */
    private void printResults(Set<Integer> set) {
        if (set != null) {
            if (set.isEmpty()) {
                logger.info("No results were found for your query. Please try something else.");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The following documents matched the query '" + this.input + "': ");
                stringBuilder.append(set);

                logger.info(stringBuilder);
            }
        } else {
            logger.info("No results were found for your query. Please try something again.");
        }
    }

    /**
     * Filters queries to match the tokenization performed on the terms in the inverted index
     *
     * @param s - subsection of the query
     * @return normalized term for lookup
     */
    private String filter(String s) {
        String returnString = s.trim().toLowerCase();

        this.stemmer.add(returnString.toCharArray(), returnString.length());
        this.stemmer.stem();
        returnString = this.stemmer.toString();

        returnString = returnString.replaceAll("\\d*", "");

        return returnString;
    }

    /**
     * Cleans input from user to match terms in index
     *
     * @param strings - user query
     * @return - cleaned user query
     */
    private String[] cleanInput(String[] strings) {
        String[] temp = new String[strings.length];

        for (int i = 0; i < strings.length; ++i) {
            if (!(strings[i].equals("AND") || strings[i].equals("OR") || strings[i].equals("NOT"))) {
                temp[i] = filter(strings[i]);
            } else {
                temp[i] = strings[i];
            }
        }

        return temp;
    }

    /**
     * Matches terms without the need for boolean operators (assumes AND)
     *
     * @param params - user query
     * @return intersection list of query
     */
    private Set<Integer> queryMatchNoOperators(String[] params) {
        logger.info("Searching...");
        List<Set<Integer>> postings = new ArrayList<>();
        List<Term> terms = new ArrayList<>();

        for (String s : params) {
            Term t = new Term(s);
            terms.add(t);
            postings.add(this.invertedIndex.getPostingList(t));
        }

        return this.ranker.okapiBM25(terms, match(postings, postings.get(0), 0), this.documents);
    }

    /**
     * Finds the common elements between 2 sets
     *
     * @param set1 - first set
     * @param set2 - second set
     * @return new set containing common elements
     */
    private Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> results = new TreeSet<>();

        if (set1 == null || set2 == null) {
            return results;
        }

        for (Integer i : set1) {
            if (set2.contains(i)) {
                results.add(i);
            }
        }

        return results;
    }

    /**
     * Finds the intersection of multiple sets
     *
     * @param list - list of postings lists
     * @return list of common elements
     */
    private Set<Integer> match(List<Set<Integer>> list, Set<Integer> set, int counter) {
        if (counter == list.size() - 1) {
            return set;
        }

        Set<Integer> i = intersection(set, list.get(counter + 1));

        return match(list, i, counter + 1);
    }

}
