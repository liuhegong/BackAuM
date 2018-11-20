package com.chankin.controller;

import com.chankin.dao.SysUserMapper;
import com.chankin.model.dto.LoginInfo;
import com.chankin.model.entity.SysUser;
import com.chankin.service.SysUserService;
import com.chankin.service.SystemService;
import com.chankin.system.security.geetest.GeetestConfig;
import com.chankin.system.security.geetest.GeetestLib;
import com.chankin.util.ResponseCode;
import com.chankin.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 系统功能模块
 */
@Api(value = "系统功能模块")
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "跳转至引导页", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "welcome", method = RequestMethod.GET)
    public String index() {
        return "system/index";
    }


    @ApiOperation(value = "登录", httpMethod = "POST", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestParam String loginName,
                        @RequestParam String password,
                        @RequestParam int platform,
                        HttpServletRequest request) throws Exception {
        //极限验证二次服务验证失败
        if (!verifyCaptcha(request)) {
            return Result.instance(ResponseCode.verify_captcha_error.getCode(), ResponseCode.verify_captcha_error.getMsg());
        }
        SysUser user = sysUserService.selectByLoginName(loginName);

        if (user == null) {
            return Result.instance(ResponseCode.unknown_account.getCode(), ResponseCode.unknown_account.getMsg());
        }
        //数据状态,1:正常,2:删除,3:禁用账号
        if (user.getStatus() == 3) {
            return Result.instance(ResponseCode.forbidden_account.getCode(), ResponseCode.forbidden_account.getMsg());
        }

        Subject subject = SecurityUtils.getSubject();
        //   subject.login(new UsernamePasswordToken(loginName,password));

        LoginInfo loginInfo = sysUserService.login(user, subject.getSession().getId(), platform);

        //将用户状态设置到session中
        //   subject.getSession().setAttribute("loginInfo",loginInfo);

        log.debug("登录成功");

        return Result.success(loginInfo);

    }

    /**
     * 极限验证
     */
    @ApiOperation(value = "极限验证", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public String captcha(HttpServletRequest request) {

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key());

/*        //从session中获取userid
        String userid = (String)request.getSession().getAttribute("userid");
        String ip = request.getRemoteAddr();

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("ip_address", ip); //传输用户请求验证时所携带的IP*/

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess();
        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        return gtSdk.getResponseStr();
    }

}
