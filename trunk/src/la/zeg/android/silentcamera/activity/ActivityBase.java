package la.zeg.android.silentcamera.activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;

public class ActivityBase extends Activity {

  public void asyncRequest(final Runnable r) {
    final Handler h = new Handler();
    final Thread t = new Thread(new Runnable() {
      public void run() {
        h.postDelayed(r, 1000);
      }
    });
    t.start();
  }
  public void pageAlert(String title,String message  ){
    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setTitle(title);
    alert.setMessage(message);
    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    public void onClick(DialogInterface dialog, int whichButton) {
       finish();
    }});
    alert.show();
   }
}
