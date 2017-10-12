package com;


import com.entity.Document;
import com.entity.Term;
import com.parsers.FileParser;
import com.spimi.SPIMI;
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

        List<Term> docTerms = new ArrayList<>();

        for (Document document : documents) {
            docTerms.addAll(document.getAllTerms());
        }

        //  will have to create a method that calls spimiInvert repeatedly while memory limitation is not surpassed
        SPIMI spimi = new SPIMI();
        spimi.spimiInvert(docTerms);

    }
}
