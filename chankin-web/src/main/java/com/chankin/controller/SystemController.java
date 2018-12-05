package com.chankin.controller;

import com.chankin.dao.SysUserMapper;
import com.chankin.model.dto.LoginInfo;
import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.SysDataGroup;
import com.chankin.model.entity.SysDataItem;
import com.chankin.model.entity.SysIpForbidden;
import com.chankin.model.entity.SysUser;
import com.chankin.service.SysUserService;
import com.chankin.service.SystemService;
import com.chankin.system.security.geetest.GeetestConfig;
import com.chankin.system.security.geetest.GeetestLib;
import com.chankin.util.ResponseCode;
import com.chankin.util.Result;
import com.chankin.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * 系统功能模块
 */
@Api(value = "系统功能模块", description = "系统功能的控制")
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
    public Result login(@ApiParam(value = "登录用户名", required = true) @RequestParam String loginName,
                        @ApiParam(value = "密码", required = true) @RequestParam String password,
                        @ApiParam(value = "登录平台", required = true) @RequestParam int platform,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        //极限验证二次服务验证失败
        if (!verifyCaptcha(request, response)) {
            return Result.instance(ResponseCode.verify_captcha_error.getCode(), ResponseCode.verify_captcha_error.getMsg());
        }
        System.out.println("================================" + loginName);
        System.out.println("================================" + password);
        SysUser user = sysUserService.selectUserByLoginName(loginName);

        if (user == null) {
            return Result.instance(ResponseCode.unknown_account.getCode(), ResponseCode.unknown_account.getMsg());
        }
        //数据状态,1:正常,2:删除,3:禁用账号
        if (user.getStatus() == 3) {
            return Result.instance(ResponseCode.forbidden_account.getCode(), ResponseCode.forbidden_account.getMsg());
        }

        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(loginName, password));

        LoginInfo loginInfo = sysUserService.login(user, subject.getSession().getId(), platform);

        //将用户状态设置到session中
        subject.getSession().setAttribute("loginInfo", loginInfo);

        log.debug("登录成功");

        return Result.success(loginInfo);

    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "退出", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        LoginInfo loginInfo = (LoginInfo) subject.getSession().getAttribute("loginInfo");
        systemService.logout(loginInfo.getLoginName(), subject);
        return Result.success();
    }


    /**
     * 极限验证
     */
    @ApiOperation(value = "极限验证", httpMethod = "GET", produces = "text/html")
    @ResponseBody
    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public String captcha(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", "cheat"); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);
        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        return gtSdk.getResponseStr();

    }

    @ApiOperation(value = "跳转用户状态模块", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("user:loginStatu:list")
    @RequestMapping(value = "online", method = RequestMethod.GET)
    public String online() {
        return "system/online";
    }

    /*
     *  page 起始页码
     *  rows 分页大小
     * */
    @ApiOperation(value = "在线用户列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("user:loginStatu:list")
    @ResponseBody
    @RequestMapping(value = "online/list", method = RequestMethod.GET)
    public PageInfo onlinelist(@ApiParam(value = "起始页", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int page,
                               @ApiParam(value = "分页大小", defaultValue = "30", required = true) @RequestParam(defaultValue = "30") int rows) {
        PageInfo pageInfo = systemService.selectLogStatus(page, rows);
        return pageInfo;
    }

    /*
     *  强制用户下线
     * */
    @ApiOperation(value = "强制用户下线", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("user:loginout")
    @ResponseBody
    @RequestMapping(value = "forceLogout", method = RequestMethod.GET)
    public Result forceLogout(@ApiParam(value = "用户id", required = true) @RequestParam String userIds) {
        System.out.println("userIds = [" + userIds + "]");
        String[] ids = userIds.split(",");
        for (String id : ids) {
            systemService.forceLogout(Long.parseLong(id));
        }
        return Result.success();
    }

    /*
     *  日志页面
     * */
    @ApiOperation(value = "跳转值日志页面", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("log:list")
    @RequestMapping(value = "log")
    public String log() {
        return "system/log";
    }

    /*
     *  order 排序规则
     *   sort 排序字段
     * */
    @ApiOperation(value = "查询日志页面", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("log:list")
    @RequestMapping(value = "log/list")
    @ResponseBody
    public PageInfo loglist(@ApiParam(value = "当前页", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int page,
                            @ApiParam(value = "分页大小", defaultValue = "30", required = true) @RequestParam(defaultValue = "30") int rows,
                            @ApiParam(value = "排序字段", defaultValue = "id", required = true) @RequestParam(defaultValue = "id") String sort,
                            @ApiParam(value = "排序规则", defaultValue = "desc", required = true) @RequestParam(defaultValue = "desc") String order,
                            @ApiParam(value = "控制器方法", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String method,
                            @ApiParam(value = "url日志", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String url,
                            @ApiParam(value = "参数日志", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String param,
                            @ApiParam(value = "响应结果", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String result) {
        PageInfo pageInfo = systemService.selectLog(page, rows, StringUtil.camelToUnderline(sort), order, method, url, param, result);
        return pageInfo;
    }


    @ApiOperation(value = "新增字典组", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:group:insert")
    @ResponseBody
    @RequestMapping(value = "dataGroup/insert", method = RequestMethod.POST)
    public Result dateGroupInsert(@ApiParam(value = "名称", required = true) @RequestParam String name,
                                  @ApiParam(value = "字典组说明", required = true) @RequestParam String description) {
        boolean isExistName = systemService.isExistDataGroupName(name);
        if (isExistName) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysDataGroup sysDataGroup = new SysDataGroup();
        sysDataGroup.setName(name);
        sysDataGroup.setDescription(description);
        sysDataGroup.setParentId(0L);
        sysDataGroup.setIsFinal(2);
        Long id = systemService.insertSysDataGroup(sysDataGroup);
        return Result.success(id);
    }

    @ApiOperation(value = "字典组列表", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:group:list")
    @ResponseBody
    @RequestMapping(value = "dataGroup/list", method = RequestMethod.GET)
    public List<SysDataGroup> dataGroupList() {
        List<SysDataGroup> list = systemService.selectDataGroupList();
        return list;
    }

    @ApiOperation(value = "跳转至字典页面", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "data", method = RequestMethod.GET)
    public String data() {
        return "system/data";
    }

    /*
     * 新增数据字典
     **/
    @ApiOperation(value = "新增字典", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:insert")
    @ResponseBody
    @RequestMapping(value = "data/insert", method = RequestMethod.POST)
    public Result dateInsert(@ApiParam(value = "键名称", required = true) @RequestParam String key,
                             @ApiParam(value = "值", required = true) @RequestParam String value,
                             @ApiParam(value = "说明", required = true) @RequestParam String description,
                             @ApiParam(value = "字典组id", required = true) @RequestParam long groupId) {
        boolean isExistName = systemService.isExistDataItemKeyName(key, groupId);

        if (isExistName) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysDataItem sysDataItem = new SysDataItem();
        sysDataItem.setKeyName(key);
        sysDataItem.setKeyValue(value);
        sysDataItem.setDescription(description);
        sysDataItem.setSysDataGroupId(groupId);
        sysDataItem.setIsFinal(2);
        Long id = systemService.insertSysDataItem(sysDataItem);
        return Result.success(id);
    }

    @ApiOperation(value = "删除字典", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:delete")
    @ResponseBody
    @RequestMapping(value = "data/delete", method = RequestMethod.GET)
    public Result dataDelete(@ApiParam(value = "字典id", required = true) @RequestParam Long id) {
        systemService.deleteDataItemById(id);
        return Result.success();
    }


    @ApiOperation(value = "更新字典", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("data:update")
    @ResponseBody
    @RequestMapping(value = "data/update", method = RequestMethod.POST)
    public Result dataUpdate(@ApiParam(value = "字典id", required = true) @RequestParam Long id,
                             @ApiParam(value = "键", required = true) @RequestParam String key,
                             @ApiParam(value = "值", required = true) @RequestParam String value,
                             @ApiParam(value = "说明", required = true) @RequestParam String description,
                             @ApiParam(value = "字典组id", required = true) @RequestParam long groupId) {

        SysDataItem sysDataItem = systemService.selectDataItemById(id);
        if (sysDataItem == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysDataItem.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        boolean isExistDataItemNameExcludeId = systemService.isExistDataItemNameExcludeId(id, key, groupId);
        if (isExistDataItemNameExcludeId) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        sysDataItem.setKeyName(key);
        sysDataItem.setKeyValue(value);
        sysDataItem.setSysDataGroupId(groupId);
        sysDataItem.setDescription(description);
        systemService.updateDateItem(sysDataItem);

        return Result.success();
    }

    @ApiOperation(value = "查询字典详情", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "data/select", method = RequestMethod.GET)
    @RequiresPermissions("data:select")
    public Result dataSelect() {
        return Result.success();
    }

    @ApiOperation(value = "字典列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("data:list")
    @ResponseBody
    @RequestMapping(value = "data/list", method = RequestMethod.GET)
    public PageInfo dataList(@ApiParam(value = "当前页", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int page,
                             @ApiParam(value = "分页大小", defaultValue = "30", required = true) @RequestParam(defaultValue = "30") int rows) {
        PageInfo pageInfo = systemService.selectDataItemPage(page, rows);
        return pageInfo;
    }

    @ApiOperation(value = "跳转至ip模块", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "ip", method = RequestMethod.GET)
    @RequiresPermissions("ip:list")
    public String ip() {
        return "system/ip";
    }

    @ApiOperation(value = "插入ip", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @RequiresPermissions("ip:insert")
    public Result ipInsert(@ApiParam(value = "ip", required = true) @RequestParam String ip,
                           @ApiParam(value = "到期时间", required = true) @RequestParam String expireTime,
                           @ApiParam(value = "说明", required = true) @RequestParam String description) throws ParseException {
        boolean isExistIp = systemService.isExistIp(ip);
        if (isExistIp) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysIpForbidden sysIpForbidden = new SysIpForbidden();
        sysIpForbidden.setIp(ip);
        sysIpForbidden.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
        sysIpForbidden.setDescription(description);
        Long id = systemService.insertIp(sysIpForbidden);
        return Result.success(id);
    }

    @ApiOperation(value = "删除ip", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("ip:delete")
    @ResponseBody
    @RequestMapping(value = "ip/delete", method = RequestMethod.GET)
    public Result ipDelete(@RequestParam long id) {
        systemService.deleteIp(id);
        return Result.success();
    }

    @ApiOperation(value = "更新ip", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("ip:update")
    @ResponseBody
    @RequestMapping(value = "ip/update", method = RequestMethod.POST)
    public Result ipUpdate(@ApiParam(value = "ip值id", required = true) @RequestParam long id,
                           @ApiParam(value = "ip", required = true) @RequestParam String ip,
                           @ApiParam(value = "到期时间", required = true) @RequestParam String expireTime,
                           @ApiParam(value = "说明", required = true) @RequestParam String description) throws ParseException {
        boolean isExistIpExcludeId = systemService.isExistIpExcludeId(ip, id);
        if (isExistIpExcludeId) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysIpForbidden sysIpForbidden = new SysIpForbidden();
        sysIpForbidden.setId(id);
        sysIpForbidden.setIp(ip);
        sysIpForbidden.setExpireTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
        sysIpForbidden.setDescription(description);
        systemService.updateIp(sysIpForbidden);
        return Result.success();
    }

    @ApiOperation(value = "查询ip列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("ip:list")
    @ResponseBody
    @RequestMapping(value = "ip/list", method = RequestMethod.GET)
    public PageInfo ipSelect(@ApiParam(value = "当前页", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int page,
                             @ApiParam(value = "分页大小", defaultValue = "30", required = true) @RequestParam(defaultValue = "30") int rows) {
        PageInfo pageInfo = systemService.selectIp(page, rows);
        return pageInfo;
    }

    @ApiOperation(value = "ip拦截开关", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "ip/intecept", method = RequestMethod.GET)
    public Result intercept(@ApiParam(value = "ip拦截开关", required = true) @RequestParam boolean open) {
        if (open == true) {
            systemService.openIpIntercept();
        }
        if (open == false) {
            systemService.closeIpIntercept();
        }
        return Result.success();
    }

    @ApiOperation(value = "ip拦截开关状态", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "ip/intercept/status")
    public Result interceptStatus() {
        boolean ip_forbidden = systemService.selectIPForbiddenStatus();
        return Result.success(ip_forbidden);
    }


}
