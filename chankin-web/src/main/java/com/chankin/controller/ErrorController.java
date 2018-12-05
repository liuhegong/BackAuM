package com.chankin.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "错误处理", description = "统一处理系统未授权,服务内部等错误")
@Controller
@RequestMapping("error")
public class ErrorController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(ErrorController.class);

    @ApiOperation(value = "未授权", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "unAuthorization", method = RequestMethod.GET)
    public String unAuthorization() {
        return "system/unAuthorization";
    }

    @ApiOperation(value = "未找到页面", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "notFound", method = RequestMethod.GET)
    public String notFound() {
        return "system/404";
    }


    @ApiOperation(value = "内部错误", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "internalError", method = RequestMethod.GET)
    public String internalError() {
        return "system/500";
    }

}
