package com.chankin.controller;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.SysUser;
import com.chankin.service.SysUserService;
import com.chankin.service.SystemService;
import com.chankin.util.ResponseCode;
import com.chankin.util.Result;
import com.chankin.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Api(value = "用户管理模块", description = "用户的增删改查")
@Controller
@RequestMapping("user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SystemService systemService;

    @ApiOperation(value = "跳转至用户管理模块", httpMethod = "GET", produces = "text/html")
    @RequestMapping(value = "user", method = RequestMethod.GET)
    @RequiresPermissions("user:list")
    public String user() {
        return "system/user";
    }

    @ApiOperation(value = "新增用户", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("user:insert")
    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result isert(@ApiParam(value = "登录用户名") @RequestParam String loginName,
                        @ApiParam(value = "用户中文名") @RequestParam String zhName,
                        @ApiParam(value = "用户英文名", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String enName,
                        @ApiParam(value = "性别") @RequestParam int sex,
                        @ApiParam(value = "出生日期", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String birth,
                        @ApiParam(value = "邮件", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String email,
                        @ApiParam(value = "手机号码", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String phone,
                        @ApiParam(value = "地址", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String address,
                        @ApiParam(value = "密码") @RequestParam String password,
                        @ApiParam(value = "是否可修改", defaultValue = "1") @RequestParam(defaultValue = "1") Integer isFinal,
                        @ApiParam(value = "职位id") @RequestParam String jobIds,
                        @ApiParam(value = "权限id") @RequestParam String permissionIds) {
        boolean isExistLoginName = sysUserService.isExistLoginName(loginName);
        if (isExistLoginName) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        if ((!StringUtils.hasText(password)) && password.length() < 6) {
            return Result.error("请设置密码长度大于等于6");
        }
        SysUser user = new SysUser();
        user.setLoginName(loginName);
        user.setZhName(zhName);
        user.setEnName(enName);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setBirth(birth);
        user.setIsFinal(isFinal);
        String salt = UUID.randomUUID().toString().replace("-", "");
        user.setPasswordSalt(salt);
        user.setPassword(StringUtil.createPassword(password, salt, 2));
        long id = sysUserService.insertUser(user, jobIds, permissionIds);
        return Result.success(id);
    }

    @ApiOperation(value = "删除用户", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("user:delete")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result delete(@ApiParam(value = "用户id") @RequestParam long id) {
        SysUser user = sysUserService.selectById(id);
        if (user == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (user.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        sysUserService.deleteById(id);
        systemService.forceLogout(id);
        return Result.success();

    }

    @ApiOperation(value = "更新用户", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("user:update")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result update(@ApiParam(value = "用户id") @RequestParam long id,
                         @ApiParam(value = "登录用户名") @RequestParam String loginName,
                         @ApiParam(value = "用户中文名") @RequestParam String zhName,
                         @ApiParam(value = "用户英文名", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String enName,
                         @ApiParam(value = "性别") @RequestParam int sex,
                         @ApiParam(value = "出生日期", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String birth,
                         @ApiParam(value = "邮件", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String email,
                         @ApiParam(value = "手机号码", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String phone,
                         @ApiParam(value = "地址", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String address,
                         @ApiParam(value = "职位id") @RequestParam String jobIds,
                         @ApiParam(value = "权限id") @RequestParam String permissionIds) {
        boolean isExistLoginNameExcludeId = sysUserService.isExistLoginNameExcludeId(id, loginName);
        if (isExistLoginNameExcludeId) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysUser user = sysUserService.selectById(id);
        if (user.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        user.setLoginName(loginName);
        user.setZhName(zhName);
        user.setEnName(enName);
        user.setSex(sex);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setBirth(birth);
        sysUserService.updateUser(user, jobIds, permissionIds);
        systemService.clearAuthorizationInfoCacheByUserId(id);
        return Result.success();
    }

    /*
     *
     *  查询用户列表
     *  page 起始页码
     *  rows 分页大小
     *  sort 排序字段
     *  order 排序规则
     *  pageInfo 封装用户信息
     *
     * */
    @ApiOperation(value = "查询用户列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("user:list")
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo list(@ApiParam(value = "当前页", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int page,
                         @ApiParam(value = "分页大小", defaultValue = "30", required = true) @RequestParam(defaultValue = "30") int rows,
                         @ApiParam(value = "排序字段", defaultValue = "zhName", required = true) @RequestParam(defaultValue = "zhName") String sort,
                         @ApiParam(value = "排序规则", defaultValue = "asc", required = true) @RequestParam(defaultValue = "asc") String order,
                         @ApiParam(value = "登录用户名", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String loginName,
                         @ApiParam(value = "用户中文名", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String zhName,
                         @ApiParam(value = "邮件", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String email,
                         @ApiParam(value = "手机号码", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String phone,
                         @ApiParam(value = "地址", defaultValue = "", required = false) @RequestParam(defaultValue = "", required = false) String address) {
        //查询 某个页面的user信息
        PageInfo pageInfo = sysUserService.selectPage(page, rows, StringUtil.camelToUnderline(sort), order, loginName, zhName, email, phone, address);
        //封装在pageInfo里面返回
        return pageInfo;
    }

    /*
     *  检查密码要求,符合的话,根据id查找用户 更新加盐密码
     * */
    @ApiOperation(value = "更新密码", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("user:updatePassword")
    public Result updatePassword(@ApiParam(value = "用户id") @RequestParam long id,
                                 @ApiParam(value = "新密码") @RequestParam String newPassword,
                                 @ApiParam(value = "重复新密码") @RequestParam String repeatNewPassword) {
        if (!StringUtils.hasText(newPassword) && newPassword.length() < 6) {
            return Result.error("请设置密码长度大于等于6");
        }
        if (!newPassword.equals(repeatNewPassword)) {
            return Result.error("两次输入的密码不一致");
        }
        SysUser sysUser = sysUserService.selectById(id);
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        sysUser.setPasswordSalt(salt);
        sysUser.setPassword(StringUtil.createPassword(newPassword, salt, 2));
        sysUserService.updateUser(sysUser);
        return Result.success();
    }


    @ApiOperation(value = "禁用账户", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("user:forbidden")
    @ResponseBody
    @RequestMapping(value = "forbiddenUser", method = RequestMethod.GET)
    public Result forbiddenUser(@ApiParam(value = "用户id") @RequestParam long id) {
        SysUser sysUser = sysUserService.selectById(id);
        if (sysUser.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        sysUser.setStatus(3);
        sysUserService.updateUser(sysUser);
        systemService.forceLogout(sysUser.getId());
        return Result.success();
    }


    /*
     *  通过id获取user,然后update,清除缓存
     * */
    @ApiOperation(value = "启用账户", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("user:enable")
    @ResponseBody
    @RequestMapping(value = "enableUser", method = RequestMethod.GET)
    public Result enableUser(@ApiParam(value = "用户id") @RequestParam long id) {
        SysUser sysUser = sysUserService.selectById(id);
        if (sysUser.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        sysUser.setStatus(1);
        sysUserService.updateUser(sysUser);
        systemService.clearAuthorizationInfoCacheByUserId(sysUser.getId());
        return Result.success();
    }

}
