package com.difusal.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Droid {
    private Bitmap bitmap;
    private Point coordinates;
    private boolean touched;

    public Droid(Bitmap bitmap, Point coordinates) {
        this.bitmap = bitmap;
        this.coordinates = new Point(coordinates);
    }

    public void setX(int x) {
        this.coordinates.x = x;
    }

    public void setY(int y) {
        this.coordinates.y = y;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    /**
     * Method which updates the droid internal state every tick
     */
    public void update() {
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, coordinates.x - (bitmap.getWidth() / 2), coordinates.y - (bitmap.getHeight() / 2), null);
    }

    /**
     * Handles the MotionEvent.ACTION_DOWN event.
     * If the event happens on the bitmap surface then the touched state
     * is set to <code>true</code> otherwise to <code>false</code>
     */
    public void handleActionDown(int eventX, int eventY) {
        if ((eventX >= (coordinates.x - bitmap.getWidth() / 2)) &&
                (eventX <= (coordinates.x + bitmap.getWidth() / 2)) &&
                (eventY >= (coordinates.y - bitmap.getHeight() / 2)) &&
                (coordinates.y <= (coordinates.y + bitmap.getHeight() / 2)))
            setTouched(true);
        else
            setTouched(false);
    }
}
