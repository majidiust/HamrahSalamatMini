package rayanhiva.telemedicine.hamrahsalamatmini;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import rayanhiva.telemedicine.hamrahsalamatmini.R;

public class PersonalInfoActivity extends Activity {

	private String mDays[];
	private String mYears[];

	private EditText mEditFirstName;
	private EditText mEditLastName;
	private EditText mEditNationalID;
	private EditText mEditEmail;
	private EditText mEditPhone;

	private Spinner mSpinBirthProvince;
	private Spinner mSpinResidenceProvince;
	private Spinner mSpinEducation;
	private Spinner mSpinDay;
	private Spinner mSpinMonth;
	private Spinner mSpinYear;

	private ImageButton mBtnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
		//		ActionBar actionBar = (ActionBar)getActionBar();
		//		actionBar.hide();

		mDays = new String[31];
		for (int i = 0; i < 31; ++i){
			mDays[i] = String.valueOf(i + 1);
		}

		mYears = new String[90];
		Calendar cal = new GregorianCalendar();
		int currentYear = cal.get(GregorianCalendar.YEAR);

		for (int i = 10; i < 100; ++i){
			mYears[i - 10] = String.valueOf(currentYear - 621 - i);
		}

		Typeface font = Typeface.createFromAsset(getAssets(),
				"bnazaninbd_0.ttf");

		// Set items in the spinner for province of residence
		mSpinResidenceProvince = (Spinner)findViewById(R.id.spinnerResidenceProvince);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.provinces_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		mSpinResidenceProvince.setAdapter(adapter);

		TextView text = (TextView)findViewById(R.id.txtViewBirthProvince);
		text.setTypeface(font);
		text = (TextView)findViewById(R.id.txtViewResidenceProvince);
		text.setTypeface(font);
		text = (TextView)findViewById(R.id.txtViewEducationLevel);
		text.setTypeface(font);
		text = (TextView)findViewById(R.id.txtViewGender);
		text.setTypeface(font);
		text = (TextView)findViewById(R.id.txtViewBirthDate);
		text.setTypeface(font);
		text = (TextView)findViewById(R.id.txtViewMaritalStatus);
		text.setTypeface(font);

		mEditFirstName = (EditText)findViewById(R.id.editFirstName);
		mEditFirstName.setTypeface(font);
		mEditLastName = (EditText)findViewById(R.id.editLastName);
		mEditLastName.setTypeface(font);
		mEditNationalID = (EditText)findViewById(R.id.editNatID);
		mEditNationalID.setTypeface(font);
		mEditEmail = (EditText)findViewById(R.id.editEmail);
		mEditEmail.setTypeface(font);
		mEditPhone = (EditText)findViewById(R.id.editPhone);
		mEditPhone.setTypeface(font);

		// Set items in the spinner for province of birth
		mSpinBirthProvince = (Spinner)findViewById(R.id.spinnerBirthProvince);
		adapter = ArrayAdapter.createFromResource(this,
				R.array.provinces_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		mSpinBirthProvince.setAdapter(adapter);

		// Set items in the spinner for education levels
		mSpinEducation = (Spinner)findViewById(R.id.spinnerEduLevel);
		adapter = ArrayAdapter.createFromResource(this,
				R.array.edulevels_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		mSpinEducation.setAdapter(adapter);

		// Set items in the spinner for days of month
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mDays);
		mSpinDay = (Spinner)findViewById(R.id.spinnerBirthDay);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		mSpinDay.setAdapter(arrayAdapter);

		// Set items in the spinner for months of year
		mSpinMonth = (Spinner)findViewById(R.id.spinnerBirthMonth);
		adapter = ArrayAdapter.createFromResource(this,
				R.array.months_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		mSpinMonth.setAdapter(adapter);

		// Set items in the spinner for year of birth
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mYears);
		mSpinYear = (Spinner)findViewById(R.id.spinnerBirthYear);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		mSpinYear.setAdapter(arrayAdapter);

		final RadioButton btnMale = (RadioButton)findViewById(R.id.radioMale);
		final RadioButton btnFemale = (RadioButton)findViewById(R.id.radioFemale);

		btnMale.setTypeface(font);
		btnFemale.setTypeface(font);

		btnMale.setChecked(true);
		btnFemale.setChecked(false);

		btnMale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnMale.setChecked(true);
				btnFemale.setChecked(false);
			}
		});

		btnFemale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnFemale.setChecked(true);
				btnMale.setChecked(false);
			}
		});

		final RadioButton btnSingle = (RadioButton)findViewById(R.id.radioSingle);
		final RadioButton btnMarried = (RadioButton)findViewById(R.id.radioMarried);

		btnSingle.setTypeface(font);
		btnMarried.setTypeface(font);

		btnSingle.setChecked(true);
		btnMarried.setChecked(false);

		btnSingle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnSingle.setChecked(true);
				btnMarried.setChecked(false);
			}
		});

		btnMarried.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnMarried.setChecked(true);
				btnSingle.setChecked(false);
			}
		});

		mBtnSubmit = (ImageButton)findViewById(R.id.imageButtonSubmit);
		mBtnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (mEditFirstName.getText().length() > 0)
						UserProfile.FirstName = mEditFirstName.getText().toString();
					if (mEditLastName.getText().length() > 0)
						UserProfile.LastName = mEditLastName.getText().toString();
					if (mEditNationalID.getText().length() > 0)
						UserProfile.NationalID = mEditNationalID.getText().toString();
					UserProfile.DeviceID = "";
					int selectedIndex = mSpinBirthProvince.getSelectedItemPosition();
					if (selectedIndex != Spinner.INVALID_POSITION)
						UserProfile.BirthProvince = selectedIndex;
					selectedIndex = mSpinResidenceProvince.getSelectedItemPosition();
					if (selectedIndex != Spinner.INVALID_POSITION)
						UserProfile.ResidenceProvince = selectedIndex;
					selectedIndex = mSpinEducation.getSelectedItemPosition();
					if (selectedIndex != Spinner.INVALID_POSITION)
						UserProfile.EducationLevel = selectedIndex;
					UserProfile.UserGender = btnMale.isChecked() ? Gender.G_Male : Gender.G_Female;
					if (mEditEmail.getText().length() > 0)
						UserProfile.Email = mEditEmail.getText().toString();
					if (mEditPhone.getText().length() > 0)
						UserProfile.PhoneNumber = mEditPhone.getText().toString();
					if (mSpinDay.getSelectedItemPosition() != Spinner.INVALID_POSITION &&
							mSpinMonth.getSelectedItemPosition() != Spinner.INVALID_POSITION &&
							mSpinYear.getSelectedItemPosition() != Spinner.INVALID_POSITION) {
						String birthdate = "";
						birthdate += mYears[mSpinYear.getSelectedItemPosition()] + "/";
						birthdate += String.valueOf(mSpinMonth.getSelectedItemPosition() + 1) + "/";
						birthdate += mDays[mSpinDay.getSelectedItemPosition()];
						UserProfile.BirthDate = birthdate;
					}					
					UserProfile.Marital = btnSingle.isChecked() ? MaritalStatus.MS_Single : MaritalStatus.MS_Married;
					
					ShowToast("اطلاعات شما ثبت گردید");
				} catch (Exception ex) {
					Log.d("PersonalInfo", ex.getMessage());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_info, menu);
		return true;
	}

	private void ShowToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
		.show();
	}
}
