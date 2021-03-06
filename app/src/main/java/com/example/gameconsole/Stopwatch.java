package com.example.gameconsole;

class Stopwatch {
    private long startTime = 0;
    private boolean running = false;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    boolean isRunning(){
        return running;
    }

    void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    void pause() {
        this.running = false;
    }

    long getElapsedTimeSecs() {
        long elapsed = 0;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000) % 60;
        }
        return elapsed;
    }
}
