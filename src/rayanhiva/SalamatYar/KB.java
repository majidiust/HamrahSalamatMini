/**
 * 
 */
package rayanhiva.SalamatYar;

/**
 * @author hiva
 * 
 */
public class KB {
	public static final int REQUEST_ENABLE_BT = 0;
	public static final int REQUEST_DISCOVERABLE_BT = 0;
	// Message types sent from the BluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	// Key names received from the BluetoothService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	public static String DeviceSerial = "00:19:5D:EE:AF:2E";
	// Intent request codes
	public static final int REQUEST_CONNECT_DEVICE = 1;
	public static final boolean D = true;
	public static String SMSServer = "6363";
	public static String []AdminNumbers = new String[]{"09197343303","9197343303","+989197343303"};;
	public static String ForgotPasswordMessage = "*140*3#";
	public static String RegisterMessage = "*140#";
	public static String SPO2Message = "*147*7*4*{0}#";// "*147*7*4*{0}*{1}#";
	public static String HRMessage = "*147*7*3*{0}#";
	public static String BIOMessage = "*147*6*{0}#";
	public static String GlOMessage = "*147*7*1*{0}#";
	public static String BPMessage = "*147*7*2*{0}*{1}#";
	public static String MSG1 = "با توجه به اطلاعات دریافتی از شما در حال حاضر علائم حیاتی در محدوده طبیعی می باشد.";
	public static String MSG2 = "با توجه به اطلاعات دریافتی از شما لازم است در طی 6 ساعت آینده مجددا اطلاعات جدیدی از خود را در اختیار ما قرار دهید.";
	public static String MSG3 = "با توجه به اطلاعات دریافتی از شما لازم است در اسرع وقت به پزشک خود مراجعه نمایید.";
	public static String MSG4 = "با توجه به اطلاعات دریافتی از شما چنانچه طبق دستور پزشک خود دارویی مصرف می نماید لطفا به ما اطلاع دهید.";
	public static String MSG5 = "اطلاعات ارائه شده از طرف شما کافی نمی باشد.";
	public static String MSG6 = "آیا از صحت اطلاعات ارسالی خود مطمئن هستید ؟";
	public static String MSG7 = "آیا از صحت تنظیمات دستگاه اندازه گیری خود اطمینان دارید؟";
	public static boolean IsLoggedIn = false;

}
