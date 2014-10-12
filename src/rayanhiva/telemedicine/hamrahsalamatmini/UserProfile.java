package rayanhiva.telemedicine.hamrahsalamatmini;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.util.Log;

public class UserProfile {
	public static final String TAG = "UserProfile";
	public static final String FILENAME = "profile.dat";
	
	public static String FirstName = "";
	public static String LastName = "";
	public static String NationalID = "0123456789";
	public static String DeviceID = "";
	public static int BirthProvince = 0;
	public static int ResidenceProvince = 0;
	public static int EducationLevel = 0;
	public static Gender UserGender = Gender.G_Male;
	public static String Email = "";
	public static String BirthDate = "";
	public static String PhoneNumber = "";
	public static MaritalStatus Marital = MaritalStatus.MS_Single;
	public static String IMEI = "";
	
	public static void loadProfile(Context context) {
		FileInputStream fileInputStream;
		DataInputStream dataInputStream;

		try {
			fileInputStream = context.openFileInput(FILENAME);
			dataInputStream = new DataInputStream(fileInputStream);

			FirstName = dataInputStream.readUTF();
			LastName = dataInputStream.readUTF();
			NationalID = dataInputStream.readUTF();
			DeviceID = dataInputStream.readUTF();
			BirthProvince = dataInputStream.readInt();
			ResidenceProvince = dataInputStream.readInt();
			EducationLevel = dataInputStream.readInt();
			UserGender = Gender.values()[dataInputStream.readInt()];
			Email = dataInputStream.readUTF();
			BirthDate = dataInputStream.readUTF();
			PhoneNumber = dataInputStream.readUTF();
			IMEI = dataInputStream.readUTF();
			Marital = MaritalStatus.values()[dataInputStream.readInt()];
			
			dataInputStream.close();
			fileInputStream.close();
		} catch (Exception ex) {
			Log.d(TAG, ex.getMessage());
		}
	}
	
	public static void saveProfile(Context context) {
		FileOutputStream fileOutputStream;
		DataOutputStream dataOutputStream;

		try {
			fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			dataOutputStream = new DataOutputStream(fileOutputStream);

			dataOutputStream.writeChars(FirstName);
			dataOutputStream.writeChars(LastName);
			dataOutputStream.writeChars(NationalID);
			dataOutputStream.writeChars(DeviceID);
			dataOutputStream.writeInt(BirthProvince);
			dataOutputStream.writeInt(ResidenceProvince);
			dataOutputStream.writeInt(EducationLevel);
			dataOutputStream.writeInt(UserGender.ordinal());
			dataOutputStream.writeChars(Email);
			dataOutputStream.writeChars(BirthDate);
			dataOutputStream.writeChars(PhoneNumber);
			dataOutputStream.writeChars(IMEI);
			dataOutputStream.writeInt(Marital.ordinal());
			
			dataOutputStream.close();
			fileOutputStream.close();
		} catch (Exception ex) {
			Log.d(TAG, ex.getMessage());
		}
	}
}
