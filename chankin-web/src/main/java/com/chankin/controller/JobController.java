package com.chankin.controller;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.SysRoleOrganization;
import com.chankin.service.SysRoleOrganizationService;
import com.chankin.util.ResponseCode;
import com.chankin.util.Result;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.chankin.util.Result.error;

@Api(value = "职位模块", description = "职位的新增,删除,更新等")
@Controller
@RequestMapping("job")
public class JobController extends BaseController {
    @Autowired
    private SysRoleOrganizationService roleOrganizationService;

    @ApiOperation(value = "跳转至职位模块", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("job:list")
    @RequestMapping(value = "job", method = RequestMethod.GET)
    public String job() {
        return "system/job";
    }

    @ApiOperation(value = "新增职位", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("job:list")
    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insertJob(@ApiParam(value = "角色id", required = true) @RequestParam long roleId,
                            @ApiParam(value = "组织id", required = true) @RequestParam long organizationId,
                            @ApiParam(value = "上级组织id", required = true) @RequestParam long parentId,
                            @ApiParam(value = "职位简称", required = true) @RequestParam String name,
                            @ApiParam(value = "说明", required = true) @RequestParam String description,
                            @ApiParam(value = "全称", required = true) @RequestParam String fullName,
                            @ApiParam(value = "是否可修改", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int isFinal) {
        boolean isExistName = roleOrganizationService.isExistName(name, parentId);
        if (isExistName) {
            return error(ResponseCode.name_already_exist.getMsg());
        }
        SysRoleOrganization roleOrganization = new SysRoleOrganization();
        roleOrganization.setName(name);
        roleOrganization.setSysOrganizationId(organizationId);
        roleOrganization.setParentId(parentId);
        roleOrganization.setDescription(description);
        roleOrganization.setIsFinal(isFinal);
        roleOrganization.setFullName(fullName);
        roleOrganization.setSysRoleId(roleId);
        long id = roleOrganizationService.insertRoleOrganization(roleOrganization);
        return Result.success(id);
    }

    @ApiOperation(value = "更新职位", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("job:update")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result insertJob(@ApiParam(value = "角色id", required = true) @RequestParam long roleId,
                            @ApiParam(value = "职位id", required = true) @RequestParam long id,
                            @ApiParam(value = "组织id", required = true) @RequestParam long organizationId,
                            @ApiParam(value = "上级职位id", required = true) @RequestParam long parentId,
                            @ApiParam(value = "职位简称", required = true) @RequestParam String name,
                            @ApiParam(value = "说明", required = true) @RequestParam String description,
                            @ApiParam(value = "全称", required = true) @RequestParam String fullName,
                            @ApiParam(value = "是否可修改", required = true) @RequestParam(defaultValue = "1") int isFinal) {
        SysRoleOrganization roleOrganization = roleOrganizationService.selectRoleOrganizationById(id);
        if (roleOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (roleOrganization.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        boolean isExistNameExcludeId = roleOrganizationService.isExistNameExcludeId(id, name, parentId);
        if (isExistNameExcludeId) {
            return Result.error(ResponseCode.name_already_exist.getMsg());
        }
        roleOrganization.setName(name);
        roleOrganization.setDescription(description);
        roleOrganization.setFullName(fullName);
        roleOrganization.setSysRoleId(roleId);
        roleOrganization.setSysOrganizationId(organizationId);
        roleOrganization.setParentId(parentId);
        roleOrganizationService.updateRoleOrganization(roleOrganization);
        return Result.success();
    }

    @ApiOperation(value = "删除职位", httpMethod = "GET", produces = "application/json", response = Result.class)
    @ResponseBody
    @RequiresPermissions("job:delete")
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result delete(@ApiParam(value = "职位id", required = true) @RequestParam long id) {
        SysRoleOrganization roleOrganization = roleOrganizationService.selectRoleOrganizationById(id);
        if (roleOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (roleOrganization.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        roleOrganization.setStatus(2);
        roleOrganizationService.updateRoleOrganization(roleOrganization);
        return Result.success();
    }

    @ApiOperation(value = "查询职位列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @ResponseBody
    @RequiresPermissions("job:list")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo list(@ApiParam(value = "起始页", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") int page,
                         @ApiParam(value = "分页大小", defaultValue = "15", required = true) @RequestParam(defaultValue = "15") int rows,
                         @ApiParam(value = "职位结构树id", defaultValue = "1", required = true) @RequestParam(defaultValue = "1") long id) {
        PageInfo pageInfo = roleOrganizationService.selectPage(page, rows, id);
        return pageInfo;
    }
}

