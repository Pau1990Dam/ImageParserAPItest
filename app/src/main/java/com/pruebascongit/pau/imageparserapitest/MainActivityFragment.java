package com.pruebascongit.pau.imageparserapitest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Button sencillo, complejo;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);

        sencillo = (Button) view.findViewById(R.id.img1);
        complejo = (Button) view.findViewById(R.id.img2);

        sencillo.setOnClickListener(this);
        complejo.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getContext(),resultActivity.class);
        intent.putExtra("caller","resultFragment");

        switch (view.getId()){

            case R.id.img1:
                intent.putExtra("img","http://dl.a9t9.com/blog/ocr-online/screenshot.jpg");//https://help.clover.com/wp-content/uploads/online-receipt-location.jpg
                break;
            case R.id.img2:
                intent.putExtra("img","https://purchasing.byu.edu/sites/default/files/receipt1.png");
                break;
        }

        startActivity(intent);
    }
}
