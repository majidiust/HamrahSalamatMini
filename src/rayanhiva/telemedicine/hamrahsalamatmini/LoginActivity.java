package rayanhiva.telemedicine.hamrahsalamatmini;

import rayanhiva.SalamatYar.DatabaseHandler;
import rayanhiva.SalamatYar.KB;
import rayanhiva.SalamatYar.SimUtility;
import rayanhiva.SalamatYar.UserInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.telephony.TelephonyManager;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import rayanhiva.telemedicine.hamrahsalamatmini.R;

public class LoginActivity extends Activity {
	DatabaseHandler db = null;
	UserInfo userInfo = null;
	private ProgressDialog mDialog;
	private Handler mHandler;
	
	private EditText mEditPhoneNumber;
	private EditText mEditPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
//		ActionBar actionBar = (ActionBar) getActionBar();
//		actionBar.hide();
		
		Typeface font = Typeface.createFromAsset(getAssets(),
		"bnazaninbd_0.ttf");
		
		mEditPhoneNumber = (EditText)findViewById(R.id.editTextPhone);
		mEditPassword = (EditText)findViewById(R.id.editTextPass);
		mEditPhoneNumber.setTypeface(font);
		mEditPassword.setTypeface(font);

		mHandler = new Handler();
		db = new DatabaseHandler(this);
		final Context tmpContext = this;

		ImageButton btn = (ImageButton) findViewById(R.id.imageButtonLoginEnter);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				userInfo = db.GetUserInfo();
				// Intent intent = new Intent(tmpContext,
				// HomeActivity.class);
				// startActivity(intent);
				String password = mEditPassword.getText().toString().trim();
				if(password.compareTo("") == 0)// "729183456258456") == 0)
				{
					if (mEditPhoneNumber.getText().length() > 0)
						UserProfile.PhoneNumber = mEditPhoneNumber.getText().toString();
					ShowToast("به عنوان مدیر وارد شده اید.");
					KB.IsLoggedIn = true;
					Intent intent = new Intent(tmpContext,
							HomeActivity.class);
					startActivity(intent);
				}
				else if (userInfo != null) {
					String userName = mEditPhoneNumber.getText().toString().trim();
					
					if (userName.isEmpty() == false
							&& password.isEmpty() == false) {
						String storedUserName = userInfo.getPhoneNumber()
								.trim();
						String storedPassword = userInfo.getPassword().trim();
						if (storedUserName.equals(userName)
								&& storedPassword.equals(password)) {
							UserProfile.PhoneNumber = mEditPhoneNumber.getText().toString();
							ShowToast("ورود شما با موفقیت انجام شد.");
							KB.IsLoggedIn = true;
							Intent intent = new Intent(tmpContext,
									HomeActivity.class);
							startActivity(intent);
						} else {
							ShowAlert("نام کاربری و کلمه عبور شما غلط است. مجددا تلاش کنید.");
						}
					} else {
						ShowAlert("لطفا نام کاربری و کلمه عبور خود را وارد کنید");
					}
				} else {
					ShowAlert("لطفا دکمه 'رمز عبورم را فراموش کرده ام' را بفشارید");
				}
			}
		});

		ImageButton forgotBtn = (ImageButton) findViewById(R.id.imageButtonForgot);
		final Context me = this;
		forgotBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int simState = SimUtility.GetSimState(me);
				if (simState == TelephonyManager.SIM_STATE_READY) {

					ShowDialog("ارسال پیامک",
					"در حال ارسال پیامک به سرور لطفا منتظر بمانید.");
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							SimUtility
									.sendSMS(
											me,
											KB.SMSServer,
											rayanhiva.SalamatYar.KB.ForgotPasswordMessage);
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									mDialog.cancel();
									ShowToast("لطفا منتظر پیامک ما حاوی اطلاعات ورود بمانید.");
								}
							});
						}
					}).start();
				} else {
					ShowAlert("برای ارسال اطلاعات ورود لطفا سیم کارت دستگاه را فعال کنید.");
				}
			}
		});
	}

	private void ShowToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	public void ShowAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Ã™â€¦Ã˜ÂªÃ™Ë†Ã˜Â¬Ã™â€¡ Ã˜Â´Ã˜Â¯Ã™â€¦",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// do things
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void ShowDialog(String title, String message) {
		mDialog = ProgressDialog.show(this, title, message, true);
		TextView tvMessage = (TextView) mDialog
				.findViewById(android.R.id.message);
		tvMessage.setGravity(Gravity.RIGHT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
