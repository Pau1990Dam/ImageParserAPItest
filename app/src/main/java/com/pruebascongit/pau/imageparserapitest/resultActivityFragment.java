package com.pruebascongit.pau.imageparserapitest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.events.Events;
import com.bumptech.glide.Glide;
import com.pruebascongit.pau.imageparserapitest.Pojo.FileContent;
import com.pruebascongit.pau.imageparserapitest.services.DownloadService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class resultActivityFragment extends Fragment {

    private View view;
    private TextView result, errorDisplayer, headerResult;
    private ImageView imagePreview;
    private String url, lnaguage, type;
    private boolean overlay;

    @Override
    public void onStart() {
        super.onStart();
        Events.register(this);
    }

    public resultActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_result, container, false);

        result = (TextView) view.findViewById(R.id.result);
        errorDisplayer = (TextView) view.findViewById(R.id.errorDisplayer);
        headerResult = (TextView) view.findViewById(R.id.headerResult);
        imagePreview = (ImageView) view.findViewById(R.id.imagePreview);

        Intent dataCaught = getActivity().getIntent();
        String guarrrader;

        if (dataCaught != null) {

            guarrrader = dataCaught.getStringExtra("caller");

            if (guarrrader.equals("mainActivity")) {
                /*
                byte[] bytes = dataCaught.getByteArrayExtra("preview");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagePreview.setImageBitmap(bitmap);*/
                type = "base64Image";
                String path = dataCaught.getStringExtra("preview");
                loadWithGlide(getContext(), path);

                //Toast.makeText(getContext(),""+encodev2(path), Toast.LENGTH_LONG).show();
                //Log.d("encode base64 ", encodev2(path));
                //url = imageToBase64(path);
                url = path;
                startDownload();

                /*
                Bitmap takenImage = BitmapFactory.decodeFile(path);
                byte[] compssedPhoto = compress(takenImage);
                Bitmap bitmap = BitmapFactory.decodeByteArray(compssedPhoto, 0, compssedPhoto.length);
                System.out.println("Bitmap is null? ->" + sizeOf(bitmap)+" and "+sizeOf(takenImage));
                Toast.makeText(getContext(), "imageSize -> "+sizeOf(bitmap)+ " || imageSize2 -> "+sizeOf(takenImage), Toast.LENGTH_LONG).show();*/

                /*Bitmap theBitmap = Glide.
                        with(this).
                        load(path).
                        asBitmap().
                        into(100, 100). // Width and height
                        get();*/

                /*
                Bitmap takenImage = BitmapFactory.decodeFile(path);
                byte [] compssedPhoto = compress(takenImage);
                Bitmap bitmap = BitmapFactory.decodeByteArray(compssedPhoto, 0, compssedPhoto.length);
                imagePreview.setImageBitmap(bitmap);*/

            } else if (guarrrader.equals("resultFragment")) {

                String sampleData = dataCaught.getStringExtra("img");

                if (!sampleData.isEmpty()) {
                    type = "url";
                    headerResult.setText(dataCaught.getDataString());
                    url = sampleData;
                    overlay = true;
                    result.setText(sampleData);
                    startDownload();

                } else
                    result.setText("algo salio mal!!1");
            }


        } else
            result.setText("El intent no a podido ser pillado!");

        return view;
    }

    private void startDownload() {

        Intent i = new Intent(getContext(), DownloadService.class);
        //i.putExtra("base64Image", url);
        i.putExtra("type",type);
        i.putExtra("url", url);
        i.putExtra("language", "eng");
        i.putExtra("overlay", overlay);
        getContext().startService(i);
    }

    @Events.Subscribe("job-done")
    void DownloadedDataResul(FileContent fileContent) {
        if (!fileContent.isErroredOnProcessing()) {

            String header = "Tiempo de procesamiento: " + fileContent.getProcesingTimeInMilliseconds()
                    + "ms\nResultado: " + fileContent.getOcrExitCode();
            headerResult.setText(header);
            result.setText(fileContent.getParsedResults().getParsedText());
            errorDisplayer.setText(fileContent.getErrorMessage());
        } else {
            result.setText(fileContent.getErrorMessage());
            headerResult.setText("algo fallo");
            errorDisplayer.setText(fileContent.getOcrExitCode() + "\n" + fileContent.getErrorMessage());
        }
    }

    private void loadWithGlide(Context context, String path) {
        Glide.with(context)
                .load(path)
                .into(imagePreview);
    }

    private String imageToBase64(String path) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap takenImage = BitmapFactory.decodeFile(path);
        takenImage.compress(Bitmap.CompressFormat.PNG, 20, baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    protected int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return (data.getRowBytes() * data.getHeight())/1024;
        } else {
            return data.getByteCount()/1024;
        }
    }

    private byte[] compress(Bitmap photo) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 20, stream);
        InputStream in = new ByteArrayInputStream(stream.toByteArray());

        return stream.toByteArray();


    }

    private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }

    private String encodev2(String path){
        byte[] bytes;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            InputStream inputStream = new FileInputStream(path);//You can get an inputStream using any IO API

            byte[] buffer = new byte[8192];
            int bytesRead;


                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        System.out.println("How large it is? "+bytes.length);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
/*
 ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);


    }
 */