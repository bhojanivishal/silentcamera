package la.zeg.android.silentcamera.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import la.zeg.android.silentcamera.R;
import la.zeg.android.silentcamera.model.ItemColor;
import la.zeg.android.silentcamera.view.Preview;

public class PreviewActivity extends ActivityBase 
implements View.OnClickListener {
  ProgressDialog saving = null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras(); 
    final int width = extras.getInt("width");
    final int height = extras.getInt("height");
    final byte []  rawData = extras.getByteArray("rawData");
    int [] pickuppedColorData = new int[rawData.length];
    ItemColor.decodeYUV(rawData, pickuppedColorData,width, height);

    setContentView(R.layout.preview);
    
    Preview view = (Preview) findViewById(R.id.preview);
    view.setOnClickListener(this);
    view.setColorDatas(pickuppedColorData, 
        width,
        height);
    view.setVisibility(View.VISIBLE);
  }

  @Override
  public void onClick(View v) {
      AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle(R.string.preview_title);
        ad.setMessage(R.string.preview_message);
        ad.setPositiveButton(R.string.preview_ok,new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog,int whichButton) {
                save();
          }
        });
        ad.setNegativeButton(R.string.preview_ng,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                finish();
            }
        });
        ad.create();
        ad.show();
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu);
     MenuInflater inflater = getMenuInflater();
     inflater.inflate(R.menu.preview, menu);
     return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.menu_about:
          Intent intent = new Intent(this,AboutActivity.class);
          startActivityForResult(intent,0);
          return true;
        case R.id.menu_save:
          save();
          return true;
        case R.id.menu_cancel:
            finish();
            return true;
    }
    return false;
  }

  private void save(){
	  saving = new ProgressDialog(findViewById(R.id.preview)
              .getContext());
      saving.setIndeterminate(true);
      saving.setMessage(getString(R.string.saving));
      saving.show();
      asyncRequest(new Runnable() {
        public void run() {
            Preview v = (Preview) findViewById(R.id.preview);
            Bitmap bitmap = v.getBitmap();

            Time now = new Time();
            now.setToNow();
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,now.format2445(), null);
            saving.dismiss(); 
            finish();
        }
      });
  }
}