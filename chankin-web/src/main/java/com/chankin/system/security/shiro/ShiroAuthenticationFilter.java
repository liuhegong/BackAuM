package com.chankin.system.security.shiro;

import com.chankin.util.ResponseCode;
import com.chankin.util.Result;
import com.google.gson.Gson;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *  登录过滤器
 *  需要请求头为application/json格式
 *
 * */
public class ShiroAuthenticationFilter extends PassThruAuthenticationFilter {
    private static Logger log = LoggerFactory.getLogger(ShiroAuthenticationFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            log.debug("isloginURL: \n {}", getPathWithinApplication(request));
            return true;
        } else {
            saveRequest(request);
            log.debug("not login URL: \n {}", getPathWithinApplication(request));
            System.out.println("==============获取头部信息============");
            System.out.println("            " + ((HttpServletRequest) request).getHeader("Accept"));
            if (((HttpServletRequest) request).getHeader("Accept").contains("application/json")) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                Result result = new Result(ResponseCode.unauthenticated.getCode(), ResponseCode.unauthenticated.getMsg());
                response.getWriter().append(new Gson().toJson(result));
                response.getWriter().flush();
                response.getWriter().close();
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                ((HttpServletResponse) response).sendRedirect("/BackAuM");
            }
            return false;
        }
    }
}

