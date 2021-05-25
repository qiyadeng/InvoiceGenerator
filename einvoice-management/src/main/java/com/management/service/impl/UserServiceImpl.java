package com.management.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.management.commons.utils.BeanUtils;
import com.management.commons.utils.PageInfo;
import com.management.mapper.UserMapper;
import com.management.mapper.UserRoleMapper;
import com.management.model.User;
import com.management.model.UserRole;
import com.management.model.vo.UserVo;
import com.management.service.IUserService;
import com.management.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Override
    public User selectByLoginName(String loginName) {
        User user = new User();
        user.setLoginName(loginName);
        return this.selectOne(user);
    }

    @Override
    public void insertByVo(UserVo userVo) {
        String merchantCode = UUID.randomUUID().toString().replaceAll("-", "");
        userVo.setMerchant_code(merchantCode);
        User user = BeanUtils.copy(userVo, User.class);
        user.setTax_dzdh(userVo.getTax_dz()+" "+userVo.getTax_dh());
        user.setTax_yhzh(userVo.getTax_yh()+" "+userVo.getTax_zh());
        user.setAppid(StringUtil.generateUUid());
        user.setApp_secret(StringUtil.generateUUid());
        user.setCreateTime(new Date());
        this.insert(user);
        Long id = user.getId();
        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();

        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public UserVo selectVoById(Long id) {
        return userMapper.selectUserVoById(id);
    }

    @Override
    public UserVo selectVoByMerchantCode(String merchantCode){
        return userMapper.selectVoByMerchantCode(merchantCode);
    }

    @Override
    public void updateByVo(UserVo userVo) {
        User user = BeanUtils.copy(userVo, User.class);
        user.setTax_dzdh(userVo.getTax_dz()+" "+userVo.getTax_dh());
        user.setTax_yhzh(userVo.getTax_yh()+" "+userVo.getTax_zh());
        this.updateSelectiveById(user);
        
        Long id = userVo.getId();
        List<UserRole> userRoles = userRoleMapper.selectByUserId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void updatePwdByUserId(Long userId, String md5Hex) {
        User user = new User();
        user.setPassword(md5Hex);
        this.updateSelectiveById(user);
    }

    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        Page<UserVo> page = new Page<UserVo>(pageInfo.getNowpage(), pageInfo.getSize());
        List<UserVo> list = userMapper.selectUserVoPage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
    }

    @Override
    public void deleteUserById(Long id) {
        this.deleteById(id);
        List<UserRole> userRoles = userRoleMapper.selectByUserId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }
    }

}