package rayanhiva.telemedicine.hamrahsalamatmini;

import rayanhiva.SalamatYar.DatabaseHandler;
import rayanhiva.SalamatYar.KB;
import rayanhiva.SalamatYar.UserInfo;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	String m_userName;
	String m_pass;
	String m_medicalMessage;
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		//---get the SMS message passed in---
		
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);     
               
                str += "SMS from " + msgs[i].getOriginatingAddress();                     
                str += " :";
                str += msgs[i].getMessageBody().toString();
                str += "\n";        
                
                String mess = msgs[i].getMessageBody().toString();
            //    Toast.makeText(context, "SMS From : " +msgs[i].getOriginatingAddress(), Toast.LENGTH_SHORT).show();
                if(IsAdminMessage(context, mess, msgs[i].getOriginatingAddress()) == true)
                {
                	 Toast.makeText(context, "Admin Message", Toast.LENGTH_SHORT).show();
                	 abortBroadcast();
                }
                else if(IsInRegisterDeliverFormat(context, mess) == true)
                {
                	try
                	{
	                	rayanhiva.SalamatYar.DatabaseHandler db = new DatabaseHandler(context);
	                	TelephonyManager telemamanger = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
						String getSimSerialNumber = telemamanger.getSimSerialNumber();
						String phoneNumber = telemamanger.getLine1Number();
						String devieID = telemamanger.getDeviceId();
						UserInfo userInfo = new UserInfo(devieID, getSimSerialNumber);
						userInfo.setPassword(m_pass);
						userInfo.setPhoneNumber(m_userName);
	                	db.AddNewUserInfo(userInfo);
	                	//Toast.makeText(context, "Password is : " + m_pass, Toast.LENGTH_SHORT).show();

	                	
						Intent act = new Intent(context, RegisterConfrim.class);
						act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						act.putExtra("Username", userInfo.getPhoneNumber());
						act.putExtra("Password", userInfo.getPassword());
						context.startActivity(act);
	                	db.UpdateAppState(1);
	                	abortBroadcast();
                	}
                	catch(Exception ex)
                	{
                		Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                	}
                }
                else if(IsDeliveryMessage(context, mess) == true){
                	Toast.makeText(context, "Ù¾ÛŒØ§Ù… Ø´Ù…Ø§ Ø¯Ø±ÛŒØ§Ù�Øª Ú¯Ø±Ø¯ÛŒØ¯ Ùˆ Ù¾Ø²Ø´Ú©Ø§Ù† Ø¯Ø± Ø­Ø§Ù„ Ø¨Ø±Ø±Ø³ÛŒ Ø¢Ù† Ù…ÛŒ Ø¨Ø§Ø´Ù†Ø¯.", Toast.LENGTH_SHORT).show();
                	abortBroadcast();
                }
                else if(IsMedicalMessage(context, mess) == true)
                {
                	try
                	{
                		int msgID = Integer.parseInt(m_medicalMessage);
                		String pm = "";
                		switch(msgID){
                		case 1:
                			pm = KB.MSG1;
                			break;
                		case 2:
                			pm = KB.MSG2;
                			break;
                		case 3:
                			pm = KB.MSG3;
                			break;
                		case 4:
                			pm = KB.MSG4;
                			break;
                		case 5:
                			pm = KB.MSG5;
                			break;
                		case 6:
                			pm = KB.MSG6;
                			break;
                		case 7:
                			pm = KB.MSG7;
                			break;
                		default:
                			pm = "Ù¾ÛŒØ§Ù… Ø¯Ø±ÛŒØ§Ù�ØªÛŒ Ù‚Ø§Ø¨Ù„ Ù†Ù…Ø§ÛŒØ´ Ù†ÛŒØ³Øª";
                		}
                		
                		Intent act = new Intent(context, MedicalMessageActivity.class);
						act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						act.putExtra("Message", pm);
						context.startActivity(act);
						
                		Toast.makeText(context, pm , Toast.LENGTH_SHORT).show();
                		abortBroadcast();
                	}
                	catch(Exception ex){
                		Toast.makeText(context,"Ù¾ÛŒØ§Ù… Ø¯Ø±ÛŒØ§Ù�ØªÛŒ Ø¯Ø± Ù�Ø±Ù…Øª Ù…Ù†Ø§Ø³Ø¨ Ù†ÛŒØ³Øª" , Toast.LENGTH_SHORT).show();
                	}
                }
                else
                {
                	//Toast.makeText(context,"Ø¨Ø±Ø§ÛŒ Ø§ÛŒÙ† Ø¨Ø±Ù†Ø§Ù…Ù‡ Ù†ÛŒØ³Øª" , Toast.LENGTH_SHORT).show();
                }
            }
            //---display the new SMS message---
            
        }        
	}

	public boolean IsAdminMessage(Context context, String msg, String from)
	{
		boolean result = true;
		try
		{
			boolean existNumber = false; 
			for(String st : KB.AdminNumbers)
			{
				int s = st.compareTo(from);
				if(s == 0 )
				{
					existNumber = true;
					break;
				}
			}
			if(existNumber)
			{
				if (msg.charAt(0) == '*' && msg.charAt(msg.length() - 1) == '#')
				{
					String s = msg.substring(1 , msg.length() - 1);
					String []args = new String[2];
					int state = 0 ;
					int lastI = 0 ;
					for(int i = 0 ; i < s.length() ; i++)
					{
						if( s.charAt(i) == '*')
						{
							if(state == 0)
							{
								args[state] = s.substring(lastI, i);
								state = 1;
								lastI = i + 1;
								break;
							}
						}
					}
					//Toast.makeText(context, "Args is : " + args[0], Toast.LENGTH_SHORT).show();
					args[state] = s.substring(lastI, s.length());
					if(Integer.parseInt(args[0]) == 110)
					{
						KB.SMSServer = args[1];
						Toast.makeText(context, "Server Address is : " + KB.SMSServer, Toast.LENGTH_SHORT).show();
					}
					else if(Integer.parseInt(args[0]) == 114)
					{
						KB.DeviceSerial = args[1];
						Toast.makeText(context, "Device Serial is : " + KB.DeviceSerial, Toast.LENGTH_SHORT).show();
					}
					else {
						result = false;
					}
				}
				else result = false;
			}
			else result = false;
		}
		catch(Exception ex)
		{
			result = false;
		}
		finally{
			return result;
		}
	}
	@SuppressWarnings("finally")
	public boolean IsInRegisterDeliverFormat(Context context, String msg) {
		boolean result = true;
		try {
			//Toast.makeText(context, "Message is : " + msg, Toast.LENGTH_SHORT).show();
			if (msg.charAt(0) == '*' && msg.charAt(msg.length() - 1) == '#') 
			{
				String s = msg.substring(1 , msg.length() - 1);
				//Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
				String []args = new String[3];
				int state = 0 ;
				int lastI = 0 ;
				for(int i = 0 ; i < s.length() ; i++)
				{
					if( s.charAt(i) == '*')
					{
						if(state == 0)
						{
							args[state] = s.substring(lastI, i);
							state = 1;
							lastI = i + 1;
						}
						else if(state == 1)
						{
							args[state] = s.substring(lastI, i);
							state = 2;
							lastI = i + 1;
							args[state] = s.substring(lastI, s.length());
						}
					}
				}
				{
					// Toast.makeText(context, args[0].trim(), Toast.LENGTH_SHORT).show();
					if(args[0].trim().indexOf("160") != -1)
					{
					//	Toast.makeText(context, "" + args[1].length(), Toast.LENGTH_SHORT).show();
						if (args[1].length() == 10) {
							try
							{
								int pass = Integer.parseInt(args[2]);
								//Toast.makeText(context, "" + pass, Toast.LENGTH_SHORT).show();
								this.m_userName = args[1];
								this.m_pass = "" + pass;
								result = true;
							}
							catch(Exception ex)
							{
								result = false;
							//	Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
							}
						}
						else result = false;
					}
					else result = false;
				}
			} else
				result = false;
		} catch (Exception ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
			result = false;
		} finally {
			return result;
		}
	}
	
	
	public boolean IsDeliveryMessage(Context context, String msg) {
		boolean result = true;
		try {
			if(msg.contains("*167*0#") == true){
				result = true;
			}
			else result = false;
		} catch (Exception ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
			result = false;
		} finally {
			return result;
		}
	}
	
	public boolean IsMedicalMessage(Context context, String msg) {
		boolean result = true;
		try {
			//Toast.makeText(context, "Message is : " + msg, Toast.LENGTH_SHORT).show();
			if (msg.charAt(0) == '*' && msg.charAt(msg.length() - 1) == '#') 
			{
				String s = msg.substring(1 , msg.length() - 1);
				String []args = new String[2];
				int state = 0 ;
				int lastI = 0 ;
				for(int i = 0 ; i < s.length() ; i++)
				{
					if( s.charAt(i) == '*')
					{
						if(state == 0)
						{
							args[state] = s.substring(lastI, i);
							state = 1;
							lastI = i + 1;
							break;
						}
					}
				}
				//Toast.makeText(context, "Args is : " + args[0], Toast.LENGTH_SHORT).show();
					args[state] = s.substring(lastI, s.length());
					//Toast.makeText(context, "Args 2 is : " + args[1], Toast.LENGTH_SHORT).show();
					if(args[0].trim().indexOf("165") != -1)
					{
						m_medicalMessage = args[1];
						result = true;
					}
					else result = false;
			} else
				result = false;
		} catch (Exception ex) {
			Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
			result = false;
		} finally {
			return result;
		}
	}
}
