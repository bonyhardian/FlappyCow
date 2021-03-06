/**
 * Manages the Bitmap for the background: mountains and clouds
 */

package com.quchen.flappycow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

public class Background extends Sprite {

	public static Bitmap globalBitmap;
	
	public Background(GameView view, Context context) {
		super(view, context);
		if(globalBitmap == null){
			BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.bg);
			globalBitmap = bd.getBitmap();
		}
		this.bitmap = globalBitmap;
	}

	/**
	 * The whole bitmap (which is pretty long/big) is drawn.
	 * In the worst case 2 times.
	 * 
	 * If you know a more efficient way to get the wrap around done
	 * you're more than welcome to do so.
	 */
	@Override
	public void draw(Canvas canvas) {
		int scaledWidth = (int) ( 1.0 * canvas.getHeight()/bitmap.getHeight() * bitmap.getWidth());
		if(-x > scaledWidth){
			x += scaledWidth;
		}
		for(int i=0; (x + scaledWidth*i) < canvas.getWidth(); i++){
			Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			Rect dst = new Rect(x+scaledWidth*i,
								0,
								x+scaledWidth*i + scaledWidth,
								canvas.getHeight());

			canvas.drawBitmap(this.bitmap, src, dst, null);
		}
	}
}
