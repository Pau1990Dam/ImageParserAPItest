package com.pruebascongit.pau.imageparserapitest.ORC_API;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.pruebascongit.pau.imageparserapitest.HttpUtils.HpptUtils;
import com.pruebascongit.pau.imageparserapitest.Pojo.FileContent;
import com.pruebascongit.pau.imageparserapitest.Pojo.Line;
import com.pruebascongit.pau.imageparserapitest.Pojo.ParsedResults;
import com.pruebascongit.pau.imageparserapitest.Pojo.TextOverlay;
import com.pruebascongit.pau.imageparserapitest.Pojo.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class JsonResponseProcessor {

    private static final String BASE_URL = "https://api.ocr.space/parse/image";
    private static final String api_key = "81831039a288957";// d2c273817d88957


    public static FileContent getParsedFile(String img_url, String type,
                                            String language, boolean overlay) throws
            Exception {

        String json = "";

        JSONObject postDataParams = new JSONObject();

        if(type.equals("base64Image"))
            img_url = imageToBase64(img_url);

        postDataParams.put("apikey", api_key);//TODO Add your Registered API key
        postDataParams.put("isOverlayRequired", overlay);
        postDataParams.put(type, img_url);
        postDataParams.put("language", language);

        json = HpptUtils.get(BASE_URL, postDataParams);

        //typeDisplayer(json);

        return parseJsonString(json, type);
    }

    private static String imageToBase64(String path) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap takenImage = BitmapFactory.decodeFile(path);
        takenImage.compress(Bitmap.CompressFormat.PNG, 20, baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private static FileContent parseJsonString (String json, String type){

        FileContent content = new FileContent();
        ParsedResults parsedResults = new ParsedResults();
        TextOverlay textOverlay = new TextOverlay();
        String page = "";

        try {

            JSONObject data = new JSONObject(json);

            content.setErrorDetails(data.getString("ErrorDetails"));
            content.setErroredOnProcessing(data.getBoolean("IsErroredOnProcessing"));
            content.setErrorMessage(data.getString("ErrorMessage"));
            content.setOcrExitCode(data.getInt("OCRExitCode"));
            content.setProcesingTimeInMilliseconds(data.getString("ProcessingTimeInMilliseconds"));

            JSONArray arrParsedRes = null;

            if(type.equals("base64Image")){
                System.out.println("\tParsedResults -> "+((Object)data.get("ParsedResults")).getClass().getName());
//                System.out.println(((JSONObject)data.get("ParsedResults")).get("ParsedText"));
                return chapuza(content, data);

            }else
                arrParsedRes = data.getJSONArray("ParsedResults");


            for(int i = 0; i < arrParsedRes.length(); i++){

                JSONObject objectResult = arrParsedRes.getJSONObject(i);

                parsedResults.setFileParserExitCode(objectResult.getInt("FileParseExitCode"));

                switch (parsedResults.getFileParserExitCode()){

                    case 1:
                        //Success
                        page += objectResult.getString("ParsedText");
                        break;
                    case 0:
                        //File not found
                    case -10:
                        //OCR Engine Parse Error
                    case -20:
                        //Timeout
                    case -30:
                        //Validation Error
                    case -99:
                        //Unknow Error
                    default:
                        parsedResults.setErrorMessage(objectResult.getString("ErrorMessage"));
                        parsedResults.setErrorDetails("ErrorDetails");
                        content.setParsedResults(parsedResults);
                        return content;

                }

                JSONObject overlay = objectResult.getJSONObject("TextOverlay");

                textOverlay.setHasOverlay(overlay.getBoolean("HasOverlay"));

                if(!textOverlay.hasOverlay() && (i == (arrParsedRes.length()-1))){
                    parsedResults.setParsedText(page);
                    content.setParsedResults(parsedResults);
                    return content;
                }else if(!textOverlay.hasOverlay())
                    continue;


                textOverlay.setMessage(overlay.getString("Message"));

                JSONArray arrLines = overlay.getJSONArray("Lines");

                ArrayList<Line> lines = new ArrayList<>();
                Line line;
                Word word;

                for(int j = 0; j < arrLines.length(); j++){

                    JSONObject jObjLine = arrLines.getJSONObject(j);

                    line = new Line();

                    line.setMaxHeight(jObjLine.getDouble("MaxHeight"));
                    line.setMinTop(jObjLine.getDouble("MinTop"));

                    JSONArray arrWords = jObjLine.getJSONArray("Words");

                    ArrayList<Word> words = new ArrayList<>();

                    for(int x = 0; x < arrWords.length(); x++){

                        JSONObject jObjWord = arrWords.getJSONObject(x);

                        word = new Word(jObjWord.getString("WordText"),
                                jObjWord.getDouble("Left"),
                                jObjWord.getDouble("Top"),
                                jObjWord.getDouble("Height"),
                                jObjWord.getDouble("Width"));

                        words.add(word);
                    }

                    line.setWords(words);

                    lines.add(line);
                }

                textOverlay.setLines(lines);

            }

            parsedResults.setParsedText(page);
            parsedResults.setTextOverlay(textOverlay);
            content.setParsedResults(parsedResults);

            return content;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static FileContent chapuza(FileContent content, JSONObject data) {

        ParsedResults parsedResults = new ParsedResults();
        String page = "";
        TextOverlay textOverlay = new TextOverlay();
        try {

                JSONObject objectResult = data.getJSONObject("ParsedResults");

                parsedResults.setFileParserExitCode(objectResult.getInt("FileParseExitCode"));

                switch (parsedResults.getFileParserExitCode()){

                    case 1:
                        //Success
                        page += objectResult.getString("ParsedText");
                        break;
                    case 0:
                        //File not found
                    case -10:
                        //OCR Engine Parse Error
                    case -20:
                        //Timeout
                    case -30:
                        //Validation Error
                    case -99:
                        //Unknow Error
                    default:
                        parsedResults.setErrorMessage(objectResult.getString("ErrorMessage"));
                        parsedResults.setErrorDetails("ErrorDetails");
                        content.setParsedResults(parsedResults);
                        return content;

                }

                JSONObject overlay = objectResult.getJSONObject("TextOverlay");

                textOverlay.setHasOverlay(overlay.getBoolean("HasOverlay"));

                if(!textOverlay.hasOverlay()) {
                    parsedResults.setParsedText(page);
                    content.setParsedResults(parsedResults);
                    return content;
                }



                textOverlay.setMessage(overlay.getString("Message"));

                JSONArray arrLines = overlay.getJSONArray("Lines");

                ArrayList<Line> lines = new ArrayList<>();
                Line line;
                Word word;

                for(int j = 0; j < arrLines.length(); j++){

                    JSONObject jObjLine = arrLines.getJSONObject(j);

                    line = new Line();

                    line.setMaxHeight(jObjLine.getDouble("MaxHeight"));
                    line.setMinTop(jObjLine.getDouble("MinTop"));

                    JSONArray arrWords = jObjLine.getJSONArray("Words");

                    ArrayList<Word> words = new ArrayList<>();

                    for(int x = 0; x < arrWords.length(); x++){

                        JSONObject jObjWord = arrWords.getJSONObject(x);

                        word = new Word(jObjWord.getString("WordText"),
                                jObjWord.getDouble("Left"),
                                jObjWord.getDouble("Top"),
                                jObjWord.getDouble("Height"),
                                jObjWord.getDouble("Width"));

                        words.add(word);
                    }

                    line.setWords(words);

                    lines.add(line);
                }

                textOverlay.setLines(lines);

            parsedResults.setParsedText(page);
            parsedResults.setTextOverlay(textOverlay);
            content.setParsedResults(parsedResults);

            return content;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return content;
    }

    private static void typeDisplayer(String json){

        System.out.println("TyPE DISPLAYER");
        System.out.println("----------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------");
        try {
            JSONObject data = new JSONObject(json);

            System.out.println("\tErrorDetails -> "+((Object)((Object)data.get("ErrorDetails")).getClass().getName()).getClass().getName());

            System.out.println("\tIsErroredOnProcessing -> "+((Object)data.get("IsErroredOnProcessing")).getClass().getName());

            System.out.println("\tErrorMessage -> "+((Object)((Object)data.get("ErrorMessage")).getClass().getName()).getClass().getName());
            //System.out.println("\tErrorMessage -> "+((Object)data.get("ErrorMessaje")).getClass().getName());
            System.out.println("\tOCRExitCode -> "+((Object)data.get("OCRExitCode")).getClass().getName());
            System.out.println("\tProcessingTimeInMilliseconds -> "+((Object)data.get("ProcessingTimeInMilliseconds")).getClass().getName());
            System.out.println("\tParsedResults -> "+((Object)data.get("ParsedResults")).getClass().getName()+" || Length = "+data.getJSONArray("ParsedResults").length());

            JSONArray arr = data.getJSONArray("ParsedResults");
            JSONObject object = arr.getJSONObject(0);

            System.out.println("*********************ParsedResults*************************");
            System.out.println();

            System.out.println("\tTextOverlay -> "+((Object)object.get("TextOverlay")).getClass().getName());
            System.out.println("\tFileParseExitCode -> "+((Object)object.get("FileParseExitCode")).getClass().getName());
            System.out.println("\tParsedText -> "+((Object)object.get("ParsedText")).getClass().getName());
            System.out.println("\tErrorMessage -> "+((Object)object.get("ErrorMessage")).getClass().getName());
            System.out.println("\tErrorDetails -> "+((Object)object.get("ErrorDetails")).getClass().getName());

            JSONObject object2 = object.getJSONObject("TextOverlay");

            System.out.println("*********************TextOverlay*************************");
            System.out.println();

            System.out.println("\tLines -> "+((Object)object2.get("Lines")).getClass().getName()+" || Length = "+object2.getJSONArray("Lines").length());
            System.out.println("\tHasOverlay -> "+((Object)object2.get("HasOverlay")).getClass().getName());
            System.out.println("\tMessage -> "+((Object)object2.get("Message")).getClass().getName());


            JSONArray arr3 = object2.getJSONArray("Lines");
            JSONObject object3 = arr3.getJSONObject(0);


            System.out.println("*********************Lines*************************");
            System.out.println();

            System.out.println("\tWords -> "+((Object)object3.get("Words")).getClass().getName()+" || Length = "+object3.getJSONArray("Words").length());
            System.out.println("\tMaxHeight -> "+((Object)object3.get("MaxHeight")).getClass().getName());
            System.out.println("\tMinTop -> "+((Object)object3.get("MinTop")).getClass().getName());

            JSONArray arr4 = object3.getJSONArray("Words");
            JSONObject object4 = arr4.getJSONObject(0);


            System.out.println("*********************Words*************************");
            System.out.println();

            System.out.println("\tWordText -> "+((Object)object4.get("WordText")).getClass().getName());
            System.out.println("\tLeft -> "+((Object)object4.get("Left")).getClass().getName());
            System.out.println("\tTop -> "+((Object)object4.get("Top")).getClass().getName());
            System.out.println("\tHeight -> "+((Object)object4.get("Height")).getClass().getName());
            System.out.println("\tWidth -> "+((Object)object4.get("Width")).getClass().getName());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------");
        System.out.println();
    }

}
