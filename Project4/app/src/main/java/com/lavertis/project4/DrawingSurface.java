package com.lavertis.project4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final int FRAME_TIME = (int) (1000.0 / 60.0); // 60 FPS
    private final String LOG_TAG = "DrawingSurface";
    private final SurfaceHolder holder;
    private final Object lock = new Object();
    private final Path path = new Path();
    private final Paint pathPaint = new Paint();
    private final Paint dotPaint = new Paint();
    private final int paintColor = Color.MAGENTA;
    private Thread thread;
    private boolean running;
    private Bitmap bitmap;
    private Canvas canvas;

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);

        pathPaint.setAntiAlias(true);
        pathPaint.setColor(paintColor);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
        pathPaint.setStrokeWidth(12);

        dotPaint.setAntiAlias(true);
        dotPaint.setColor(paintColor);
        dotPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d(LOG_TAG, "ACTION_DOWN");
                synchronized (lock) {
                    canvas.drawCircle(x, y, 20, dotPaint);
                }
                path.moveTo(x, y);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d(LOG_TAG, "ACTION_MOVE: " + event.getX() + ", " + event.getY());
                path.lineTo(x, y);
                synchronized (lock) {
                    canvas.drawPath(path, pathPaint);
                }
                return true;
            }
            case MotionEvent.ACTION_UP: {
                Log.d(LOG_TAG, "ACTION_UP");
                synchronized (lock) {
                    canvas.drawCircle(x, y, 20, dotPaint);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        startDrawingThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawingThread();
    }

    public void startDrawingThread() {
        running = true;
        thread = new Thread(this, "DrawSurface");
        thread.start();
    }

    public void stopDrawingThread() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            Canvas localCanvas = null;
            try {
                synchronized (holder) {
                    if (!holder.getSurface().isValid()) continue;
                    localCanvas = holder.lockCanvas(null);
                    synchronized (lock) {
                        if (running) {
                            localCanvas.drawBitmap(bitmap, 0, 0, null);
                        }
                    }
                }
            } catch (Exception e) {
                Log.w(LOG_TAG, "Exception while locking/unlocking");
            } finally {
                if (localCanvas != null) {
                    holder.unlockCanvasAndPost(localCanvas);
                }
            }
            try {
                Thread.sleep(FRAME_TIME);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
// https://riptutorial.com/android/example/13004/surfaceview-with-drawing-thread