package de.hska.vs2.beschte.Babble.login.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import de.hska.vs2.beschte.Babble.login.LoginRepository;

@Component
public class SimpleCookieInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
    private LoginRepository repository;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		Cookie[] cookies = req.getCookies();
		if (!ObjectUtils.isEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				if ("auth".equals(cookie.getName())) {
					String auth = cookie.getValue();
					if (auth != null) {
						String username = repository.getUsername(auth);
						if (username != null) {
							SimpleSecurity.setUser(username, auth);
							repository.refreshAuth(username, SecurityUtil.TIMEOUT.getSeconds(), SecurityUtil.TIME_UNIT);
						} else
							SimpleSecurity.logout();
					}
				}
			}
		}
		return true;
	}
}
