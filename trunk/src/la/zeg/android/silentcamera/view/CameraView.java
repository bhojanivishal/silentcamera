package la.zeg.android.silentcamera.view;

import java.io.IOException;

import la.zeg.android.silentcamera.activity.CameraActivity;
import la.zeg.android.silentcamera.activity.PreviewActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class CameraView extends SurfaceView implements Callback {

    private SurfaceHolder holder;
    private Camera camera = null;
    private Camera.Parameters parameter = null;
    private boolean inProgress   = false;
    private boolean hasSurface   = false;
    private boolean isPreviewing = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      if (event.getAction()==MotionEvent.ACTION_DOWN) {
          takePicture();
      }
      return true;
    }
    public void takePicture() {
        parameter = camera.getParameters();
        camera.setPreviewCallback(new RawPreviewCallback());
        if(!inProgress){
          inProgress = true;
          camera.setPreviewCallback(new RawPreviewCallback());
        }
    }
    private final class RawPreviewCallback implements PreviewCallback {
      public void onPreviewFrame(byte[] rawData, Camera camera) {

        camera.setPreviewCallback(null);
        stopCameraPreview();

        Intent intent = new Intent(getContext(), PreviewActivity.class);
        intent.putExtra("width",parameter.getPreviewSize().width);
        intent.putExtra("height",parameter.getPreviewSize().height);
        CameraActivity.rawData = rawData;

        ((CameraActivity)getContext()).startActivityForResult(intent, 0);
        camera.stopPreview();
      }
    };

    public CameraView(Context context, AttributeSet attrs) {
        super(context,attrs);
        holder = getHolder();
        initSurface();
      }

    private void initSurface(){
      holder.addCallback(this);
      holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void startCameraPreview() {
      if (!isPreviewing) {
        camera.startPreview();
        isPreviewing = true;
      }
    }

    private void stopCameraPreview() {
      if (isPreviewing) {
        camera.stopPreview();
        isPreviewing = false;
      }
    }

    private void cameraDestroy() {
      if (camera != null) {
        stopCameraPreview();
            camera.release();
            camera = null;
      }
    }

    public void onResume() {
      if(hasSurface){
          surfaceCreated(holder);
          startCameraPreview();
      }else{
          initSurface();
      }
    }

    public void onPause() {
      cameraDestroy();
    }

    public void surfaceCreated(SurfaceHolder holder) {
      camera = Camera.open();
      parameter = camera.getParameters();
      try {
       camera.setPreviewDisplay(holder);
      } catch (IOException exception) {
        camera.release();
        camera = null;
      }
      hasSurface = true;
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
      cameraDestroy();
      hasSurface = false;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
      startCameraPreview();
    }
}
