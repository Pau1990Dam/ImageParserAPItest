package com.pruebascongit.pau.imageparserapitest.HttpUtils;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;


public class HpptUtils {

    public static String get(String base_url, JSONObject post_params) throws Exception {

        URL url = new URL(base_url);
        StringBuffer response = null;
        System.out.println(base_url);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        urlConnection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
        wr.writeBytes(getPostDataString(post_params));
        wr.flush();
        wr.close();

        System.out.println(urlConnection.toString());

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return String.valueOf(response);
    }
    private static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }

        return result.toString();
    }
}
