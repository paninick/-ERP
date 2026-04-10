package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.CustomerTemplateMaterial;

/**
 * 客户模板面料Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface CustomerTemplateMaterialMapper {
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
     * 删除客户模板面料
     *
     * @param id 客户模板面料主键
     * @return 结果
     */
    int deleteCustomerTemplateMaterialById(Long id);

    /**
     * 批量删除客户模板面料
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCustomerTemplateMaterialByIds(Long[] ids);

    /**
     * 批量新增客户模板面料批量
     *
     * @param list 客户模板面料
     * @return 结果
     */
    int insertCustomerTemplateMaterialBatch(List<CustomerTemplateMaterial> list);
}
