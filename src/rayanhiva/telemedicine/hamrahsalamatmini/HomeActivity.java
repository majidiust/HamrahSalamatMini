package rayanhiva.telemedicine.hamrahsalamatmini;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import rayanhiva.telemedicine.hamrahsalamatmini.R;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
//		ActionBar actionBar = (ActionBar)getActionBar();
//		actionBar.hide();
		
//		View actionBarView = getLayoutInflater().inflate(R.layout.home_custom_bar, null);
//		actionBar.setCustomView(actionBarView);
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		final Context tmpContext = this;

		ImageButton btn = (ImageButton)findViewById(R.id.imgBtnEquipments);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(tmpContext, DevicesActivity.class);
				startActivity(intent);
			}
		});
		
		btn = (ImageButton)findViewById(R.id.imgBtnPersonalInfo);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(tmpContext, PersonalInfoActivity.class);
				startActivity(intent);
			}
		});
		
		btn = (ImageButton)findViewById(R.id.imgBtnHealthInfo);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(tmpContext, SubmitHealthActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
