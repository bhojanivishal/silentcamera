package la.zeg.android.silentcamera.activity;

import la.zeg.android.silentcamera.R;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingActivity extends PreferenceActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
	
	public static boolean getSaveImmediately(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("pref_save_immediately_key",false);	
	}
}
