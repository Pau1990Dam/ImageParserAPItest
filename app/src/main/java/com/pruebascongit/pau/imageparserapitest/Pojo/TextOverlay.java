package com.pruebascongit.pau.imageparserapitest.Pojo;

import java.util.ArrayList;


public class TextOverlay {

    private ArrayList<Line> lines;
    private boolean hasOverlay;
    private String message;

    public TextOverlay(){}

    public TextOverlay(ArrayList<Line> lines, boolean hasOverlay, String message) {
        this.lines = lines;
        this.hasOverlay = hasOverlay;
        this.message = message;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public boolean hasOverlay() {
        return hasOverlay;
    }

    public void setHasOverlay(boolean hasOverlay) {
        this.hasOverlay = hasOverlay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "TextOverlay{" +
                "lines=" + lines +
                ", hasOverlay=" + hasOverlay +
                ", message='" + message + '\'' +
                '}';
    }


}
