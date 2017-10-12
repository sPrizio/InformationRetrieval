package com.tokenizer;

import org.tartarus.martin.Stemmer;

import java.util.*;

/**
 * Responsible for term tokenization and normalization
 *
 * @author Stephen Prizio
 */
public class Tokenizer {
    private ArrayList<String> tokens;
    private Stemmer stemmer;

    public Tokenizer() {
        tokens = new ArrayList<>();
        stemmer = new Stemmer();
    }

    /**
     * Returns a list of tokens
     *
     * @return list of tokens from file
     */
    public List<String> getTokens(String text) {
        StringTokenizer stringTokenizer = new StringTokenizer(text);
        while (stringTokenizer.hasMoreTokens()) {
            this.tokens.add(stringTokenizer.nextToken());
        }

        return this.tokens;
    }

    /**
     * Case folding algorithm, removes all capitalization from tokens
     *
     * @param tokens - list of tokens extracted from a document
     * @return list of tokens, all lowercase
     */
    public List<String> removeCaps(List<String> tokens) {
        List<String> temp = new ArrayList<>();
        for (String s : tokens) {
            temp.add(s.toLowerCase());
        }

        return temp;
    }

    /**
     * Algorithm that removes standard punctuation from tokens
     *
     * @param tokens - list of tokens extracted from a document
     * @return list of tokens without punctuation
     */
    public List<String> removePunctuation(List<String> tokens) {
        List<String> temp = new ArrayList<>();
        for (String s : tokens) {
            temp.add(s.replaceAll("[\\W*]", " ").trim());
        }

        tokens.clear();

        for (String s : temp) {
            tokens.add(s.replaceAll("[\\s*]", "-"));
        }

        return tokens;
    }

    /**
     * Uses porter stemming to retrieve morphological root of each token
     *
     * @param tokens - list of tokens extracted from document
     * @return list of tokens each of which is their morphological root
     */
    public List<String> stem(List<String> tokens) {
        List<String> temp = new ArrayList<>();
        for (String s : tokens) {
            this.stemmer.add(s.toCharArray(), s.length());
            this.stemmer.stem();
            temp.add(this.stemmer.toString());
        }

        return temp;
    }
}
