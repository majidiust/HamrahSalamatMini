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

	private RadioButton mRadioMale;
	private RadioButton mRadioFemale;
	private RadioButton mRadioSingle;
	private RadioButton mRadioMarried;

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

		mRadioMale = (RadioButton)findViewById(R.id.radioMale);
		mRadioFemale = (RadioButton)findViewById(R.id.radioFemale);

		mRadioMale.setTypeface(font);
		mRadioFemale.setTypeface(font);

		mRadioMale.setChecked(true);
		mRadioFemale.setChecked(false);

		mRadioMale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mRadioMale.setChecked(true);
				mRadioFemale.setChecked(false);
			}
		});

		mRadioFemale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mRadioFemale.setChecked(true);
				mRadioMale.setChecked(false);
			}
		});

		mRadioSingle = (RadioButton)findViewById(R.id.radioSingle);
		mRadioMarried = (RadioButton)findViewById(R.id.radioMarried);

		mRadioSingle.setTypeface(font);
		mRadioMarried.setTypeface(font);

		mRadioSingle.setChecked(true);
		mRadioMarried.setChecked(false);

		mRadioSingle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mRadioSingle.setChecked(true);
				mRadioMarried.setChecked(false);
			}
		});

		mRadioMarried.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mRadioMarried.setChecked(true);
				mRadioSingle.setChecked(false);
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
					UserProfile.UserGender = mRadioMale.isChecked() ? Gender.G_Male : Gender.G_Female;
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
					UserProfile.Marital = mRadioSingle.isChecked() ? MaritalStatus.MS_Single : MaritalStatus.MS_Married;

					ShowToast("اطلاعات شما ثبت گردید");
				} catch (Exception ex) {
					Log.d("PersonalInfo", ex.getMessage());
				}
			}
		});

		loadData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_info, menu);
		return true;
	}

	private void loadData() {
		mEditFirstName.setText(UserProfile.FirstName);
		mEditLastName.setText(UserProfile.LastName);
		mEditNationalID.setText(UserProfile.NationalID);
		mEditPhone.setText(UserProfile.PhoneNumber);
		mEditEmail.setText(UserProfile.Email);
		mSpinBirthProvince.setSelection(UserProfile.BirthProvince);
		mSpinResidenceProvince.setSelection(UserProfile.ResidenceProvince);
		mSpinEducation.setSelection(UserProfile.EducationLevel);
		if (UserProfile.UserGender == Gender.G_Male) {
			mRadioMale.setChecked(true);
			mRadioFemale.setChecked(false);
		} else {
			mRadioMale.setChecked(false);
			mRadioFemale.setChecked(true);
		}
		if (UserProfile.Marital == MaritalStatus.MS_Single) {
			mRadioSingle.setChecked(true);
			mRadioMarried.setChecked(false);
		} else {
			mRadioSingle.setChecked(false);
			mRadioMarried.setChecked(true);
		}

		int birthDay = 0;
		int birthMonth = 0;
		int birthYear = 0;
		
		try {
			String[] tokens = UserProfile.BirthDate.split("/");
			birthDay = Integer.parseInt(tokens[2]) - 1;
			birthMonth = Integer.parseInt(tokens[1]) - 1;
			birthYear = 1383 - Integer.parseInt(tokens[0]);
		} catch (Exception ex) {
		} finally {
			mSpinDay.setSelection(birthDay);
			mSpinMonth.setSelection(birthMonth);
			mSpinYear.setSelection(birthYear);
		}
	}

	private void ShowToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
		.show();
	}
}
