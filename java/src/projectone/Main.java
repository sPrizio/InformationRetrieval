package projectone;


import org.apache.log4j.BasicConfigurator;
import projectone.entity.Dictionary;
import projectone.entity.Document;
import projectone.entity.Term;
import projectone.parsers.FileParser;

import java.util.*;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        BasicConfigurator.configure();

        FileParser fileParser = new FileParser("java/resources/sgm/reut2-000.sgm");
        List<Document> documents = fileParser.getDocuments();

        Dictionary dictionary = new Dictionary();
        Set<Term> set = dictionary.index(documents);

        for (Term t : set) {
            System.out.println(t);
        }
    }
}
