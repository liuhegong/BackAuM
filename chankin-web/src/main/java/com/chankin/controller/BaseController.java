package com.chankin.controller;


import com.chankin.service.SystemService;
import com.chankin.system.security.geetest.GeetestConfig;
import com.chankin.system.security.geetest.GeetestLib;
import com.chankin.util.ResponseCode;
import com.chankin.util.Result;
import com.chankin.util.StringUtil;
import com.google.gson.Gson;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private SystemService systemService;

    public boolean verifyCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {

        log.debug("===============begin verifyCaptcha===============");

        //创建极限验证实例，从数据库中获取极限私钥和公钥
        GeetestLib gtSdk = new GeetestLib(systemService.selectDataItemByKey("geetest_id", 1L),
                systemService.selectDataItemByKey("geetest_key", 1L),
                GeetestConfig.isnewfailback());
        //log.debug("Geetest_Id" + gtSdk.getCaptchaId() + "===============" + "Geetest_Key" + gtSdk.getPrivateKey());

        String challenge = httpServletRequest.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = httpServletRequest.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = httpServletRequest.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) httpServletRequest.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", "cheat"); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
        int gtResult = 0;
        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
        }
        if (gtResult == 1) {
            // 验证成功
            JSONObject data = new JSONObject();
            try {
                data.put("status", "success");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // 验证失败
            JSONObject data = new JSONObject();
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        log.debug("极限验证结果 : {}", gtResult);
        return gtResult == 1;

    }

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Exception exception) throws IOException, ServletException {
        log.error("Exception occur: \n {}", StringUtil.exceptionDetail(exception));
        if (request.getHeader("Accept").contains("applicaiton/json")) {
            log.debug("REQUEST");
            Result result = Result.error();
            if (exception instanceof IncorrectCredentialsException) {
                result = Result.instance(ResponseCode.password_incorrect.getCode(), ResponseCode.password_incorrect.getMsg());
            } else if (exception instanceof UnknownAccountException) {
                result = Result.instance(ResponseCode.unknown_account.getCode(), ResponseCode.unknown_account.getMsg());
                //未授权
            } else if (exception instanceof UnauthorizedException) {
                result = Result.instance(ResponseCode.unauthorized.getCode(), ResponseCode.unauthorized.getMsg());
                //未登录
            } else if (exception instanceof UnauthenticatedException) {
                result = Result.instance(ResponseCode.unauthenticated.getCode(), ResponseCode.unauthenticated.getMsg());
                //缺少参数
            } else if (exception instanceof MissingServletRequestParameterException) {
                result = Result.instance(ResponseCode.missing_parameter.getCode(), ResponseCode.missing_parameter.getMsg());
                //参数格式错误
            } else if ((exception instanceof MethodArgumentTypeMismatchException)) {
                result = Result.instance(ResponseCode.param_format_error.getCode(), ResponseCode.param_format_error.getMsg());
                //ip限制
            } else if (exception.getCause().getMessage().contains("system.exception.ForbiddenIpException")) {
                result = Result.instance(ResponseCode.forbidden_ip.getCode(), ResponseCode.forbidden_ip.getMsg());
                //其他错误
            }
            if (systemService.selectDataItemByKey("error_detail", 2L).equals("true")) {
                result.setData(StringUtil.exceptionDetail(exception));
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().append(new Gson().toJson(result));
            response.getWriter().flush();
            response.getWriter().close();

        } else {
            String basePath = systemService.selectDataItemByKey("basePath", 4L);
            String url = "/error/internalError";
            if (exception instanceof UnauthorizedException) {
                url = "/error/unAuthorization";
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.sendRedirect(basePath + url);
        }
    }
}
