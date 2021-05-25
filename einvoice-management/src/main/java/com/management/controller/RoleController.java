package com.management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.management.commons.base.BaseController;
import com.management.commons.utils.PageInfo;
import com.management.model.Role;
import com.management.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description：权限管理
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    /**
     * 权限管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "admin/role";
    }

    /**
     * 权限列表
     *
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
    @ResponseBody
    public Object dataGrid(Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        Map<String, Object> condition = new HashMap<String, Object>();
        pageInfo.setCondition(condition);

        roleService.selectDataGrid(pageInfo);
        return pageInfo;
    }

    /**
     * 权限树
     *
     * @return
     */
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public Object tree() {
        return roleService.selectTree();
    }

    /**
     * 添加权限页
     *
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "admin/roleAdd";
    }

    /**
     * 添加权限
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object add(Role role) {
        roleService.insert(role);
        return renderSuccess("添加成功！");
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object delete(Long id) {
        roleService.deleteById(id);
        return renderSuccess("删除成功！");
    }

    /**
     * 编辑权限页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, Long id) {
        Role role = roleService.selectById(id);
        model.addAttribute("role", role);
        return "admin/roleEdit";
    }

    /**
     * 删除权限
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/edit",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object edit(Role role) {
        roleService.updateSelectiveById(role);
        return renderSuccess("编辑成功！");
    }

    /**
     * 授权页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/grantPage")
    public String grantPage(Long id, Model model) {
        model.addAttribute("id", id);
        return "admin/roleGrant";
    }

    /**
     * 授权页面页面根据角色查询资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/findResourceIdListByRoleId")
    @ResponseBody
    public Object findResourceByRoleId(Long id) {
        List<Long> resources = roleService.selectResourceIdListByRoleId(id);

        return renderSuccess(resources);
    }

    /**
     * 授权
     *
     * @param id
     * @param resourceIds
     * @return
     */
    @RequestMapping(value = "/grant",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object grant(Long id, String resourceIds) {
        roleService.updateRoleResource(id, resourceIds);
        return renderSuccess("授权成功！");
    }

}
