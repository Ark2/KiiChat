package com.kii.blacbot.Ochat.ui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kii.blacbot.Ochat.PreferencesManager;
import com.kii.blacbot.Ochat.model.ChatRoom;
import com.kii.blacbot.Ochat.ui.util.SimpleProgressDialogFragment;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

/**
 * Main activity.
 * This activity does not have UI.
 * 
 */
public class MainActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//hard coded the vibrator		
		//audioManager.VIBRATE_TYPE_NOTIFICATION;
		// audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
        // audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);

		String token = PreferencesManager.getStoredAccessToken();
		Toast.makeText(this, token, 20);
		if (!TextUtils.isEmpty(token)) {
			SimpleProgressDialogFragment.show(getSupportFragmentManager(), "Login", "Processing...");
			KiiUser.loginWithToken(new KiiUserCallBack() {
				@Override
				public void onLoginCompleted(int token, KiiUser user, Exception e) {
					if (e == null) {
						ChatRoom.ensureSubscribedBucket(user);
						Intent intent = new Intent(MainActivity.this, ChatMainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else {
						PreferencesManager.setStoredAccessToken("");
						Intent intent = new Intent(MainActivity.this, SigninActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
					SimpleProgressDialogFragment.hide(getSupportFragmentManager());
				}
			},token);
		} else {
			Intent intent = new Intent(MainActivity.this, SigninActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
}
