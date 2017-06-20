package org.vbm.handlertimer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements reportProgress {

    Handler handler;
    Boolean isRunning, isBackRunnung;
    int seconds = 0;
    BackgroundTimer timer;
    int backseconds;
    TextView tv, tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRunning = false;
        tv = (TextView) findViewById(R.id.textView);
        tv1 = (TextView) findViewById(R.id.textView2);
        handler = new Handler();
        backseconds = 0;
        isBackRunnung = false;
        runTimer();
        timer = (BackgroundTimer) getLastCustomNonConfigurationInstance();
        if( timer != null ) {
            timer.link(this);
            isBackRunnung = true;
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        timer.unlink();
        if( isBackRunnung )
           return timer;
        else return null;

    }

    public void onClick(View view) {
        isRunning = !isRunning;
    }

    void runTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    tv.setText(String.valueOf(seconds));
                    ++seconds;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void startBackground(View view) {
        if (timer == null || !isBackRunnung) {
            timer = (BackgroundTimer) getLastNonConfigurationInstance();
            if( timer == null )
               timer = new BackgroundTimer(backseconds, this);
            else timer.link(this);
            timer.execute();
            isBackRunnung = true;
        } else {
            backseconds = timer.onStop();
            isBackRunnung = false;
        }
    }

    @Override
    public void show(int seconds) {
        tv1.setText(String.valueOf(seconds));
    }
}
