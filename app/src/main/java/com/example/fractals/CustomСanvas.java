package com.example.fractals;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class CustomСanvas extends View implements View.OnTouchListener {

    String TAG = "123";

    public ArrayList<Point> mainPoints = new ArrayList<>();
    public Paint p;
    public Bitmap bitmap;


    public CustomСanvas(Context context) {
        super(context);

        init();
    }

    public CustomСanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CustomСanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomСanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    public void init(){

        setDrawingCacheEnabled(true);
        //buildDrawingCache();
        setOnTouchListener(this);


        p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mainPoints.size() < 1){
            return;
        }
        canvas.drawBitmap(bitmap, 0, 0, p);
        //
        //Bitmap bitmap = Bitmap.createBitmap(this.getMeasuredWidth(), this.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        /*
        if (points.size() < 1){
            return;
        }
        buildDrawingCache();

        canvas.drawBitmap(getDrawingCache(), 0, 0, p);
        Point point = points.get(points.size() - 1);
        canvas.drawPoint(point.x, point.y, p);

        */
        //destroyDrawingCache();
        //buildDrawingCache();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.d(TAG, "onTouchEvent: ");

        if (MainActivity.started) {

            Point newPoint = new Point(event.getX(), event.getY());

            Point lastPoint;
            if (mainPoints.size() < 1){
                lastPoint = newPoint;
            }else{
                lastPoint = mainPoints.get(mainPoints.size() - 1);
            }

            newPoint.value1 = lastPoint.value1;
            newPoint.value2 = lastPoint.value2;

            newPoint.value1 += 2;
            newPoint.value2 += 2;

            MainActivity.maxNum = newPoint.value2;
            mainPoints.add(newPoint);
        }

        if (MainActivity.isRunning) {

            MainActivity.startedPoint = new Point(event.getX(), event.getY());

            MainActivity.startedPoint.value1 = 0;
            MainActivity.startedPoint.value2 = 0;

            Canvas bitmapHolder = new Canvas(bitmap);
            bitmapHolder.drawBitmap(bitmap, 0, 0, p);

            p.setColor(Color.RED);
            bitmapHolder.drawPoint(MainActivity.startedPoint.x, MainActivity.startedPoint.y, p);
            p.setColor(Color.BLACK);

        }
        return false;
    }




}
