package com.chankin.controller;

import com.chankin.model.dto.PageInfo;
import com.chankin.model.entity.SysOrganization;
import com.chankin.service.SysOrganizationService;
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

@Api(value = "组织机构模块", description = "组织的新增,删除,更新等")
@Controller
@RequestMapping("organization")
public class OrganizationController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private SysOrganizationService sysOrganizationService;

    @ApiOperation(value = "跳转至组织机构", httpMethod = "GET", produces = "text/html")
    @RequiresPermissions("organization:list")
    @RequestMapping(value = "organization", method = RequestMethod.GET)
    public String organization() {
        return "system/organization";
    }

    /*   @ApiOperation(value="新增机构",httpMethod = "POST",produces="application/json",response= Result.class)
       @RequiresPermissions("organization:list")
       @RequestMapping(value="insert",method = RequestMethod.POST)
       @ResponseBody
       public Result insert(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam String fullName,
                            @RequestParam long parentId,
                            @RequestParam(defaultValue="1") int isFinal){
           boolean isExistFullName = sysOrganizationService.isExistFullName(fullName);
           if(isExistFullName){
               return Result.error(ResponseCode.fullname_already_exist.getMsg());
           }
           SysOrganization organization = new SysOrganization();
           organization.setDescription(description);
           organization.setFullName(fullName);
           organization.setName(name);
           organization.setIsFinal(isFinal);
           organization.setParentId(parentId);
           long i = sysOrganizationService.insertOrganization(organization);
           return Result.success();
       }*/
    @ApiOperation(value = "新增机构", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("organization:insert")
    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insert(@ApiParam(value = "简称", required = true) @RequestParam String name,
                         @ApiParam(value = "说明", required = true) @RequestParam String description,
                         @ApiParam(value = "全称", required = true) @RequestParam String fullName,
                         @ApiParam(value = "上级组织id", required = true) @RequestParam long parentId,
                         @ApiParam(value = "是否可修改", required = true) @RequestParam(defaultValue = "1") int isFinal) {
        boolean isExistFullName = sysOrganizationService.isExistFullName(fullName);
        if (isExistFullName) {
            return Result.error(ResponseCode.fullname_already_exist.getMsg());
        }
        SysOrganization organization = new SysOrganization();
        organization.setFullName(fullName);
        organization.setName(name);
        organization.setDescription(description);
        organization.setParentId(parentId);
        organization.setIsFinal(isFinal);

        long i = sysOrganizationService.insertOrganization(organization);
        return Result.success();
    }


    @ApiOperation(value = "删除机构", httpMethod = "GET", produces = "application/json", response = Result.class)
    @RequiresPermissions("organization:delete")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result delete(@ApiParam(value = "组织id", required = true) @RequestParam long id) {
        SysOrganization sysOrganization = sysOrganizationService.selectOrganization(id);
        if (sysOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysOrganization.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        int i = sysOrganizationService.deleteOrganization(id);
        return Result.success();
    }


    @ApiOperation(value = "更新机构", httpMethod = "POST", produces = "application/json", response = Result.class)
    @RequiresPermissions("organization:update")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@ApiParam(value = "组织id", required = true) @RequestParam long id,
                         @ApiParam(value = "简称", required = true) @RequestParam String name,
                         @ApiParam(value = "全称", required = true) @RequestParam String fullName,
                         @ApiParam(value = "说明", required = true) @RequestParam String description,
                         @ApiParam(value = "上级组织id", required = true) @RequestParam long parentId) {
        SysOrganization sysOrganization = sysOrganizationService.selectOrganization(id);
        if (sysOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMsg());
        }
        if (sysOrganization.getIsFinal() == 2) {
            return Result.error(ResponseCode.can_not_edit.getMsg());
        }
        if (sysOrganization.getId() == parentId) {
            return Result.error("上级机构不能选择自己,请选择其他组织机构!");
        }
        boolean isExistFullNameExcludeId = sysOrganizationService.isExistFullNameExcludeId(id, fullName);
        if (isExistFullNameExcludeId) {
            return Result.error(ResponseCode.fullname_already_exist.getMsg());
        }
        SysOrganization organization = new SysOrganization();
        organization.setId(id);
        organization.setFullName(fullName);
        organization.setName(name);
        organization.setDescription(description);
        organization.setParentId(parentId);
        sysOrganizationService.updateOrganization(organization);
        return Result.success();
    }

    @ApiOperation(value = "查询机构列表", httpMethod = "GET", produces = "application/json", response = PageInfo.class)
    @RequiresPermissions("organization:list")
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public PageInfo list(@ApiParam(value = "起始页", required = true) @RequestParam(value = "page", defaultValue = "1") int page,
                         @ApiParam(value = "分页大小", required = true) @RequestParam(value = "row", defaultValue = "15") int row,
                         @ApiParam(value = "组织结构树id", required = true) @RequestParam(value = "id", defaultValue = "1") long id) {
        PageInfo pageInfo = sysOrganizationService.selectPage(page, row, id);
        return pageInfo;
    }

}