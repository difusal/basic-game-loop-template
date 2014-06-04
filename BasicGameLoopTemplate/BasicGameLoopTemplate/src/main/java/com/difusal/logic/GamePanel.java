package com.difusal.logic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.difusal.basicgamelooptemplate.R;

/**
 * This is the main surface that handles the touch events and draws the image to the screen.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = GamePanel.class.getSimpleName();

    private MainThread thread;
    private Droid droid;

    public GamePanel(Context context) {
        super(context);

        // add the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // load droid bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);

        // create droid
        droid = new Droid(bitmap, new Point(0, 0));

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    /**
     * Game update method.
     */
    public void update() {
        // Update the droid
        droid.update();
    }

    /**
     * Game draw method.
     */
    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        droid.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //if it is the first time the thread starts
        if (thread.getState() == Thread.State.TERMINATED)
            thread = new MainThread(getHolder(), this);

        MainThread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "Surface is being destroyed");

        // tell the thread to shut down and wait for it to finish. this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the droid
            droid.handleActionDown((int) event.getX(), (int) event.getY());

            // check if in the lower part of the screen we exit
            if (event.getY() > getHeight() - 50) {
                MainThread.setRunning(false);
                ((Activity) getContext()).finish();
            } else {
                Log.d(TAG, "Coordinates: x=" + event.getX() + ",y=" + event.getY());
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
            if (droid.isTouched()) {
                // the droid was picked up and is being dragged
                droid.setX((int) event.getX());
                droid.setY((int) event.getY());
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (droid.isTouched()) {
                droid.setTouched(false);
            }
        }
        return true;
    }
}
