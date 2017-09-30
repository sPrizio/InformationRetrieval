package projectone.tokenizer;

import java.util.*;

/**
 * Responsible for term tokenization and normalization
 *
 * @author Stephen Prizio
 */
public class Tokenizer {
    private ArrayList<String> tokens;

    public Tokenizer() {
        tokens = new ArrayList<>();
    }

    /**
     * Returns a list of tokens, without duplicates
     *
     * @return list of tokens from file
     */
    public List<String> getTokens(String text) {
        StringTokenizer stringTokenizer = new StringTokenizer(text);
        while (stringTokenizer.hasMoreTokens()) {
            this.tokens.add(stringTokenizer.nextToken());
        }
        Set<String> strings = new HashSet<>();
        strings.addAll(this.tokens);
        this.tokens.clear();
        this.tokens.addAll(strings);

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

        return temp;
    }
}
//\d{4}|\/\d{2}