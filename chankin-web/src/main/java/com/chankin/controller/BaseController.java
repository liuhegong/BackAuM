package com.chankin.controller;


import com.chankin.service.SystemService;
import com.chankin.system.security.geetest.GeetestConfig;
import com.chankin.system.security.geetest.GeetestLib;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private SystemService systemService;

    public boolean verifyCaptcha(HttpServletRequest httpServletRequest) throws Exception {

        log.debug("===============begin verifyCaptcha===============");
        int gtResult = 0;

        //创建极限验证实例，从数据库中获取极限私钥和公钥
        GeetestLib gtSdk = new GeetestLib(systemService.selectDataItemByKey("geetst_id", 1L), systemService.selectDataItemByKey("geetest_key", 1L));
        log.debug("Geetest_Id" + gtSdk.getCaptchaId() + "===============" + "Geetest_Key" + gtSdk.getPrivateKey());

        String challenge = httpServletRequest.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = httpServletRequest.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = httpServletRequest.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) httpServletRequest.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
//        HashMap<String, String> param =  (HashMap<String, String>) httpServletRequest.getSession().getAttribute("param");

        log.debug("极限验证服务器状态 : {}", gt_server_status_code);
        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
        }

        log.debug("极限验证结果 : {}", gtResult);

        return gtResult == 1;


    }


}
