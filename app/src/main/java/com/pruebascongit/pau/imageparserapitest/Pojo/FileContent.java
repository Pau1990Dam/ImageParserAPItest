package com.pruebascongit.pau.imageparserapitest.Pojo;

/**
 * Created by pau on 5/06/17.
 */

public class FileContent {

    ParsedResults parsedResults;
    int ocrExitCode;
    boolean isErroredOnProcessing;
    String errorMessage;
    String errorDetails;
    String  procesingTimeInMilliseconds;

    public FileContent() {
    }

    public FileContent(ParsedResults parsedResults, int ocrExitCode, boolean isErroredOnProcessing, String errorMessage, String errorDetails, String procesingTimeInMilliseconds) {
        this.parsedResults = parsedResults;
        this.ocrExitCode = ocrExitCode;
        this.isErroredOnProcessing = isErroredOnProcessing;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
        this.procesingTimeInMilliseconds = procesingTimeInMilliseconds;
    }

    public ParsedResults getParsedResults() {
        return parsedResults;
    }

    public void setParsedResults(ParsedResults parsedResults) {
        this.parsedResults = parsedResults;
    }

    public String getOcrExitCode() {

        switch (ocrExitCode){
            case 1:
                return "File parsing completed successfully";
            case 2:
                return "File partially parsed";
            case 3:
                return "Failed parsing";
            case 4:
            default:
                return "Error ocurred when attempting parse";
        }
    }

    public void setOcrExitCode(int ocrExitCode) {
        this.ocrExitCode = ocrExitCode;
    }

    public boolean isErroredOnProcessing() {
        return isErroredOnProcessing;
    }

    public void setErroredOnProcessing(boolean erroredOnProcessing) {
        isErroredOnProcessing = erroredOnProcessing;
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

    public String getProcesingTimeInMilliseconds() {
        return procesingTimeInMilliseconds;
    }

    public void setProcesingTimeInMilliseconds(String procesingTimeInMilliseconds) {
        this.procesingTimeInMilliseconds = procesingTimeInMilliseconds;
    }

    @Override
    public String toString() {
        return "FileContent{" +
                "parsedResults=" + parsedResults +
                ", ocrExitCode=" + ocrExitCode +
                ", isErroredOnProcessing=" + isErroredOnProcessing +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorDetails='" + errorDetails + '\'' +
                ", procesingTimeInMilliseconds=" + procesingTimeInMilliseconds +
                '}';
    }
}
