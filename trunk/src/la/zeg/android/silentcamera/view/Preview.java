package la.zeg.android.silentcamera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class Preview extends View {
   private Bitmap pickuppedBitmap = null;

   public Preview(Context context,AttributeSet attrs) {
    super(context,attrs);
   }

   public void setColorDatas(int[] pickuppedColorData ,int width,int height){
     pickuppedBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
     pickuppedBitmap.setPixels(pickuppedColorData, 0, width, 0, 0, width, height);
   }

   @Override
   protected void onDraw(Canvas canvas) {
     if(null == this.pickuppedBitmap)
       return;
     canvas.drawBitmap(pickuppedBitmap, 0,0,null);
   }

   public Bitmap getBitmap(){
     return pickuppedBitmap;
   }
}
