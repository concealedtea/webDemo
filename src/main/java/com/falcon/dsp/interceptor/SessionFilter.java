package com.falcon.dsp.interceptor;

import com.falcon.dsp.common.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 用于检查用户是否登录了系统的过滤器<br>
 *
 * @author wei.wang
 * @from 2016-04-15
 * @since V1.0
 */
public class SessionFilter implements Filter {

    /**
     * 要检查的 session 的名称
     */
    private String sessionKey = "USER_INFO_SESSION";
    /**
     * 需要排除（不拦截）的URL的正则表达式
     */
    private Pattern exceptUrlPattern = null;
    /**
     * 检查不通过时，转发的URL
     */
    private String redirectUrl;
    private boolean debug;

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        String excepUrlRegex = cfg.getInitParameter("excepUrlRegex");
        if (StringUtil.hasTextWithoutWhiteSpace(excepUrlRegex)) {
            exceptUrlPattern = Pattern.compile(excepUrlRegex);
        } else {
            exceptUrlPattern = Pattern.compile("/resources/*");
        }
        redirectUrl = cfg.getInitParameter("redirectUrl");
        debug = Boolean.parseBoolean(cfg.getInitParameter("debug"));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        chain.doFilter(req, res);
       /* HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String servletPath = request.getServletPath();

        // 如果请求的路径与forwardUrl相同，或请求的路径是排除的URL时，则直接放行
        if (servletPath.equals(redirectUrl) || exceptUrlPattern.matcher(servletPath).matches()) {
            chain.doFilter(req, res);
            return;
        }
        Object sessionObj = request.getSession(true).getAttribute(sessionKey);
        // 如果Session为空，则跳转到指定页面
        if (sessionObj == null) {

            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + defaultIfEmpty(redirectUrl, "/"));
        } else {
            chain.doFilter(req, res);
        }*/
        return;
    }

    private String defaultIfEmpty(String redirectUrl, String defaultStr) {
        if (StringUtil.hasTextWithoutWhiteSpace(redirectUrl)) {
            return redirectUrl;
        } else {
            return defaultStr;
        }
    }

    @Override
    public void destroy() {

    }

}
