package com.galadar.graphicstest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Galadar on 19/12/2016.
 */
public class Sprite8 {
    public enum Direction {Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight}
    int x, y, Xspeed, Yspeed, height, width;
    Direction dir;
    int stage;
    Bitmap sheet;
    MainActivity.GraphicsView gv;

    public Sprite8(MainActivity.GraphicsView myView, Bitmap mip){
        sheet = mip;
        gv = myView;
        x = y = Yspeed = 0;
        Xspeed = 5;
        stage = 0;
        dir = Direction.Right;
        height = sheet.getHeight()/8;
        width = sheet.getWidth()/8;
    }

    private int getIndexRow(){
        switch (dir) {
            case Down:
                return 0;
            case DownRight:
                return 1;
            case Right:
                return 2;
            case UpRight:
                return 3;
            case Up:
                return 4;
            case UpLeft:
                return 5;
            case Left:
                return 6;
            case DownLeft:
                return 7;

        }
        return 0;
    }

    public void onDraw(Canvas canvas){
        update();
        int startY = getIndexRow()*height;
        stage = (stage+1)%4;
        int startX=stage*width;
        Rect src = new Rect(startX,startY,startX+width,startY+height);
        //TODO: Fix the printed size
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
