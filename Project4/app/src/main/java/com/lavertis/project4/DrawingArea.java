package com.lavertis.project4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingArea extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final int MAX_FRAME_TIME = (int) (1000.0 / 60.0); // 60 FPS
    private static final String LOG_TAG = "surface";
    private SurfaceHolder holder;
    private Thread drawThread;
    private boolean surfaceReady = false;
    private boolean drawingActive = false;
    private Bitmap bitmap;
    private Canvas canvas;

    public DrawingArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d("DrawingArea", "ACTION_DOWN");
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d("DrawingArea", "ACTION_MOVE: " + event.getX() + ", " + event.getY());
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(10);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(event.getX(), event.getY(), 50, paint);
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.d("DrawingArea", "ACTION_UP");
                break;
            }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void run() {
        Log.d(LOG_TAG, "Draw thread started");
        long frameStartTime;
        long frameTime;

        try {
            while (drawingActive) {
                if (!holder.getSurface().isValid()) continue;

                frameStartTime = System.nanoTime();
                Canvas localCanvas = holder.lockCanvas();
                if (localCanvas != null) {
                    try {
                        localCanvas.drawBitmap(bitmap, 0, 0, null);
                    } finally {
                        holder.unlockCanvasAndPost(localCanvas);
                    }
                }

                // calculate the time required to draw the frame in ms
                frameTime = (System.nanoTime() - frameStartTime) / 1000000;
                if (frameTime < MAX_FRAME_TIME) // faster than the max fps - limit the FPS
                {
                    try {
                        //noinspection BusyWait
                        Thread.sleep(MAX_FRAME_TIME - frameTime);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, "Exception while locking/unlocking");
        }
        Log.d(LOG_TAG, "Draw thread finished");
    }

    public void stopDrawThread() {
        if (drawThread == null) {
            Log.d(LOG_TAG, "DrawThread is null");
            return;
        }
        drawingActive = false;
        while (true) {
            try {
                Log.d(LOG_TAG, "Request last frame");
                drawThread.join(5000);
                break;
            } catch (Exception e) {
                Log.e(LOG_TAG, "Could not join with draw thread");
            }
        }
        drawThread = null;
    }

    public void startDrawThread() {
        if (surfaceReady && drawThread == null) {
            drawThread = new Thread(this, "Draw thread");
            drawingActive = true;
            drawThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;

        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        if (drawThread != null) {
            Log.d(LOG_TAG, "draw thread still active..");
            drawingActive = false;
            try {
                drawThread.join();
            } catch (InterruptedException ignored) {
            }
        }

        surfaceReady = true;
        startDrawThread();
        Log.d(LOG_TAG, "Created");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface is not used anymore - stop the drawing thread
        stopDrawThread();
        // and release the surface
        holder.getSurface().release();

        this.holder = null;
        surfaceReady = false;
        Log.d(LOG_TAG, "Destroyed");
    }
}
// https://riptutorial.com/android/example/13004/surfaceview-with-drawing-thread