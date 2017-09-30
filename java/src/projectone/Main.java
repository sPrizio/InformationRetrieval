package projectone;


import org.apache.log4j.BasicConfigurator;
import projectone.parsers.FileParser;
import projectone.tokenizer.Tokenizer;

import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        BasicConfigurator.configure();
        Tokenizer tokenizer = new Tokenizer();

        FileParser fileParser = new FileParser("java/resources/sgm/reut2-000.sgm");
        List<String> strings = fileParser.getDocuments().get(0).getAllTokens();
        strings = tokenizer.removePunctuation(strings);
        System.out.println(strings);
    }
}
