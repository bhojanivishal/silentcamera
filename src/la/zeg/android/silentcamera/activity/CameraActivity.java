package la.zeg.android.silentcamera.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import la.zeg.android.silentcamera.R;
import la.zeg.android.silentcamera.helper.DummyCameraClient;
import la.zeg.android.silentcamera.view.CameraView;

public class CameraActivity extends Activity {
  public static byte rawData[];
  @Override
  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.camera);
   initializeCamera();
  }
  
  private void initializeCamera(){

	   WebView dummyCameraView = (WebView)findViewById(R.id.dummy_camera_view);

	   if(SettingActivity.getDummyMonitor(this)){
		 dummyCameraView.setVisibility(View.VISIBLE);
	     dummyCameraView.setWebViewClient(new DummyCameraClient(this)); 
	     dummyCameraView.getSettings().setSupportZoom(true);
	     dummyCameraView.getSettings().setBuiltInZoomControls(true);
	     dummyCameraView.getSettings().setJavaScriptEnabled(true); 
	     dummyCameraView.loadUrl(getString(R.string.config_dummy_camera_url));
	     dummyCameraView.setInitialScale(100);
	   }else{
		 dummyCameraView.setVisibility(View.GONE);
	   }
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu);
     MenuInflater inflater = getMenuInflater();
     inflater.inflate(R.menu.camera, menu);
     return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    Intent intent = null;
    switch (item.getItemId()) {
        case R.id.menu_about:
          intent = new Intent(this,AboutActivity.class);
          startActivityForResult(intent,0);
          return true;
        case R.id.menu_preference:
            intent = new Intent(this,SettingActivity.class);
            startActivityForResult(intent,0);
            return true;
        case R.id.menu_take_a_picture:
          CameraView cameraView = (CameraView) findViewById(R.id.camera_view);
          cameraView.takePicture();
          return true;
    }
    return false;
  }
  @Override
  public void onActivityResult(int requestCode, int resultCode,Intent data){
	  initializeCamera();
	  
  }
}
