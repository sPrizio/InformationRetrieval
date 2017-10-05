package projectone;


import org.apache.log4j.BasicConfigurator;
import projectone.entity.Document;
import projectone.entity.Term;
import projectone.parsers.FileParser;

import java.util.*;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        BasicConfigurator.configure();

        List<Term> docTerms = new ArrayList<>();
        List<Term> docTerms2 = new ArrayList<>();
        List<Term> test = new ArrayList<>();
        Set<Term> terms = new HashSet<>();

        FileParser fileParser = new FileParser("java/resources/sgm/reut2-000.sgm");
        List<Document> documents = fileParser.getDocuments();

        Hashtable<Term, List<Integer>> dictionary = new Hashtable<>();

        for (Document document : documents) {
            docTerms.addAll(document.getAllTerms());
        }

        for (Term t : docTerms) {
            dictionary.put(t, t.getPostingsList());
        }

        System.out.println(dictionary);
    }
}
