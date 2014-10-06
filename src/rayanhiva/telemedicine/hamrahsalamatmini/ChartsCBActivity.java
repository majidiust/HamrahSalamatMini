package rayanhiva.telemedicine.hamrahsalamatmini;

import java.lang.reflect.InvocationTargetException;
import rayanhiva.Communication.Bluetooth.*;
import java.math.BigInteger;
import RayanHiva.PHD.CardioBlue.Protocol;
import rayanhiva.SalamatYar.KB;
import rayanhiva.SalamatYar.SimUtility;
import rayanhiva.telemedicine.hamrahsalamatmini.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.opengl.*;

public class ChartsCBActivity extends Activity {

	private static BluetoothAdapter mBluetoothAdapter = null;
	private boolean connected = false;
	private static BluetoothService mBluetoothService = null;
	private static final String TAG = "ChartsCBActivity";
	private RendererSPO2 mRendererSPO2 = null;
	private RendererECG mRendererECGChannel1 = null;
	private RendererECG mRendererECGChannel2 = null;
	private String mConnectedDeviceName = null;
	private ProgressBar batteryState = null;
	private ToggleButton btnConnectToDevice = null;
	private ProgressDialog mDialog;

	int max = 0;

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charts_cb);
		
//		ActionBar actionBar = (ActionBar)getActionBar();
//		actionBar.hide();
		
		mRendererSPO2 = new RendererSPO2(this, 12.47f);
		GLSurfaceView glView = (GLSurfaceView) findViewById(R.id.chartSPO2);
		glView.setRenderer(mRendererSPO2);

		mRendererECGChannel1 = new RendererECG(this, 10.0f);
		glView = (GLSurfaceView) findViewById(R.id.chartECG1);
		glView.setRenderer(mRendererECGChannel1);

		mRendererECGChannel2 = new RendererECG(this, 10.0f);
		glView = (GLSurfaceView) findViewById(R.id.chartECG2);
		glView.setRenderer(mRendererECGChannel2);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"bnazaninbd_0.ttf");

		TextView txtView = (TextView) findViewById(R.id.textViewSPO2ValueLabel);
		txtView.setTypeface(font);
		txtView = (TextView) findViewById(R.id.textViewSPO2Value);
		txtView.setTypeface(font);
		txtView.setTextColor(ColorStateList.valueOf(Color.argb(255, 0, 127, 14)));
		txtView = (TextView) findViewById(R.id.textViewSPO2PRLabel);
		txtView.setTypeface(font);
		txtView = (TextView) findViewById(R.id.textViewSPO2PRValue);
		txtView.setTypeface(font);
		txtView.setTextColor(ColorStateList.valueOf(Color.argb(255, 0, 127, 14)));
		txtView = (TextView) findViewById(R.id.textViewSPO2Message);
		txtView.setTypeface(font);
		txtView.setTextColor(ColorStateList.valueOf(Color.RED));
		txtView = (TextView) findViewById(R.id.textViewECGValue);
		txtView.setTypeface(font);
		txtView.setTextColor(ColorStateList.valueOf(Color.RED));

		ToggleButton tglBtn = (ToggleButton) findViewById(R.id.tglBtnDeviceConnection);
		tglBtn.setTypeface(font);

		batteryState = (ProgressBar) findViewById(R.id.progBarBattery);
		batteryState.setMax(350);

		btnConnectToDevice = (ToggleButton) findViewById(R.id.tglBtnDeviceConnection);

		btnConnectToDevice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isChecked = btnConnectToDevice.isChecked();
				if (isChecked == true) {
					btnConnectToDevice.setChecked(false);
					ShowDialog("ØµØ¨Ø± Ù†Ù…Ø§ÛŒÛŒØ¯",
							"ØªÙ„Ø§Ø´ Ø¨Ø±Ø§ÛŒ Ø§ØªØµØ§Ù„ Ø¨Ù‡ Ø¯Ø³ØªÚ¯Ø§Ù‡ ØªÙ„Ù‡ Ù…Ø§Ù†ÛŒØªÙˆØ±ÛŒÙ†Ú¯ Ø®Ø§Ù†Ú¯ÛŒ...");
					// Show Dialog
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// TODO Auto-generated method stub
							String address = KB.DeviceSerial;
							BluetoothDevice device = mBluetoothAdapter
									.getRemoteDevice(address);
							// Attempt to connect to the device
							try {
								mBluetoothService.connect(device);
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
				} else {
					// End Of Dialog
					if (mBluetoothService != null) {
						btnConnectToDevice.setChecked(false);
						new Thread(new Runnable() {
							@Override
							public void run() {
								mBluetoothService.stop();
							}
						}).start();
						// Show Dialog In order to Wait
					}
				}
			}
		});

		final Context me = this;
		ImageButton btnHRSend = (ImageButton)findViewById(R.id.imgBtnECGSend);
		btnHRSend.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (connected == false) {
					ShowAlert("Ù„Ø·Ù�Ø§ Ø§Ø¨ØªØ¯Ø§ Ø¨Ù‡ Ø¯Ø³ØªÚ¯Ø§Ù‡ Ù…ØªØµÙ„ Ø´Ø¯Ù‡ Ùˆ Ø³Ù¾Ø³ Ù…Ù‚Ø§Ø¯ÛŒØ± Ø±Ø§ Ø§Ø±Ø³Ø§Ù„ Ú©Ù†ÛŒØ¯.");
				} 
				else 
				{
					TextView tvHRValue = (TextView) findViewById(R.id.textViewECGValue);
					
					final String HRValue = (String) tvHRValue.getText();
					if (HRValue.isEmpty() == false && HRValue != "0" ) 
					{
						ShowDialog("Ø§Ø±Ø³Ø§Ù„ Ù¾ÛŒØ§Ù…Ú©",
								"Ø¯Ø± Ø­Ø§Ù„ Ø§Ø±Ø³Ø§Ù„ Ù¾ÛŒØ§Ù…Ú© Ø¨Ù‡ Ø³Ø±ÙˆØ± Ù„Ø·Ù�Ø§ Ù…Ù†ØªØ¸Ø± Ø¨Ù…Ø§Ù†ÛŒØ¯.");
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String message = rayanhiva.SalamatYar.KB.HRMessage
										.replace("{0}", HRValue);
								SimUtility.sendSMS(me, KB.SMSServer, message);
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										mDialog.cancel();
										ShowToast("Ù„Ø·Ù�Ø§ Ù…Ù†ØªØ¸Ø± Ù¾Ø§Ø³Ø® Ù¾Ø²Ø´Ú© Ø¨Ù…Ø§Ù†ÛŒØ¯.");
									}
								});
							}
						}).start();
					}
					else
					{
						ShowAlert("Ù„Ø·Ù�Ø§ Ø¨Ø¹Ø¯ Ø§Ø² Ù…Ø´Ø§Ù‡Ø¯Ù‡ Ø¹Ù„Ø§Ø¦Ù… Ø­ÛŒØ§ØªÛŒ Ø§Ù‚Ø¯Ø§Ù… Ø¨Ù‡ Ø§Ø±Ø³Ø§Ù„ Ú©Ù†ÛŒØ¯.");
					}
				}
			}
		});
		ImageButton btnSendSPO2 = (ImageButton) findViewById(R.id.imgBtnSPO2Send);
		btnSendSPO2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (connected == false) {
					ShowAlert("Ù„Ø·Ù�Ø§ Ø§Ø¨ØªØ¯Ø§ Ø¨Ù‡ Ø¯Ø³ØªÚ¯Ø§Ù‡ Ù…ØªØµÙ„ Ø´Ø¯Ù‡ Ùˆ Ø³Ù¾Ø³ Ù…Ù‚Ø§Ø¯ÛŒØ± Ø±Ø§ Ø§Ø±Ø³Ø§Ù„ Ú©Ù†ÛŒØ¯.");
				} else {
					TextView tvPulseRate = (TextView) findViewById(R.id.textViewSPO2PRValue);
					TextView tvSpo2Value = (TextView) findViewById(R.id.textViewSPO2Value);
					final int PulseRate = Integer.parseInt((String) tvPulseRate.getText());
					final int Spo2Value = Integer.parseInt((String) tvSpo2Value.getText());
					if (PulseRate != 0 && Spo2Value != 0) {
						ShowDialog("Ø§Ø±Ø³Ø§Ù„ Ù¾ÛŒØ§Ù…Ú©",
								"Ø¯Ø± Ø­Ø§Ù„ Ø§Ø±Ø³Ø§Ù„ Ù¾ÛŒØ§Ù…Ú© Ø¨Ù‡ Ø³Ø±ÙˆØ± Ù„Ø·Ù�Ø§ Ù…Ù†ØªØ¸Ø± Ø¨Ù…Ø§Ù†ÛŒØ¯.");
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String message = rayanhiva.SalamatYar.KB.SPO2Message
										.replace("{0}", String.valueOf(PulseRate));
								message = message.replace("{1}", String.valueOf(Spo2Value));
								SimUtility.sendSMS(me, KB.SMSServer, message);
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										mDialog.cancel();
										ShowToast("Ù„Ø·Ù�Ø§ Ù…Ù†ØªØ¸Ø± Ù¾Ø§Ø³Ø® Ù¾Ø²Ø´Ú© Ø¨Ù…Ø§Ù†ÛŒØ¯.");
									}
								});
							}
						}).start();
					}else
					{
						ShowAlert("Ù„Ø·Ù�Ø§ Ø¨Ø¹Ø¯ Ø§Ø² Ù…Ø´Ø§Ù‡Ø¯Ù‡ Ø¹Ù„Ø§Ø¦Ù… Ø­ÛŒØ§ØªÛŒ Ø§Ù‚Ø¯Ø§Ù… Ø¨Ù‡ Ø§Ø±Ø³Ø§Ù„ Ú©Ù†ÛŒØ¯.");
					}
				}
			}
		});

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	private void ShowToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	public void ShowAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Ù…ØªÙˆØ¬Ù‡ Ø´Ø¯Ù…",
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

	public void OnDestroy() {
		mBluetoothService.stop();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent,
					rayanhiva.SalamatYar.KB.REQUEST_ENABLE_BT);
		} else {
			if (mBluetoothService == null)
				mBluetoothService = new BluetoothService(mHandler);
		}
	}

	private void SetPulseRate(int value) {
		TextView pulseRate = (TextView) findViewById(R.id.textViewSPO2PRValue);
		pulseRate.setText("" + value);
	}

	private void SetSpo2Status(String msg) {
		TextView spo2MSG = (TextView) findViewById(R.id.textViewSPO2Message);
		spo2MSG.setText(msg);
	}

	private void SetPulseValue(int value) {
		TextView spo2Value = (TextView) findViewById(R.id.textViewSPO2Value);
		spo2Value.setText("" + value);
	}

	private void SetBattery(int value) {
		ProgressBar batteryState = (ProgressBar) findViewById(R.id.progBarBattery);
		batteryState.setProgress(value);
	}

	private void SetHeartRate(int value) {
		TextView heartRate = (TextView) findViewById(R.id.textViewECGValue);
		heartRate.setText(String.valueOf(value));
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case rayanhiva.SalamatYar.KB.MESSAGE_STATE_CHANGE:
				if (rayanhiva.SalamatYar.KB.D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
					btnConnectToDevice.setChecked(true);
					if (mDialog != null)
						mDialog.cancel();
					connected = true;
					break;
				case BluetoothService.STATE_CONNECTING:
					break;
				case BluetoothService.STATE_FAILED:
					if (mDialog != null)
						mDialog.cancel();
					break;
				case BluetoothService.STATE_NONE:
					connected = false;
					btnConnectToDevice.setChecked(false);
					SetSpo2Status("Ø¯Ø³ØªÚ¯Ø§Ù‡ ØºÛŒØ± Ù�Ø¹Ø§Ù„ Ø§Ø³Øª");
					if (mDialog != null)
						mDialog.cancel();
					break;
				}
				break;
			case rayanhiva.SalamatYar.KB.MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				// mConversationArrayAdapter.add(">>> " + writeMessage);
				if (rayanhiva.SalamatYar.KB.D)
					Log.d(TAG, "written = '" + writeMessage + "'");
				break;
			case rayanhiva.SalamatYar.KB.MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				String readMessage = new String(readBuf, 0, msg.arg1);
				if (rayanhiva.SalamatYar.KB.D)
					Log.d(TAG, readMessage);
				byte[] readBuff2 = hexEncode(readBuf).getBytes();
				if (readBuff2.length == 80) {
					RayanHiva.PHD.CardioBlue.Protocol protocol = new Protocol();
					protocol.ParsePacket(readBuff2);
					SetPulseRate(protocol.getPulseRate());
					SetPulseValue(protocol.getSPO2Percent());
					SetHeartRate(protocol.getHearRate());
					SetBattery(protocol.getBattery());
					SetSpo2Status("Ø¯Ø± Ø­Ø§Ù„ Ø¯Ø±ÛŒØ§Ù�Øª Ø§Ø·Ù„Ø§Ø¹Ø§Øª ...");
					if (protocol.isFinger() == false) {
						SetSpo2Status("Ø§Ù†Ú¯Ø´Øª Ø®ÙˆØ¯ Ø±Ø§ Ø¯Ø± Ø¯Ø³ØªÚ¯Ø§Ù‡ Ù‚Ø±Ø§Ø± Ø¯Ù‡ÛŒØ¯");
					}
					if (protocol.isSensorConnection() == false) {
						SetSpo2Status("Ø³Ù†Ø³ÙˆØ± Ø±Ø§ Ø¨Ù‡ Ø¯Ø³ØªÚ¯Ø§Ù‡ ÙˆØµÙ„ Ú©Ù†ÛŒØ¯");
					}
					mRendererSPO2
							.addNewSample((float) protocol.getData1() / 128.0f);
					mRendererSPO2
							.addNewSample((float) protocol.getData2() / 128.0f);
					mRendererSPO2
							.addNewSample((float) protocol.getData3() / 128.0f);

					// mRendererSPO2.addNewSample((float) 0 / 128.0f);
					// mRendererSPO2.addNewSample((float) 0 / 128.0f);
					// mRendererSPO2.addNewSample((float) 0 / 128.0f);

					int value = 0;

					for (int i = 0; i < 8; ++i) {
						mRendererECGChannel1.addNewSample((float) (protocol
								.getECGData(0, i) - 2000) / 100);

						value = protocol.getECGData(1, i) - 2000;
						// if (value > max)
						// max = value;
						mRendererECGChannel2.addNewSample((float) value / 100);
					}
				}
				break;
			case rayanhiva.SalamatYar.KB.MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(
						rayanhiva.SalamatYar.KB.DEVICE_NAME);
				Toast.makeText(
						getApplicationContext(),
						"Ø§Ø±ØªØ¨Ø§Ø· Ø¨Ø§ Ø¯Ø³ØªÚ¯Ø§Ù‡   " + mConnectedDeviceName
								+ " Ø¨Ø±Ù‚Ø±Ø§Ø± Ú¯Ø±Ø¯ÛŒØ¯. ", Toast.LENGTH_SHORT).show();
				break;
			case rayanhiva.SalamatYar.KB.MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(rayanhiva.SalamatYar.KB.TOAST),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	private static String hexEncode(byte[] val) {
		BigInteger b = new BigInteger(1, val);
		String t = b.toString(16);
		return t;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.charts_cb, menu);
		return true;
	}

}
