package com.pruebascongit.pau.imageparserapitest.Pojo;

/**
 * Created by pau on 5/06/17.
 */

public class ParsedResults {

    TextOverlay textOverlay;
    int fileParserExitCode;
    String parsedText;
    String errorMessage;
    String errorDetails;

    public ParsedResults() {
    }

    public ParsedResults(TextOverlay textOverlay, int fileParserExitCode, String parsedText, String errorMessage, String errorDetails) {
        this.textOverlay = textOverlay;
        this.fileParserExitCode = fileParserExitCode;
        this.parsedText = parsedText;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public TextOverlay getTextOverlay() {
        return textOverlay;
    }

    public void setTextOverlay(TextOverlay textOverlay) {
        this.textOverlay = textOverlay;
    }

    public int getFileParserExitCode() {
        return fileParserExitCode;
    }

    public void setFileParserExitCode(int fileParserExitCode) {
        this.fileParserExitCode = fileParserExitCode;
    }

    public String getParsedText() {
        return parsedText;
    }

    public void setParsedText(String parsedText) {
        this.parsedText = parsedText;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    @Override
    public String toString() {
        return "ParsedResults{" +
                "textOverlay=" + textOverlay +
                ", fileParserExitCode=" + fileParserExitCode +
                ", parsedText='" + parsedText + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorDetails='" + errorDetails + '\'' +
                '}';
    }

    public String getErrorMessages(){
        return "Error: "+errorMessage
                +"\n\tError Details: "+errorDetails;
    }
}
