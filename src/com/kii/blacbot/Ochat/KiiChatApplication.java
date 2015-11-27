package com.kii.blacbot.Ochat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kii.blacbot.Ochat.util.Logger;
import com.kii.cloud.analytics.KiiAnalytics;
import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.Kii.Site;
import com.kii.cloud.storage.KiiUser;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

/**
 * Custom implementation of Application.
 * 
 */
public class KiiChatApplication extends Application {
	
	private static Context context;
	
	public void onCreate() {
		//super.onCreate();

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(
                    "com.kii.blacbot.Ochat", PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        
		context = this;
		// Initialize SDK when application is started.
		//KiiUser.pushInstallation().install();
		Logger.i("■■■ initialize KII SDK ■■■");
		Kii.initialize(ApplicationConst.APP_ID, ApplicationConst.APP_KEY, Site.US);
		Logger.i("■■■ initialize KII Analytics SDK ■■■");
		KiiAnalytics.initialize(context, ApplicationConst.APP_ID, ApplicationConst.APP_KEY, KiiAnalytics.Site.JP);
	

		
		
	}
	public static Context getContext(){
		return context;
	}
	public static String getMessage(int msgId) {
		return context.getResources().getString(msgId);
	}
	public static String getFormattedMessage(int msgId, Object... args) {
		String message = context.getResources().getString(msgId);
		return String.format(message, args);
	}
}
