package com.pruebascongit.pau.imageparserapitest.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.alexvasilkov.events.Events;
import com.pruebascongit.pau.imageparserapitest.ORC_API.JsonResponseProcessor;
import com.pruebascongit.pau.imageparserapitest.Pojo.FileContent;


public class DownloadService extends IntentService {

    public DownloadService(){ super("DownloadService"); }

    @Override
    protected void onHandleIntent(Intent intent) {

        String type;
        String url;
        String language;
        boolean bool;

        FileContent data;

        if(intent != null){

            type = intent.getStringExtra("type");
            url = intent.getStringExtra("url");
            language = intent.getStringExtra("language");
            bool = intent.getExtras().getBoolean("overlay");

            try {
                data = JsonResponseProcessor.getParsedFile(url,type,language,bool);
                Events.create("job-done").param(data).post();
            } catch (Exception e) {
                Log.d("FAIL","Exception faiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiil!!!");
                e.printStackTrace();
            }

        }else{
            Log.d("FAIL","else faiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiil!!!");
        }
    }
}
