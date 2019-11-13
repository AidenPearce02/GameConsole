package com.example.gameconsole;

import android.app.Activity;
import android.app.Application;

public class App extends Application {
    private Activity mCurrentActivity = null;
    private String speedNow="";
    private String mazeNow="";
    private Stopwatch stopwatch=null;

    public Stopwatch getStopwatch() {
        return stopwatch;
    }

    public void setStopwatch(Stopwatch stopwatch) {
        this.stopwatch = stopwatch;
    }

    public String getSpeedNow() {
        return speedNow;
    }

    public void setSpeedNow(String speedNow) {
        this.speedNow = speedNow;
    }

    public String getMazeNow() {
        return mazeNow;
    }

    public void setMazeNow(String mazeNow) {
        this.mazeNow = mazeNow;
    }

    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    public Thread getThread(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) return t;
        }
        return null;
    }
}
