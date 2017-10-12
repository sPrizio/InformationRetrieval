package com;


import com.entity.Dictionary;
import com.entity.Document;
import com.entity.Term;
import com.parsers.FileParser;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        //  configures logging pattern to show only the log message
        PropertyConfigurator.configure("java/resources/lib/log4j.properties");

        FileParser fileParser = new FileParser("java/resources/sgm/reut2-000.sgm");
        List<Document> documents = fileParser.getDocuments();

        Dictionary dictionary = new Dictionary();
        Set<Term> set = dictionary.index(documents);

       /* for (Document d : documents) {
            if (d.getAllTerms().isEmpty()) {
                System.out.print(d.getDocumentID() + " ");
            }
            //System.out.println(d.getAllTerms());
        }*/

        for (Term t : set) {
           logger.info(t);
        }
    }
}
