package com.chankin.controller;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.SysRole;
import com.chankin.service.SysRoleService;
import com.chankin.service.SystemService;
import com.chankin.util.ResponseCode;
import com.chankin.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "角色模块", description = "角色的新增,修改,删除等")
@RequestMapping("role")
@Controller
public class RoleController {
    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SystemService systemService;

    @ApiOperation(value = "跳转至角色模块", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("role:list")
    @RequestMapping(value = "role", method = RequestMethod.GET)
    public String role() {
        return "system/role";
    }

    @ApiOperation(value = "新增角色", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("role:insert")
    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insert(@ApiParam(value = "角色名称", required = true) @RequestParam String name,
                         @ApiParam(value = "说明", required = true) @RequestParam String description,
                         @ApiParam(value = "所有权限", required = true) @RequestParam String permissionIds,
                         @ApiParam(value = "是否可修改", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int isFinal) {
        boolean isExsitRoleName = sysRoleService.isExsitRoleName(name);
        if (isExsitRoleName) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        SysRole sysRole = new SysRole();
        sysRole.setName(name);
        sysRole.setDescription(description);
        sysRole.setIsFinal(isFinal);
        long id = sysRoleService.insertRole(sysRole, permissionIds);
        return Result.success(id);
    }

    @ApiOperation(value = "更新角色", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("role:update")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@ApiParam(value = "角色id", required = true) @RequestParam long id,
                         @ApiParam(value = "角色名称", required = true) @RequestParam String name,
                         @ApiParam(value = "说明", required = true) @RequestParam String description,
                         @ApiParam(value = "所有权限", required = true) @RequestParam String permissionIds) {
        System.out.println("id = [" + id + "], name = [" + name + "], description = [" + description + "], permissionIds = [" + permissionIds + "]");
        boolean isExsitRoleNameExcludeId = sysRoleService.isExsitRoleNameExcludeId(id, name);
        if (isExsitRoleNameExcludeId) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        SysRole sysRole = sysRoleService.selectById(id);
        if (sysRole == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysRole.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        sysRole.setId(id);
        sysRole.setName(name);
        sysRole.setDescription(description);
        sysRoleService.updateRole(sysRole, permissionIds);
        systemService.clearAuthorizationInfoByRoleId(id);
        return Result.success();
    }

    @ApiOperation(value = "删除角色", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("role:delete")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@ApiParam(value = "角色id", required = true) @RequestParam long id) {
        SysRole sysRole = sysRoleService.selectById(id);
        if (sysRole == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysRole.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        sysRole.setStatus(2);
        sysRoleService.deleteRole(sysRole);
        systemService.clearAuthorizationInfoByRoleId(id);
        return Result.success();
    }

    @ApiOperation(value = "角色列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("role:list")
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo list(@ApiParam(value = "当前页", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int page,
                         @ApiParam(value = "每页行数", defaultValue = "15", required = true) @RequestParam(defaultValue = "15") int rows) {
        PageInfo pageInfo = sysRoleService.selectPage(page, rows);
        return pageInfo;
    }

}
