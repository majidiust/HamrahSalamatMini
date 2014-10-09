package rayanhiva.telemedicine.hamrahsalamatmini;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import rayanhiva.SalamatYar.DatabaseHandler;
import rayanhiva.SalamatYar.KB;
import rayanhiva.SalamatYar.SimUtility;
import rayanhiva.telemedicine.hamrahsalamatmini.R;

public class MainActivity extends Activity {
	Handler mHandler;
	DatabaseHandler mDb = null;
	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		ActionBar actionBar = 
//		actionBar.hide();		
		
		mHandler = new Handler();
		mDb = new DatabaseHandler(this);
		final Context tmpContext = this;
		
		UserProfile.IMEI = SimUtility.getIMEI(tmpContext);
		
		ImageButton btn = (ImageButton) findViewById(R.id.imageButtonLogin);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(tmpContext, LoginActivity.class);
				startActivity(intent);
			}
		});

		ImageButton loginBtn = (ImageButton) findViewById(R.id.imageButtonRegister);
		final Context me = this;
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					int simState = SimUtility.GetSimState(me);
					if (simState == TelephonyManager.SIM_STATE_READY) {
						if (mDb.GetAppState() < 1) {
							try {
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
										SimUtility.sendSMS(me, KB.SMSServer, KB.RegisterMessage);								
										mHandler.post(new Runnable() {
											@Override
											public void run() {
												mDialog.cancel();
											}
										});
									}
								}).start();

							} catch (Exception ex) {
								ShowToast("خطا در هنگام ارسال پیامک ");
								if (mDialog != null) {
									if (mDialog.isShowing())
										mDialog.cancel();
								}
							}
						} else {
							ShowToast("ثبت نام شما انجام شده است. لطفا وارد سامانه شوید.");
						}
					} else {
						ShowToast("برای ثبت نام لطفا سیم کارت دستگاه را فعال کنید.");
					}
				} catch (Exception ex) {
					ShowToast(ex.getMessage());
				}
			}
		});
	}

	private void ShowToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}
	
	public void ShowAlert()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Look at this dialog!")
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                //do things
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
}
