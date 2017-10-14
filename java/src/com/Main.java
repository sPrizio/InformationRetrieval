package com;


import com.entity.Document;
import com.entity.Term;
import com.parsers.TotalParser;
import com.spimi.SPIMI;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        //  configures logging pattern to show only the log message
        PropertyConfigurator.configure("java/resources/lib/log4j.properties");

        TotalParser totalParser = new TotalParser();
        List<Document> documents = totalParser.getDocuments();

        List<Term> docTerms = new ArrayList<>();

        for (Document document : documents) {
            docTerms.addAll(document.getAllTerms());
        }

        SPIMI spimi = new SPIMI();
        spimi.spimi(docTerms, 1500000);  //  500,000 is a decent starting point for slowest simulations, collections hover around 5,700,000 terms
        spimi.clearData();  //  clears data created by spimi
    }
}
