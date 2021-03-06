package Model;

import Links.UserClassLink;
import Links.UserDeleteLink;
import Links.UserSuspendLink;

public class User {
	
	private String email;
	private String password;
	private boolean isManager = false;
	private boolean isSuspended = false;
	private String dateJoined;
	private UserClassLink userClassHyperLink;
	private UserSuspendLink userSuspendHyperLink;
	private UserDeleteLink userDeleteHyperLink;

	public User(String e, String p) {
		email = e;
		password = p;
	}
	
	public User(String e, String p, boolean m) {
		this(e, p);
		isManager = m;
		
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getIsManager() {
		return isManager;
	}

	public void toggleManager() {
		isManager = !isManager;
	}
	public void toggleSuspended() {
		isSuspended = !isSuspended;
	}

	public boolean getSuspended() {
		return isSuspended;
	}

	public void setDateJoined(String s) {
		dateJoined = s;
	}

	public void setIsSuspended(boolean b) {
		isSuspended = b;
	}

	public String getDateJoined() {
		return dateJoined;
	}

	public UserClassLink getUserClassHyperLink() { return userClassHyperLink; }

	public void setUserClassHyperLink(UserClassLink cl) { userClassHyperLink = cl; }

	public UserSuspendLink getUserSuspendHyperLink() { return userSuspendHyperLink; }

	public void setUserSuspendHyperLink(UserSuspendLink sl) { userSuspendHyperLink = sl; }

	public UserDeleteLink getUserDeleteHyperLink() { return userDeleteHyperLink; }

	public void setUserDeleteHyperLink(UserDeleteLink dl) { userDeleteHyperLink = dl; }
}
