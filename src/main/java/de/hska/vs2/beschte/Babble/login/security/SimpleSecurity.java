package de.hska.vs2.beschte.Babble.login.security;

import org.springframework.core.NamedThreadLocal;

public class SimpleSecurity {
	private static final ThreadLocal<UserInfo> user = new NamedThreadLocal<UserInfo>("microblog-id");

	private static class UserInfo {
		String username;
		String auth;
	}

	public static void setUser(String username, String auth) {
		UserInfo userInfo = new UserInfo();
		userInfo.username = username;
		userInfo.auth = auth;
		user.set(userInfo);
	}

	public static boolean isUserSignedIn(String username) {
		UserInfo userInfo = user.get();
		return userInfo != null && userInfo.username.equals(username);
	}
	
	public static boolean isSignedIn() {
		UserInfo userInfo = user.get();
		return userInfo != null;
	}

	public static String getUsername() {
		UserInfo userInfo = user.get();
		return userInfo.username;
	}

	public static String getAuth() {
		UserInfo userInfo = user.get();
		return userInfo.auth;
	}
	
	public static void logout() {
		user.set(null);
	}
}
