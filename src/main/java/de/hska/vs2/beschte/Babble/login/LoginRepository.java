package de.hska.vs2.beschte.Babble.login;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {
	private final String USER_PREFIX = "user:";
	private final String USER_SUFFIX = ":user";
	private final String AUTH_PREFIX = "auth:";
	private final String AUTH_SUFFIX = ":auth";
	
	@Autowired
	private StringRedisTemplate template;

	public boolean auth(String username, String hashedPassword) {
		BoundHashOperations<String, String, String> userOps = template.boundHashOps(USER_PREFIX + username);
		return hashedPassword.equals(userOps.get("password"));
	}

	public String addAuth(String username, long timeout, TimeUnit tUnit) {
		String auth = UUID.randomUUID().toString();
		String userAuthKey = USER_PREFIX + username + AUTH_SUFFIX;
		template.boundHashOps(userAuthKey).put("auth", auth);
		template.expire(userAuthKey, timeout, tUnit);
		template.opsForValue().set(AUTH_PREFIX + auth + USER_SUFFIX, username, timeout, tUnit);
		return auth;
	}

	public void deleteAuth(String username) {
		String userAuthKey = USER_PREFIX + username + AUTH_SUFFIX;
		String auth = (String) template.boundHashOps(userAuthKey).get("auth");
		List<String> keysToDelete = Arrays.asList(userAuthKey, AUTH_PREFIX + auth + USER_SUFFIX);
		template.delete(keysToDelete);
	}
	
	public String getUsername(String auth) {
		return template.opsForValue().get(AUTH_PREFIX + auth + USER_SUFFIX);
	}
	
	public void refreshAuth(String username, long timeout, TimeUnit tUnit) {
		String userAuthKey = USER_PREFIX + username + AUTH_SUFFIX;
		template.expire(userAuthKey, timeout, tUnit);
	}
}
