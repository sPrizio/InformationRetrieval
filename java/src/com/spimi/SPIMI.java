package com.spimi;

import com.entity.Dictionary;
import com.entity.Term;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Implementation of the SPIMI algorithm, taking in a list of terms to be indexed and a memory limit (here stimulated by a limit of terms for one chunk)
     *
     * @param terms       - terms to be indexed
     * @param memoryLimit - maximum number of terms for one chunk to stimulate a memory limitation
     */
    public File spimi(List<Term> terms, int memoryLimit) {
        List<List<Term>> chunks = new ArrayList<>();
        List<File> memoryChunks = new ArrayList<>();

        //  partition token list into lists of equal size, stimulates small memory capacity
        for (int i = 0; i < terms.size(); i += memoryLimit) {
            chunks.add(terms.subList(i, Math.min(i + memoryLimit, terms.size())));
        }

        //  for each "memory chunk", create an inverted index and write it to disk
        for (List<Term> list : chunks) {
            memoryChunks.add(spimiInvert(list));
        }

        return mergeChunks(memoryChunks);
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
    private File spimiInvert(List<Term> terms) {
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

    private File mergeChunks(List<File> files) {
        File file = new File("java/resources/results/inverted-index.txt");
        StringBuilder stringBuilder = new StringBuilder();

        for (File f : files) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(f))) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
            } catch (IOException e) {
                logger.error("File could not be found.");
            }

            Pattern pattern = Pattern.compile("(.*) - (\\[.*\\])");
            Matcher matcher = pattern.matcher(stringBuilder.toString());

            while (matcher.find()) {
                Term term = new Term(matcher.group(1));
                term.addToPostingsList(convertToSet(matcher.group(2)));

                this.dictionary.addToDictionary(term);
            }

            f.delete();
        }

        //  get dictionary
        Set<Term> terms = this.dictionary.getKeySet();

        //  sort terms
        terms = sortTerms(terms);

        //  write dictionary to file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (Term t : terms) {
                bufferedWriter.write(t + "\n");
            }
        } catch (IOException e) {
            logger.error("Error, cannot write to disc.");
        }

        return file;
    }


    //  HELPERS

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

    private Set<Integer> convertToSet(String setString) {
        String convertee = setString.replaceAll("\\[|\\]", "");

        Set<Integer> integers = new TreeSet<>();

        List<String> list = Arrays.asList(convertee.split(","));

        for (String s : list) {
            integers.add(Integer.parseInt(s.trim()));
        }

        return integers;
    }
}
