package com.example.fractals;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String TAG = "123";

    Button buttonStart, buttonRun;
    CustomСanvas customСanvas;
    public static Handler tickHandler;
    long DELAY_MILLIS = 1000;
    public long delayMillis = DELAY_MILLIS;
    public static boolean started, isRunning;
    public static Point startedPoint;
    SeekBar seekBar;
    TextView tvSeekBarMonitor;
    public static int maxNum;

    public Runnable mTickRunnable = new Runnable() {
        @Override
        public void run() {

            if (customСanvas.mainPoints.size() < 1){
                if (started) {
                    MainActivity.tickHandler.postDelayed(mTickRunnable, delayMillis);
                }
                return;
            }

            Canvas bitmapHolder = new Canvas(customСanvas.bitmap);
            bitmapHolder.drawBitmap(customСanvas.bitmap, 0, 0, customСanvas.p);
            Point point = customСanvas.mainPoints.get(customСanvas.mainPoints.size() - 1);
            bitmapHolder.drawPoint(point.x, point.y, customСanvas.p);

            customСanvas.invalidate();

            if (started) {
                MainActivity.tickHandler.postDelayed(mTickRunnable, delayMillis);
            }

        }
    };

    public Runnable mTicRunnable = new Runnable() {
        @Override
        public void run() {

            if (customСanvas.mainPoints.size() < 1 || startedPoint == null){
                if (isRunning) {
                    MainActivity.tickHandler.postDelayed(mTicRunnable, delayMillis);
                }
                return;
            }

            Random random = new Random();
            int num = 1 + random.nextInt(maxNum);

            Point targetPoint = startedPoint;
            for(Point point : customСanvas.mainPoints)
            {
                if (point.value1 == num || point.value2 == num){
                    targetPoint = point;
                }
            }

            double length = Math.rint(100.0 * determineLength(targetPoint, startedPoint)) / 100.0;


            float x;
            float y;

            if (startedPoint.x > targetPoint.x){
                x = (float)(startedPoint.x - (startedPoint.x - targetPoint.x)/2);
            }else{
                x = (float)(targetPoint.x - (targetPoint.x - startedPoint.x)/2);
            }
            if (startedPoint.y > targetPoint.y){
                y = (float)(startedPoint.y - (startedPoint.y - targetPoint.y)/2);
            }else{
                y = (float)(targetPoint.y - (targetPoint.y - startedPoint.y)/2);
            }
            Log.d(TAG, "run: x"+x+"y"+y);


            Point point = new Point(x , y);

            Canvas bitmapHolder = new Canvas(customСanvas.bitmap);
            bitmapHolder.drawBitmap(customСanvas.bitmap, 0, 0, customСanvas.p);

            bitmapHolder.drawPoint(point.x, point.y, customСanvas.p);

            startedPoint = point;
            customСanvas.invalidate();

            if (isRunning) {
                MainActivity.tickHandler.postDelayed(mTicRunnable, delayMillis);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonRun = findViewById(R.id.buttonRun);
        customСanvas = findViewById(R.id.customСanvas);
        seekBar = findViewById(R.id.seekBar);
        tvSeekBarMonitor = findViewById(R.id.seekBarMonitor);

        String str = "Delay millis = "+delayMillis;
        tvSeekBarMonitor.setText(str);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress > 0) {
                    delayMillis = (long) (DELAY_MILLIS * ((double)progress / 100));
                    String str = "Delay millis = "+delayMillis;
                    tvSeekBarMonitor.setText(str);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tickHandler = new Handler();

        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isRunning = !isRunning;

                if (isRunning){
                    buttonRun.setText("Stop");

                    started = false;
                    buttonStart.setText("Start");
                }else {
                    buttonRun.setText("Run");
                }

                if (isRunning){

                    tickHandler.postDelayed(mTicRunnable, delayMillis);
                }

            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                started = !started;

                if (started){
                    buttonStart.setText("Finish");

                    isRunning = false;
                    buttonRun.setText("Run");
                }else {
                    buttonStart.setText("Start");
                    startedPoint = null;
                }

                if (started){
                    customСanvas.mainPoints.clear();
                    customСanvas.bitmap = Bitmap.createBitmap(customСanvas.getMeasuredWidth(), customСanvas.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

                    tickHandler.postDelayed(mTickRunnable, delayMillis);
                }

            }
        });

    }

    private static double determineLength(Point point1, Point point2) {

        double length = 0;
        length = Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
        return length;
    }


}
