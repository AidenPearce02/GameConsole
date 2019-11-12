package com.example.gameconsole;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

public class App extends Application {
    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    /*private ThreadConnected threadConnected;
    public ThreadConnected getThreadConnected() {
        return threadConnected;
    }
    public void setThreadConnected(ThreadConnected threadConnected) {
        this.threadConnected = threadConnected;
    }*/
    public Thread getThreadByName(String threadName) {
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals(threadName)) return t;
        }
        return null;
    }


    public void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
