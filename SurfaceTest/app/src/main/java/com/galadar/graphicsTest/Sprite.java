package com.galadar.graphicstest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.galadar.graphicstest.MainActivity.GraphicsView;

/**
 * Created by Galadar on 19/12/2016.
 *
 * 4 line character animation sprite sheet.
 */
public class Sprite {

    public enum Direction {Up, Down, Left, Right}
    int x, y, Xspeed, Yspeed, height, width;
    Direction dir;
    int stage;
    Bitmap sheet;
    GraphicsView gv;

    public Sprite(GraphicsView myView, Bitmap mip){
        sheet = mip;
        gv = myView;
        x = y = Yspeed = 0;
        Xspeed = 5;
        stage = 0;
        dir = Direction.Right;
        height = sheet.getHeight()/4;
        width = sheet.getWidth()/4;
    }

    private int getIndexRow(){
        switch (dir) {
            case Down:
                return 0;
            case Up:
                return 1;
            case Left:
                return 2;
            case Right:
                return 3;
        }
        return 0;
    }

    public void onDraw(Canvas canvas){
        update();
        int startY = getIndexRow()*height;
        stage = (stage+1)%4;
        int startX=stage*width;
        Rect src = new Rect(startX,startY,startX+width,startY+height);
        Rect dst = new Rect(x,y,x+width/10,y+height/10);
        canvas.drawBitmap(sheet, src, dst, null);

    }

    public Point getPivot(){
        int locx, locy;

        locy = y+height/10;
        locx = x+(width/10)/2; //div 10 div 2, because we want to go only halfway

        return new Point(locx,locy);
    }

    private void update(){
        if(x+Xspeed>=gv.getWidth()-width/10){
            Xspeed = 0;
            Yspeed = 5;
            dir=Direction.Down;
        } else if(y+Yspeed>=gv.getHeight()-height/10){
            Yspeed=0;
            Xspeed = -5;
            dir=Direction.Left;
        } else if(x+Xspeed<=0){
            Xspeed = 0;
            Yspeed = -5;
            dir=Direction.Up;
        } else if(y+Yspeed<=0){
            Yspeed = 0;
            Xspeed = 5;
            dir=Direction.Right;
        }

        x=x+Xspeed;
        y=y+Yspeed;
    }

    public void running(){
        Xspeed *=2;
        Yspeed *=2;
    }

    public void walking(){
        Xspeed /=2;
        Yspeed /=2;
    }

    public void RightMove(){
        Yspeed = 0;
        Xspeed = 5;
        dir=Direction.Right;
    }

    public void LeftMove(){
        Yspeed=0;
        Xspeed = -5;
        dir=Direction.Left;
    }

    public class Point{

        private int loc_x;
        private int loc_y;

        public Point(int loc_x, int loc_y) {
            this.loc_x = loc_x;
            this.loc_y = loc_y;
        }

        public Point(){

        }

        public int getx() {
            return loc_x;
        }

        public void setx(int loc_x) {
            this.loc_x = loc_x;
        }

        public int gety() {
            return loc_y;
        }

        public void sety(int loc_y) {
            this.loc_y = loc_y;
        }
    }
}
