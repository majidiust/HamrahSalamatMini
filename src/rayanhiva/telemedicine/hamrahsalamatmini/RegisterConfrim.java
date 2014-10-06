package rayanhiva.telemedicine.hamrahsalamatmini;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import rayanhiva.telemedicine.hamrahsalamatmini.R;

public class RegisterConfrim extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_confrim);
		
//		ActionBar actionBar = (ActionBar)getActionBar();
//		actionBar.hide();
		
		
		final Context tmpContext = this;
		
		Typeface font = Typeface.createFromAsset(getAssets(), "bnazaninbd_0.ttf");

		Intent _intent = getIntent();
		String userName =_intent.getStringExtra("Username");
		String password =_intent.getStringExtra("Password");
		
		TextView text = (TextView)findViewById(R.id.textViewConfirmMessage);
		text.setTypeface(font);

		TextView userNameView = (TextView)findViewById(R.id.textViewConfirmUserNameValue);
		userNameView.setText(userName);

		TextView passwordView = (TextView)findViewById(R.id.textViewConfirmPasswordValue);
		passwordView.setText(password);
		
		ImageButton btn = (ImageButton) findViewById(R.id.imgBtnStartApp);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(tmpContext, LoginActivity.class);
				startActivity(intent);
			}
		});
		
		btn = (ImageButton) findViewById(R.id.imgBtnExit);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
