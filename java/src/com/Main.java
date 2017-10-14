package com;


import com.entity.Document;
import com.entity.Term;
import com.parsers.TotalParser;
import com.query.QueryHandler;
import com.spimi.SPIMI;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //  configures logging pattern to show only the log message
        PropertyConfigurator.configure("java/resources/lib/log4j.properties");

        //  gets all documents for indexing
        TotalParser totalParser = new TotalParser();
        List<Document> documents = totalParser.getDocuments();

        List<Term> docTerms = new ArrayList<>();

        //  gets every term from each document
        for (Document document : documents) {
            docTerms.addAll(document.getAllTerms());
        }

        //  runs  the SPIMI algorithm on the collection to build the inverted index
        SPIMI spimi = new SPIMI();
        spimi.spimi(docTerms, 0);  //  500,000 is a decent starting point for slowest simulations, collections hover around 5,700,000 tokens
        //spimi.clearData();  //  clears data created by spimi

        //  runs the query handler to fetch documents requested by the user
        QueryHandler queryHandler = new QueryHandler(spimi.getInvertedIndex());
        queryHandler.run();     //  accept new queries
    }
}
