package la.zeg.android.silentcamera.view;

import java.io.IOException;
import la.zeg.android.silentcamera.activity.CameraActivity;
import la.zeg.android.silentcamera.activity.PreviewActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class CameraView extends SurfaceView implements Callback {
    private SurfaceHolder holder;
    private Camera camela;
    public boolean touched =false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      if (event.getAction()==MotionEvent.ACTION_DOWN) {
        touched = true;
      }
      return true;
    }

    private final class RawPreviewCallback implements PreviewCallback {
      public void onPreviewFrame(byte[] rawData, Camera camera) {
        if(!touched || null == rawData){
          return;
        }
        Parameters p = camera.getParameters();

        Intent intent = new Intent(getContext(), PreviewActivity.class);
        intent.putExtra("width",p.getPreviewSize().width);
        intent.putExtra("height",p.getPreviewSize().height);
        intent.putExtra("rawData",rawData);

        touched = false;
        ((CameraActivity)getContext()).startActivityForResult(intent, 0);
        camera.stopPreview();
      }
    };

    public CameraView(Context context, AttributeSet attrs) {
      super(context,attrs);
      holder = getHolder();
      holder.addCallback(this);
      holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
      camela = Camera.open();
      try {
       camela.setPreviewDisplay(holder);
      } catch (IOException exception) {
        camela.release();
        camela = null;
      }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
      camela.stopPreview();
      camela.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
      camela.setPreviewCallback(new RawPreviewCallback());
      Camera.Parameters parameters = camela.getParameters();
      parameters.setPreviewSize(w, h);
      camela.setParameters(parameters);
      camela.startPreview();
    }
    
}
