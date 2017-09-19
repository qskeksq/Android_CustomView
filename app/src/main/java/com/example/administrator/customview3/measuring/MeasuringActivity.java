package com.example.administrator.customview3.measuring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.customview3.R;

public class MeasuringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measuring);

        final Measuring meas = (Measuring) findViewById(R.id.meas);
        final TextView textView = (TextView) findViewById(R.id.text);

        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(meas.mResult);
            }
        }, 100);
    }
}
