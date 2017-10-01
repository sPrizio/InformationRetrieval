package projectone;


import org.apache.log4j.BasicConfigurator;
import projectone.entity.Document;
import projectone.entity.Term;
import projectone.parsers.FileParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        BasicConfigurator.configure();

        Set<Term> docTerms = new HashSet<>();
        ArrayList<Term> test = new ArrayList<>();

        FileParser fileParser = new FileParser("java/resources/sgm/reut2-000.sgm");
        List<Document> documents = fileParser.getDocuments();

        for (Document document : documents) {
            docTerms.addAll(document.getAllTerms());
            test.addAll(document.getAllTerms());
        }

        /*for (Term t : docTerms) {
            System.out.println(t);
        }*/

        System.out.println(test);
    }
}
