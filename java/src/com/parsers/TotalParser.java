package com.parsers;

import com.entity.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * I certify that this submission is my original work and meets the Faculty’s Expectations of Originality - 16 October 2017
 *
 * Responsible for parsing every available sgm file and returning the entire collection of available documents
 *
 * @author Stephen Prizio - 40001739
 */
public class TotalParser {
    private StringBuilder text;
    private List<Document> documents;

    /**
     * Regular constructor that creates the parser and reads all the files
     */
    public TotalParser() {
        this.text = new StringBuilder();
        this.documents = new ArrayList<>();

        readFiles();    //  reads all sgm files
    }

    /**
     * Returns a list of documents from every file
     *
     * @return total list of documents
     */
    public List<Document> getDocuments() {
        return this.documents;
    }

    /**
     * Returns the contents of the files
     *
     * @return contents of the files as a string
     */
    public String getText() {
        return this.text.toString();
    }

    /**
     * Reads each sgm file and gets its documents
     */
    private void readFiles() {
        for (int i = 0; i < 22; ++i) {
            FileParser fileParser = new FileParser("java/resources/sgm/reut2-" + fileNumberDeducer(i) + ".sgm");
            this.documents.addAll(fileParser.getDocuments());
        }
    }

    /**
     * Correctly determines file number to append to file name for consistent looping
     *
     * @param counter - current value of the counter
     * @return string representation of current counter
     */
    private String fileNumberDeducer(int counter) {
        if (counter < 10) {
            return "00" + counter;
        } else if (counter < 100) {
            return "0" + counter;
        }

        return "";
    }
}
