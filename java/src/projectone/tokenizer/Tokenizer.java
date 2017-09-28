package projectone.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Responsible for term tokenization and normalization
 *
 * @author Stephen Prizio
 */
public class Tokenizer {
    private StringTokenizer stringTokenizer;
    private ArrayList<String> tokens;

    public Tokenizer(String someText) {
        stringTokenizer = new StringTokenizer(someText);
        tokens = new ArrayList<>(stringTokenizer.countTokens());
    }

    /**
     * Returns an list of tokens
     *
     * @return list of tokens from file
     */
    public List<String> getTokens() {
        while (stringTokenizer.hasMoreTokens()) {
            tokens.add(stringTokenizer.nextToken());
        }
        return tokens;
    }


}
