package la.zeg.android.silentcamera.helper;

import la.zeg.android.silentcamera.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public final class DummyCameraClient extends WebViewClient {
    private ProgressDialog loading; 
    private Context context;

    public DummyCameraClient(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        loading = new ProgressDialog(view.getContext());
        loading.setIndeterminate(true);
        loading.setMessage(context.getString(R.string.loading));
        loading.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
    	if(null!=loading){
          loading.dismiss();
    	}
    }

    public void onRecievedError(WebView view, int code , String description , String url) {
    	if(null!=loading){
          loading.dismiss();
    	}
    }
}
