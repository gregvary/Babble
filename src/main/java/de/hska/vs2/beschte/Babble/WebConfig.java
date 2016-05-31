package de.hska.vs2.beschte.Babble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import de.hska.vs2.beschte.Babble.login.security.SimpleCookieInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private SimpleCookieInterceptor simpleCookieInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
		registry.addInterceptor(simpleCookieInterceptor).addPathPatterns("/**");
	}
}
