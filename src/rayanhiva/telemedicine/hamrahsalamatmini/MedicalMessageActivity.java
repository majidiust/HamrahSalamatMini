package rayanhiva.telemedicine.hamrahsalamatmini;

import rayanhiva.SalamatYar.KB;
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

public class MedicalMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medical_message);
//		ActionBar actionBar = (ActionBar)getActionBar();
//		actionBar.hide();
		
		Intent _intent = getIntent();
		String message =_intent.getStringExtra("Message");
		TextView tvMedicalMessage = (TextView)findViewById(R.id.textViewMedicalMessage);
		tvMedicalMessage.setText(message);
		
		Typeface font = Typeface.createFromAsset(getAssets(),
		"bnazaninbd_0.ttf");

		TextView tvTopicMessage = (TextView) findViewById(R.id.textViewMedicalMessageTopic);
		tvTopicMessage.setTypeface(font);
		tvMedicalMessage.setTypeface(font);
		
		ImageButton btn = (ImageButton) findViewById(R.id.imgBtnStartAppMedicalMessage);
		final Context me = this;
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(KB.IsLoggedIn == true)
				{
					Intent intent = new Intent(me, HomeActivity.class);
					startActivity(intent);
				}
				else{
					Intent intent = new Intent(me, LoginActivity.class);
					startActivity(intent);
				}
			}
		});
		
		btn = (ImageButton) findViewById(R.id.imgBtnExitMedicalMessage);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medical_message, menu);
		return true;
	}

}
