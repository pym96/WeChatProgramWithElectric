package com.waibaoservice.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author DJSv
 * Date create 14:55 2023/2/24
 * Modified By DJS
 **/

@WebFilter(filterName = "userFilter", urlPatterns = "/login/*")
public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        final String url = req.getRequestURL().toString();
        if (url.contains("/userLogin") || url.contains("/userRegister")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            res.sendError(404, "not found");
        }
    }
}
