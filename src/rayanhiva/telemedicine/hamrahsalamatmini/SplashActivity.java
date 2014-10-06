package rayanhiva.telemedicine.hamrahsalamatmini;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import rayanhiva.telemedicine.hamrahsalamatmini.R;

public class SplashActivity extends Activity {

	
	Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Splash", "BEFORE LOADING XML UI");
		setContentView(R.layout.activity_splash);
		Log.d("Splash", "AFTER LOADING XML UI");
//		ActionBar actionBar =  (ActionBar)getActionBar();
//		actionBar.hide();
		mHandler = new Handler();
		final Context tmpContext = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(tmpContext, MainActivity.class);
							startActivity(intent);
							finish();
						}
					});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
