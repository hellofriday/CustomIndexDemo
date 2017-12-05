package com.futureproteam.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends Activity{

    private IndexBar indexBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        textView = (TextView) findViewById(R.id.tev);
        indexBar = (IndexBar) findViewById(R.id.indexBar);
        indexBar.setOnIndexClickedListener(new IndexBar.OnIndexClickedListener() {
            @Override
            public void onClicked(int position, String c) {
                textView.setText(c);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
