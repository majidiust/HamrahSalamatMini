package rayanhiva.telemedicine.hamrahsalamatmini;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;
import rayanhiva.telemedicine.hamrahsalamatmini.R;

public class ParametersMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parameters_main);
		
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parameters_main, menu);
		return true;
	}

}
