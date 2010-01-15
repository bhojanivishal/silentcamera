package la.zeg.android.silentcamera.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import la.zeg.android.silentcamera.R;
import la.zeg.android.silentcamera.view.CameraView;

public class CameraActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.camera);
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
          CameraView v = (CameraView) findViewById(R.id.camera_view);
          v.takePicture();
          return true;
    }
    return false;
  }
}
