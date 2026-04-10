package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.CustomerTemplate;

/**
 * 客户尺寸Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface ICustomerTemplateService {
    /**
     * 查询客户尺寸
     *
     * @param id 客户尺寸主键
     * @return 客户尺寸
     */
    CustomerTemplate selectCustomerTemplateById(Long id);

    /**
     * 查询客户尺寸列表
     *
     * @param customerTemplate 客户尺寸
     * @return 客户尺寸集合
     */
    List<CustomerTemplate> selectCustomerTemplateList(CustomerTemplate customerTemplate);

    /**
     * 新增客户尺寸
     *
     * @param customerTemplate 客户尺寸
     * @return 结果
     */
    int insertCustomerTemplate(CustomerTemplate customerTemplate);

    /**
     * 修改客户尺寸
     *
     * @param customerTemplate 客户尺寸
     * @return 结果
     */
    int updateCustomerTemplate(CustomerTemplate customerTemplate);

    /**
     * 批量删除客户尺寸
     *
     * @param ids 需要删除的客户尺寸主键集合
     * @return 结果
     */
    int deleteCustomerTemplateByIds(Long[] ids);

    /**
     * 删除客户尺寸信息
     *
     * @param id 客户尺寸主键
     * @return 结果
     */
    int deleteCustomerTemplateById(Long id);

    /**
     * 新增客户尺寸批量
     *
     * @param list 客户尺寸
     * @return 结果
     */
    int insertCustomerTemplateBatch(List<CustomerTemplate> list);
}
