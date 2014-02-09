package com.quchen.flappycow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class Sprite {
	public static final short SPEED_DEFAULT = 5;
	public static final short ANIMATION_TIME = 50;
	public static final byte NUMBER_OF_ROWS = 1;
	public static final byte NUMBER_OF_COLUMNS = 1;

	protected Bitmap bitmap;
	protected int height, width;
	protected int x;
	protected int y;
	protected float speedX;
	protected float speedY;
	protected Rect src;
	protected Rect dst;
	protected byte col, row;	// Spritesheet kords
	protected byte colNr = 1;
	protected short frameTime;
	protected short frameTimeCounter;
	
	protected GameView view;
	protected Context context;
	
	public Sprite(GameView view, Context context){
		this.view = view;
		this.context = context;
		frameTime = ANIMATION_TIME / /*Util.UPDATE_INTERVAL*/ 50;
	}
	
	public void draw(Canvas canvas){
		src = new Rect(col*width, row*height, (col+1)*width, (row+1)*height);
		dst = new Rect(x, y, x+width, y+height);
		canvas.drawBitmap(bitmap, src, dst, null);
	}
	
	public void move(){
		/*
		this.frameTimeCounter++;
		if(this.frameTimeCounter >= this.frameTime){
			this.col = (byte) ((this.col+1) % this.colNr);
			this.frameTimeCounter = 0;
		}
		*/
		x+= speedX;
		y+= speedY;
	}
	
	public boolean isOutOfRange(){
		return this.x + width < 0;
	}
	
	/**
	 * checks whether the sprite is touching this.
	 * with the distance of the 2 centers.
	 * @param sprite
	 * @return
	 */
	public boolean isColliding(Sprite sprite){
		if(this.x + getCollisionTolerance() < sprite.x + sprite.width
				&& this.x + this.width > sprite.x + getCollisionTolerance()
				&& this.y + getCollisionTolerance() < sprite.y + sprite.height
				&& this.y + this.height > sprite.y + getCollisionTolerance()){
			return true;
		}
		return false;
	}
	
	public boolean isCollidingRadius(Sprite sprite, float factor){
		int m1x = this.x+(this.width>>1);
		int m1y = this.y+(this.height>>1);
		int m2x = sprite.x+(sprite.width>>1);
		int m2y = sprite.y+(sprite.height>>1);
		int dx = m1x - m2x;
		int dy = m1y - m2y;
		int d = (int) Math.sqrt(dy*dy + dx*dx);
		
		if(d < (this.width + sprite.width) * factor
			|| d < (this.height + sprite.height) * factor){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isTouching(int x, int y){
		if(	x /* + Util.ATTACK_AREA_EFFECT */ > this.x && x /* - Util.ATTACK_AREA_EFFECT */ < this.x + width
			&& y /* + Util.ATTACK_AREA_EFFECT */ > this.y && y /* - Util.ATTACK_AREA_EFFECT */ < this.y + height){
			return true;
		}
		return false;
	}
	
	public void onCollision(){
		
	}
	
	public boolean isPassed(){
		return this.x + this.width < view.cow.getX();
	}
	
	public void onPass(){
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}
	
	public Bitmap createBitmap(Drawable drawable){
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bm = bd.getBitmap();
		return Bitmap.createScaledBitmap(bm,
				(int)(bm.getWidth() /* * getScaleFactor() */),
				(int)(bm.getHeight() /* * getScaleFactor() */),
				false);
	}
	
	public float getScaleFactor(){
		// 1.0 @ 720x1280 px
		return context.getResources().getDisplayMetrics().heightPixels / 1280f;
	}
	
	private int getCollisionTolerance(){
		// 25 @ 720x1280 px
		return context.getResources().getDisplayMetrics().heightPixels / 50;
	}

}