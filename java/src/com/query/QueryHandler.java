package com.query;

import com.entity.Dictionary;
import com.entity.Term;
import org.apache.log4j.Logger;
import org.tartarus.martin.Stemmer;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Handles queries passed by the user
 *
 * @author Stephen Prizio
 */
public class QueryHandler {
    private Dictionary invertedIndex;
    private static final Scanner keyIn = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(QueryHandler.class);
    private String input;
    private Stemmer stemmer;

    /**
     * Regular constructor that takes an inverted index on which to perform lookups
     *
     * @param dictionary - inverted index
     */
    public QueryHandler(Dictionary dictionary) {
        this.invertedIndex = dictionary;
        this.input = "";
        this.stemmer = new Stemmer();
    }

    /**
     * Runs the query handler and accepts user input
     */
    public void run() {
        logger.info("Welcome to Speemer! Type in a boolean query with 2 words separated by AND, OR and NOT to find some cool information! Type quit to exit");
        String[] inputParameters;

        do {
            this.input = keyIn.nextLine();  //  gets the command
            inputParameters = this.input.trim().split("\\s+");  //  converts query to array of parameters
            control(inputParameters);
        } while(!this.input.equalsIgnoreCase("quit"));

        logger.info("Goodbye.");
    }

    /**
     * Handles queries and finds and displays appropriate matching postings
     *
     * @param params - each part of the query
     */
    private void control(String[] params) {
        if (params.length == 1) {
            Term term = new Term(filter(params[0]));
            printResults(this.invertedIndex.getPostingList(term));
        } else {
            String[] cleaned = cleanInput(params);
            printResults(queryMatch(cleaned));
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
                stringBuilder.append("The following documents matched the query: ");
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

    private Set<Integer> queryMatch(String[] params) {
        if (params[0].equals("AND") || params[0].equals("OR") || params[0].equals("NOT") || params.length > 4) {
            return null;
        }

        if (params[1].equals("AND")) {
            Term term1 = new Term(params[0]);
            Set<Integer> set1 = this.invertedIndex.getPostingList(term1);

            Term term2 = new Term(params[2]);
            Set<Integer> set2 = this.invertedIndex.getPostingList(term2);

            set1.retainAll(set2);

            return set1;
        } else if (params[1].equals("OR")) {
            Term term1 = new Term(params[0]);
            Set<Integer> set1 = this.invertedIndex.getPostingList(term1);

            Term term2 = new Term(params[2]);
            Set<Integer> set2 = this.invertedIndex.getPostingList(term2);

            set1.addAll(set2);

            return set1;
        }

        return null;
    }
}
