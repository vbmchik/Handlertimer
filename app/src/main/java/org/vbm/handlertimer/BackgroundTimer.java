package org.vbm.handlertimer;

import android.os.AsyncTask;

/**
 * Created by vbm on 20.06.17.
 */

public class BackgroundTimer extends AsyncTask {

    int n;
    boolean onStop;
    reportProgress reporter;
    boolean needProgress;

    public BackgroundTimer(int milliseconds, reportProgress reporter) {
        n = milliseconds ;
        onStop = true;
        this.reporter = reporter;
        needProgress = true;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        do{
            publishProgress();
            try {
                Thread.sleep(1000);
                ++n;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(onStop);
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        if( needProgress)
           reporter.show(n);
    }

    public synchronized int onStop(){
        onStop = false;
        return n;
    }

    public synchronized void unlink(){
        reporter = null;
        needProgress = false;
    }

    public synchronized void link(reportProgress reporter){
        this.reporter = reporter ;
        needProgress = true;
    }
}
