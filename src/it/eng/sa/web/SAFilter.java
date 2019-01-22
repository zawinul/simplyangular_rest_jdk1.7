package it.eng.sa.web;

import it.eng.sa.util.Log;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.NDC;


public class SAFilter implements Filter {

	public void destroy() {
	}

	private static int cnt=0;
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest r = (HttpServletRequest) req;
		String uri = r.getRequestURI();

		boolean needLog = ((uri.indexOf("/rest")>=0) || (uri.indexOf("/download")>=0) || (uri.indexOf("/upload")>=0));
		if (!needLog) {
			filterChain.doFilter(req, resp);
			return;
		}
		if (needLog) {
			String s="WEB_"+(cnt++);
			NDC.push(s);
			Log.web.debug("!+ "+r.getMethod()+" "+r.getRequestURI());
			Log.web.debug("QueryString="+r.getQueryString());
	
			Throwable t = null;
			try {
				filterChain.doFilter(req, resp);
				resp.setCharacterEncoding("UTF-8");
			} 
			catch(Exception e) {
				t=e;
				throw new RuntimeException(e);
			}
			finally {
				if (t==null)
					Log.web.debug("!- "+r.getMethod()+" "+r.getRequestURI()+" -> OK");
				else 
					Log.web.debug("!- "+r.getMethod()+" "+r.getRequestURI()+" -> ERROR class="+t.getClass().getName()+" msg="+t.getMessage());
				
				NDC.pop();
			}
		}
	}
	
	public void init(FilterConfig arg0) throws ServletException {
	}

}
