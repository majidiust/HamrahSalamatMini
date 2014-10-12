package rayanhiva.telemedicine.hamrahsalamatmini;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SignupActivity extends Activity {
	
	private EditText mEditPhoneNumber;
	private EditText mEditNationalID;
	private EditText mEditPassword;
	private EditText mEditPasswordConfirm;
	private EditText mEditValidationCode;
	private ImageButton mBtnGetCode;
	private ImageButton mBtnSubmit;
	
	private String mValidationCode = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "bnazaninbd_0.ttf");
		
		mEditPhoneNumber = (EditText)findViewById(R.id.editSignupPhone);
		mEditNationalID = (EditText)findViewById(R.id.editSignupNationalID);
		mEditPassword = (EditText)findViewById(R.id.editSignupPass);
		mEditPasswordConfirm = (EditText)findViewById(R.id.editSignupPassConfirm);
		mEditValidationCode = (EditText)findViewById(R.id.editSignupValidation);
		
		mEditPhoneNumber.setTypeface(font);
		mEditNationalID.setTypeface(font);
		mEditPassword.setTypeface(font);
		mEditPasswordConfirm.setTypeface(font);
		mEditValidationCode.setTypeface(font);
		
		mBtnGetCode = (ImageButton)findViewById(R.id.btnSignupGetCode);
		mBtnGetCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO: Create a 4-digit code and send it to the given phone number
				Random random = new Random();
				mValidationCode = String.valueOf(Math.floor(random.nextDouble() * 8889) + 1111);
				Log.d("Signup", mValidationCode);
				
				ShowToast("یک کد 4 رقمی به شماره شما ارسال شده است."
						+ " لطفاً این کد را در کادر وارد نموده و دکمه تأیید را فشار دهید");
				mEditPhoneNumber.setEnabled(false);
				mEditNationalID.setVisibility(View.VISIBLE);
				mEditPassword.setVisibility(View.VISIBLE);
				mEditPasswordConfirm.setVisibility(View.VISIBLE);
				mEditValidationCode.setVisibility(View.VISIBLE);
				mBtnGetCode.setVisibility(View.INVISIBLE);
				mBtnSubmit.setVisibility(View.VISIBLE);
			}
		});
		
		mBtnSubmit = (ImageButton)findViewById(R.id.btnSignupSubmit);
		mBtnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mValidationCode.compareTo(mEditValidationCode.getText().toString()) == 0) {
					ShowAlert("کد وارد شده صحیح است");
				}
			}
		});
	}

	public void ShowAlert(String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//do things
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void ShowToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
		.show();
	}
}
