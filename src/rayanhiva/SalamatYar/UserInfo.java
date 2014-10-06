/**
 * 
 */
package rayanhiva.SalamatYar;

/**
 * @author Administrator
 *
 */
public class UserInfo {
	private String m_MIME;
	private String m_SIMID;
	private String m_password;
	private String m_PhoneNumber;
	public UserInfo(){
		m_MIME = m_SIMID = m_password = m_PhoneNumber = "";
	}

	public UserInfo(String MIME, String SIMID){
		setMIME(MIME);
		setSIMID(SIMID);
	}
	/**
	 * @param m_MIME the m_MIME to set
	 */
	public void setMIME(String m_MIME) {
		this.m_MIME = m_MIME;
	}
	/**
	 * @return the m_MIME
	 */
	public String getMIME() {
		return m_MIME;
	}
	/**
	 * @param m_SIMID the m_SIMID to set
	 */
	public void setSIMID(String m_SIMID) {
		this.m_SIMID = m_SIMID;
	}
	/**
	 * @return the m_SIMID
	 */
	public String getSIMID() {
		return m_SIMID;
	}
	/**
	 * @param m_password the m_password to set
	 */
	public void setPassword(String m_password) {
		this.m_password = m_password;
	}
	/**
	 * @return the m_password
	 */
	public String getPassword() {
		return m_password;
	}
	/**
	 * @param m_PhoneNumber the m_PhoneNumber to set
	 */
	public void setPhoneNumber(String m_PhoneNumber) {
		this.m_PhoneNumber = m_PhoneNumber;
	}
	/**
	 * @return the m_PhoneNumber
	 */
	public String getPhoneNumber() {
		return m_PhoneNumber;
	}
	
}
