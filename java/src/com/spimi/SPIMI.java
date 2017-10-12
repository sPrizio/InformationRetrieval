package com.spimi;

import com.entity.Dictionary;

import java.io.File;

/**
 * Collection of Single-pass in-memory indexing algorithms for use in an information retrieval system
 *
 * @author Stephen Prizio
 */
public class SPIMI {
    private Dictionary dictionary;

    public SPIMI() {
        this.dictionary = new Dictionary();
    }

    public File spimiInvert() {
        //  refer to https://nlp.stanford.edu/IR-book/html/htmledition/single-pass-in-memory-indexing-1.html
        return new File("TEMP");
    }
}
