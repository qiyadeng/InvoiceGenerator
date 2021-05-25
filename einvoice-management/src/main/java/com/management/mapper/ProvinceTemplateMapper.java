package com.management.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.management.model.ProvinceTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Effort
 * @description
 * @date 2020/9/25 5:23 下午
 */
public interface ProvinceTemplateMapper extends AutoMapper<ProvinceTemplate> {
    List<ProvinceTemplate> listProvinceTemplate(Pagination page, Map<String, Object> params);
    void deleteProvinceTemplate(@Param("id") Long id);
    ProvinceTemplate getTemplateByProvince(@Param("province") String province);
    List<ProvinceTemplate> listTemplate();
    ProvinceTemplate getTemplateByUuid(@Param("uuid") String uuid);
}
