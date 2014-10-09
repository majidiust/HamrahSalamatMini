package rayanhiva.telemedicine.hamrahsalamatmini;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import rayanhiva.SalamatYar.KB;
import rayanhiva.SalamatYar.SimUtility;
import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import rayanhiva.telemedicine.hamrahsalamatmini.R;

public class SubmitHealthActivity extends Activity {

	protected static final String FILE_SERVER_URL = "http://health-cloud.ir:80/api/v1/kiosk_services";//  5.200.78.14:80/api/v1/kiosk_services";
	Handler mHandler ;
	private ProgressDialog mDialog;
	private EditText mEditIBPSys;
	private EditText mEditIBPDia;
	private EditText mEditHeartRate;
	private EditText mEditBloodSugar;
	private EditText mEditSPO2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_health);
		//		ActionBar actionBar = (ActionBar)getActionBar();
		//		actionBar.hide();
		mHandler = new Handler();

		Typeface font = Typeface.createFromAsset(getAssets(),
		"bnazaninbd_0.ttf");

		mEditIBPSys = (EditText)findViewById(R.id.editPressureHigh);
		mEditIBPDia = (EditText)findViewById(R.id.editPressureLow);
		mEditHeartRate = (EditText)findViewById(R.id.editHeartRate);
		mEditBloodSugar = (EditText)findViewById(R.id.editGlucose);
		mEditSPO2 = (EditText)findViewById(R.id.editOxygenPercent);

		mEditIBPSys.setTypeface(font);
		mEditIBPDia.setTypeface(font);
		mEditHeartRate.setTypeface(font);
		mEditBloodSugar.setTypeface(font);
		mEditSPO2.setTypeface(font);

		ImageButton btnSendVitals = (ImageButton)findViewById(R.id.imgBtnSend);
		btnSendVitals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CreateMessage();
			}
		});
	}

	public void ShowAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("متوجه شدم",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void CreateMessage(){
		String pressureSys = mEditIBPDia.getText().toString();
		String pressureDia = mEditIBPDia.getText().toString();
		String heartRate = mEditHeartRate.getText().toString();
		String glucose = mEditBloodSugar.getText().toString();
		String spo2 = mEditSPO2.getText().toString();
		boolean IsSendPressure = false;
		boolean IsSendGlucose = false;
		boolean IsSendHR = false;
		boolean IsSendOxygen = false;
		//		boolean hasError = false;
		String PressureMessage = "";
		String GlucoseMessage = "";
		String OxygenMessage = "";
		String HRMessage = "";

		if((pressureSys.isEmpty() == false && pressureDia.isEmpty()) ||
				(pressureDia.isEmpty() == false && pressureSys.isEmpty())) {
			ShowAlert("برای ارسال فشار خون فشار بالا و پایین را وارد کنید.");
			return;
		}
		else if(pressureSys.isEmpty() == false && pressureDia.isEmpty() == false) {
			IsSendPressure = true;
		}

		if(spo2.isEmpty() == false) {
			IsSendOxygen = true;
		}
		if(heartRate.isEmpty() == false) {
			IsSendHR = true;
		}
		if(glucose.isEmpty() == false) {
			IsSendGlucose = true;
		}

		if(IsSendGlucose == false && IsSendHR == false && IsSendOxygen == false && IsSendPressure == false){
			ShowAlert("مقداری برای ارسال وجود ندارد.");
		}
		else// if(hasError == false)
		{
			String DialogMessage = "مقادیر ";
			Vector<String> Messages = new Vector<String>();
			if(IsSendGlucose == true){
				GlucoseMessage = KB.GlOMessage.replace("{0}", glucose);
				DialogMessage += " قندخون ";
				Messages.add(GlucoseMessage);
			}
			if(IsSendPressure == true){
				PressureMessage = KB.BPMessage.replace("{0}", pressureSys);
				PressureMessage = PressureMessage.replace("{1}", pressureDia);
				DialogMessage += " فشارخون ";
				Messages.add(PressureMessage);
			}
			if(IsSendHR == true){
				HRMessage = KB.HRMessage.replace("{0}", HRMessage);
				DialogMessage += " ضربان قلب ";
				Messages.add(HRMessage);
			}
			if(IsSendOxygen == true){
				OxygenMessage = KB.SPO2Message.replace("{0}", spo2);
				DialogMessage += " اکسیژن ";
				Messages.add(OxygenMessage);
			}
			DialogMessage += " ارسال خواهد شد. ";
			//			ShowAlert(DialogMessage);

			//	send SMS
			try {
				//				SendMessage(Messages);
			} catch (Exception ex) {
				Log.d("SubmitHealth", ex.getMessage());
			}

			//	send to server
			if (UserProfile.PhoneNumber.isEmpty() || UserProfile.NationalID.isEmpty()) {
				ShowAlert("لطفاً ابتدا شماره تلفن و کد ملی خود را در صفحه اطلاعات شخصی وارد نمائید.");
				return;
			}
			sendDataToServer(pressureSys, pressureDia, heartRate, glucose, spo2);
		}
	}

	public void ShowDialog(String title, String message) {
		mDialog = ProgressDialog.show(this, title, message, true);
		TextView tvMessage = (TextView) mDialog
		.findViewById(android.R.id.message);
		tvMessage.setGravity(Gravity.RIGHT);
	}

	public void SendMessage(final Vector<String> msg){
		final Context me = this;
		ShowDialog("ارسال پیامک",
		"در حال ارسال پیامک به سرور لطفا منتظر بمانید.");
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(String message : msg){
					SimUtility.sendSMS(me, KB.SMSServer, message);
				}

				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mDialog.cancel();
						ShowToast("لطفا منتظر پاسخ پزشک بمانید.");
					}
				});
			}
		}).start();
	}

	private void sendDataToServer(final String ibpSys, final String ibpDia, final String hr, final String glucose, final String spo2) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(FILE_SERVER_URL);
					JSONObject json = new JSONObject();
					json.put("mobile", UserProfile.PhoneNumber);
					json.put("national_id", UserProfile.NationalID);
					json.put("device_id", "");
					json.put("nibp_sys", ibpSys);
					json.put("nibp_dia", ibpDia);
					json.put("nibp_message", "");
					json.put("heart_rate", hr);
					json.put("blood_sugar", glucose);
					json.put("spo2", spo2);
					json.put("spo2_message", "");
					json.put("msg_platform", "mobileApp");
					String jsonString = json.toString();
					Log.d("HTTP", jsonString);
					StringEntity body = new StringEntity(jsonString);
					//					httppost.addHeader("data", jsonString);
					ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();

					parameters.add(new BasicNameValuePair("data", jsonString));
					//					HttpParams params = new BasicHttpParams();
					//					params.setParameter("data", jsonString);
					httppost.setEntity(new UrlEncodedFormEntity(parameters));
					//					httppost.setParams(params);
					//					Log.d("HTTP", httppost.getParams().getParameter("data").toString());
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity responseEntity = response.getEntity();
					final String responseString = EntityUtils.toString(responseEntity, EntityUtils.getContentCharSet(responseEntity));
					Log.d("HTTP", responseString);
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							ShowToast(responseString);
						}
					});
					finish();

				} catch (Exception ex) {
					Log.d("HTTP", "Exception: " + ex.getMessage());
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_health, menu);
		return true;
	}

	private void ShowToast(String message) {
		Toast.makeText(getApplicationContext(), message, 5000)// Toast.LENGTH_SHORT)
		.show();
	}
}
