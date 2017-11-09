package com.entity;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * A class representation of a document ID and its ranking score
 *
 * @author Stephen Prizio
 */
public class Score implements Comparable<Score> {
    private int id;
    private double score;
    private Random random;
    private int randomInt;
    private DecimalFormat decimalFormat;

    public Score(int iD, double s) {
        this.decimalFormat = new DecimalFormat("####.000");
        this.id = iD;
        this.score = Double.parseDouble(this.decimalFormat.format(s));
        this.random = new Random();
        this.randomInt = this.random.nextInt();
    }

    public int getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        return (this.id + this.randomInt);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Score && this.id == ((Score) obj).getId() && this.score == ((Score) obj).getScore());
    }

    @Override
    public int compareTo(Score o) {
        return Double.compare(this.score, o.score);
    }

    @Override
    public String toString() {
        return (this.id + " : " + this.score);
    }
}
