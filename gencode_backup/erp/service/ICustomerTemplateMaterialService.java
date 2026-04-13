package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.CustomerTemplateMaterial;

/**
 * 客户模板面料Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ICustomerTemplateMaterialService {
    /**
     * 查询客户模板面料
     *
     * @param id 客户模板面料主键
     * @return 客户模板面料
     */
    CustomerTemplateMaterial selectCustomerTemplateMaterialById(Long id);

    /**
     * 查询客户模板面料列表
     *
     * @param customerTemplateMaterial 客户模板面料
     * @return 客户模板面料集合
     */
    List<CustomerTemplateMaterial> selectCustomerTemplateMaterialList(CustomerTemplateMaterial customerTemplateMaterial);

    /**
     * 新增客户模板面料
     *
     * @param customerTemplateMaterial 客户模板面料
     * @return 结果
     */
    int insertCustomerTemplateMaterial(CustomerTemplateMaterial customerTemplateMaterial);

    /**
     * 修改客户模板面料
     *
     * @param customerTemplateMaterial 客户模板面料
     * @return 结果
     */
    int updateCustomerTemplateMaterial(CustomerTemplateMaterial customerTemplateMaterial);

    /**
     * 批量删除客户模板面料
     *
     * @param ids 需要删除的客户模板面料主键集合
     * @return 结果
     */
    int deleteCustomerTemplateMaterialByIds(Long[] ids);

    /**
     * 删除客户模板面料信息
     *
     * @param id 客户模板面料主键
     * @return 结果
     */
    int deleteCustomerTemplateMaterialById(Long id);

    /**
     * 新增客户模板面料批量
     *
     * @param list 客户模板面料
     * @return 结果
     */
    int insertCustomerTemplateMaterialBatch(List<CustomerTemplateMaterial> list);
}
