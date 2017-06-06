package com.pruebascongit.pau.imageparserapitest.Pojo;

/**
 * Created by pau on 5/06/17.
 */


public class Word{
    String wordText;
    double left;
    double top;
    double height;
    double width;

    public Word(){}

    public Word(String wordText, double left, double top, double height, double width) {
        this.wordText = wordText;
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
    }

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordText='" + wordText + '\'' +
                ", left=" + left +
                ", top=" + top +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}