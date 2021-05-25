package com.management.mapper;


import java.util.List;

import com.management.model.Organization;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * Organization 表数据库控制层接口
 *
 */
public interface OrganizationMapper extends AutoMapper<Organization> {

    List<Organization> selectByPIdNull();

    List<Organization> selectAllByPId(@Param("pId") Long pid);

    List<Organization> selectAll();

}