package br.com.devsystem.auth.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {
	private static final Logger log = LoggerFactory.getLogger(UrlUtil.class);
	
	public static String getApplicationUrl(HttpServletRequest request) {
		String appUrl = request.getRequestURL().toString();
		
	    log.info("UrlUtil.class > getApplicationUrl() : "+appUrl);
		return appUrl.replace(request.getServletPath(), "");
	}

}
