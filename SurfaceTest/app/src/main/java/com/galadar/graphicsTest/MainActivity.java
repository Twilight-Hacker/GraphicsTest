package com.galadar.graphicstest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    final Handler h = new Handler();
    GraphicsView myView;
    Bitmap sheet;
    float x,y;
    Sprite mySprite;

    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 150;
    private static final int ACCEL_CLICK_MIN_DURATION = 600;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);

        sheet = BitmapFactory.decodeResource(getResources(), R.drawable.mysheet);
        x=y=0;

        myView = new GraphicsView(this);

        gestureDetector = new GestureDetector(this, new GestDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getDownTime() >= ACCEL_CLICK_MIN_DURATION) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        myView.decel();
                        //return true;
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        myView.accel();
                        //return true;
                    }
                }
                return gestureDetector.onTouchEvent(event);
            }
        };

        setContentView(myView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        myView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myView.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    public class GraphicsView extends SurfaceView implements Runnable{

        SurfaceHolder holder;
        Thread t = null;
        boolean running = false;
        boolean loaded = false;

        public GraphicsView(Context context){
            super(context);
            holder = getHolder();
            t = new Thread(this);
            t.start();
        }

        @SuppressLint("WrongCall")
        public void run(){
            while(true){
                if(holder.getSurface().isValid()){
                    break;
                }
            }
            if(!loaded){
                mySprite = new Sprite(GraphicsView.this, sheet);
                loaded = true;
            }
            Canvas c = holder.lockCanvas();
            onDraw(c);
            holder.unlockCanvasAndPost(c);
            if(running){
                h.postDelayed(this, 60);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawARGB(255, 255, 255, 255);
            mySprite.onDraw(canvas);
        }

        public void flingleft(){
            mySprite.LeftMove();
        }

        public void flingright(){
            mySprite.RightMove();
        }

        public void accel(){
            mySprite.running();
        }

        public void decel(){
            mySprite.walking();
        }

        public void pause(){
            running = false;
            while(true) {
                try {
                    t.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        public void resume(){
            running = true;
            t= new Thread(this);
            t.start();
        }
    }

    private class GestDetector implements GestureDetector.OnGestureListener {

        @Override
        public void onShowPress(MotionEvent p1)
        {
            // TODO: Implement this method
        }



        @Override
        public boolean onSingleTapUp(MotionEvent p1)
        {
            // TODO: Implement this method
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent p1, MotionEvent p2, float p3, float p4)
        {
            // TODO: Implement this method
            return false;
        }

        @Override
        public void onLongPress(MotionEvent p1)
        {
            // TODO: Implement this method
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    myView.flingleft();
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    myView.flingright();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e){
            // TODO: Implement this method
            return true;
        }
    }
}
