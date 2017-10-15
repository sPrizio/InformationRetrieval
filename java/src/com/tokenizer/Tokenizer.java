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
    private static final String[] THIRY_STOP_WORDS = { "a", "and", "as", "at", "be", "but", "by", "do", "for", "from", "have", "his", "in", "it", "not", "of", "on", "or", "say", "she", "that", "that", "the",
            "they", "this", "to", "to", "we", "with", "you" };
    private static final String[] ONE_FIFTY_STOP_WORDS = { "I", "a", "about", "after", "all", "also", "and", "another", "any", "as", "as", "as", "ask", "at", "back", "be", "because", "become", "between", "but",
            "by", "call", "can", "child", "come", "could", "day", "do", "down", "even", "family", "feel", "find", "first", "for", "from", "get", "give", "go", "good", "have", "he", "her", "her", "here", "high",
            "him", "his", "how", "if", "in", "in", "into", "it", "its", "just", "know", "last", "leave", "life", "like", "look", "make", "man", "many", "may", "me", "more", "more", "most", "much", "my", "n't",
            "need", "never", "new", "no", "not", "now", "of", "on", "one", "one", "only", "or", "other", "our", "out", "out", "over", "own", "people", "really", "say", "school", "see", "she", "should", "so",
            "some", "something", "state", "still", "take", "tell", "than", "that", "that", "the", "their", "them", "then", "there", "there", "these", "they", "thing", "think", "this", "those", "three",
            "through", "time", "to", "to", "too", "try", "two", "up", "us", "use", "very", "want", "way", "we", "well", "what", "when", "when", "which", "who", "will", "with", "woman", "work", "world", "would",
            "year", "you", "your" };

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

    /**
     * Removes the 30 most common stop words
     *
     * @param tokens - list of tokens extracted from document
     * @return list of tokens without 30 most common stop words
     */
    public List<String> remove30StopWords(List<String> tokens) {
        List<String> temp = new ArrayList<>();

        for (int i = 0; i < tokens.size(); ++i) {
            Boolean found = false;
            for (int j = 0; j < THIRY_STOP_WORDS.length; ++j) {
                if (tokens.get(i).equalsIgnoreCase(THIRY_STOP_WORDS[j])) {
                    found = true;
                }
            }

            if (!found) {
                temp.add(tokens.get(i));
            }
        }

        return temp;
    }

    /**
     * Removes the 150 most common stop words
     *
     * @param tokens - list of tokens extracted from document
     * @return list of tokens without 150 most common stop words
     */
    public List<String> remove150StopWords(List<String> tokens) {
        List<String> temp = new ArrayList<>();

        for (int i = 0; i < tokens.size(); ++i) {
            Boolean found = false;
            for (int j = 0; j < ONE_FIFTY_STOP_WORDS.length; ++j) {
                if (tokens.get(i).equalsIgnoreCase(ONE_FIFTY_STOP_WORDS[j])) {
                    found = true;
                }
            }

            if (!found) {
                temp.add(tokens.get(i));
            }
        }

        return temp;
    }

    /**
     * Removes all numbers
     *
     * @param tokens - list of tokens extracted from document
     * @return tokens without numbers
     */
    public List<String> removeNumbers(List<String> tokens) {
        List<String> temp = new ArrayList<>();

        for (String s : tokens) {
            temp.add(s.trim().replaceAll("\\d*", ""));
        }

        return temp;
    }
}
