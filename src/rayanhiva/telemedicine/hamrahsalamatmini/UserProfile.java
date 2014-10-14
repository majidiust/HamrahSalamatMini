package rayanhiva.telemedicine.hamrahsalamatmini;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class UserProfile {
	public static final String TAG = "UserProfile";
	public static final String FILENAME = "profile.dat";
	
	public static String FirstName = "مرتضی";
	public static String LastName = "ثابت رفتار";
	public static String NationalID = "0010363351";
	public static String DeviceID = "1234567890";
	public static int BirthProvince = 1;
	public static int ResidenceProvince = 1;
	public static int EducationLevel = 3;
	public static Gender UserGender = Gender.G_Male;
	public static String Email = "morteza.sabetraftar@gmail.com";
	public static String BirthDate = "1368/03/17";
	public static String PhoneNumber = "09125300764";
	public static MaritalStatus Marital = MaritalStatus.MS_Single;
	public static String IMEI = "1837294562584560";
	
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
		} catch (FileNotFoundException fnfEx) {
			Log.d(TAG, fnfEx.getMessage());
		} catch (IOException ioEx) {
//			Log.d(TAG, ioEx.getMessage());
			Log.d(TAG, "IO Error");
			saveProfile(context);
		}
	}
	
	public static void saveProfile(Context context) {
		FileOutputStream fileOutputStream;
		DataOutputStream dataOutputStream;

		try {
			fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			dataOutputStream = new DataOutputStream(fileOutputStream);

			dataOutputStream.writeUTF(FirstName);
			dataOutputStream.writeUTF(LastName);
			dataOutputStream.writeUTF(NationalID);
			dataOutputStream.writeUTF(DeviceID);
			dataOutputStream.writeInt(BirthProvince);
			dataOutputStream.writeInt(ResidenceProvince);
			dataOutputStream.writeInt(EducationLevel);
			dataOutputStream.writeInt(UserGender.ordinal());
			dataOutputStream.writeUTF(Email);
			dataOutputStream.writeUTF(BirthDate);
			dataOutputStream.writeUTF(PhoneNumber);
			dataOutputStream.writeUTF(IMEI);
			dataOutputStream.writeInt(Marital.ordinal());
			
			dataOutputStream.close();
			fileOutputStream.close();
		} catch (Exception ex) {
			Log.d(TAG, ex.getMessage());
		}
	}
}
