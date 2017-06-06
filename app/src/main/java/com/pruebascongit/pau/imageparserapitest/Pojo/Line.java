package com.pruebascongit.pau.imageparserapitest.Pojo;

import java.util.ArrayList;

public class Line{

    ArrayList<Word> words;
    double MaxHeight;
    double MinTop;

    public Line(){};

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public double getMaxHeight() {
        return MaxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        MaxHeight = maxHeight;
    }

    public double getMinTop() {
        return MinTop;
    }

    public void setMinTop(double minTop) {
        MinTop = minTop;
    }

    @Override
    public String toString() {
        return "Line{" +
                "words=" + words +
                ", MaxHeight=" + MaxHeight +
                ", MinTop=" + MinTop +
                '}';
    }

}