package com.spimi;

import com.entity.Dictionary;
import com.entity.Term;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Collection of Single-pass in-memory indexing algorithms for use in an information retrieval system
 *
 * @author Stephen Prizio
 */
public class SPIMI {
    private static final Logger logger = Logger.getLogger(SPIMI.class.getName());
    private Dictionary dictionary;
    private int fileCounter;

    /**
     * Default constructor that instantiates a new, empty inverted index
     */
    public SPIMI() {
        this.dictionary = new Dictionary();
        this.fileCounter = 0;
    }


    //  METHODS

    //  comment
    public void spimi(List<Term> terms, int memoryLimit) {
        List<List<Term>> chunks = new ArrayList<>();

        for (int i  = 0; i < terms.size(); i += memoryLimit) {
            chunks.add(terms.subList(i, Math.min(i + memoryLimit, terms.size())));
        }

        for (List<Term> list : chunks) {
            spimiInvert(list);
        }
    }


    /**
     * Java implementation of the SPIMI-Invert algorithm. The hash table implementation in Java already
     * handles previously existing entries, therefore the adding to dictionary and adding to postings lists
     * operations can be done in tandem, simplifying the algorithm. Additionally, each term's postings
     * list is itself a Set which will automatically double its size should its capacity become full,
     * further simplifying the algorithm.
     *
     * @param terms - term stream of terms to be added to the dictionary
     * @return file containing an inverted index of the given terms
     */
    public File spimiInvert(List<Term> terms) {
        File file = new File("java/resources/results/inverted-index-" + this.fileCounter + ".txt");
        Dictionary fileDictionary = new Dictionary();
        int counter = 0;    //  artificial memory parameter

        //  while memory is available, add terms to dictionary
        while (counter < terms.size()) {
            Term temp = terms.get(counter);     //  next term in stream

            //  java's hash table implementation already handles whether an entry is already an
            //  element of the table, therefore there's no need to check for it's existence
            fileDictionary.addToDictionary(temp);  //  add to dictionary

            ++counter;  //  increases counter to stimulate decrease in available memory
        }

        //  get the terms and the postings lists
        Set<Term> results = fileDictionary.getKeySet();

        //  sorts the terms
        results = sortTerms(results);

        //  writes to file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Term t : results) {
                bufferedWriter.write(t + "\n");
            }
            ++this.fileCounter;
        } catch (IOException e) {
            logger.error("Error, cannot write to disc.");
        }

        return file;
    }

    /**
     * Sorts all terms in a set
     *
     * @param terms - set of unsorted terms
     * @return set of sorted terms
     */
    private Set<Term> sortTerms(Set<Term> terms) {
        TreeSet<Term> sorted = new TreeSet<>();
        sorted.addAll(terms);

        return sorted;
    }
}
