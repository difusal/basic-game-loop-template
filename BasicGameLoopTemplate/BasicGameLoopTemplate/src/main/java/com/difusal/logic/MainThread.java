package com.difusal.logic;

import android.util.Log;
import android.view.SurfaceHolder;

/**
 * The Main thread which contains the game loop. The thread must have access to
 * the surface view and holder to trigger events every game tick.
 */
public class MainThread extends Thread {
    private static final String TAG = MainThread.class.getSimpleName();

    // Surface holder that can access the physical surface
    private SurfaceHolder surfaceHolder;

    // The actual view that handles inputs and draws to the surface
    private GamePanel gamePanel;

    // Flag to hold game state
    private boolean running;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long tickCount = 0L;

        Log.d(TAG, "Starting game loop");
        while (running) {
            tickCount++;

            // update game state

            // render state to the screen
        }

        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }
}
