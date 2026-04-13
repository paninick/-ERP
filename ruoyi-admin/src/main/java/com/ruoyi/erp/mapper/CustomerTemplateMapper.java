package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.CustomerTemplate;

/**
 * 客户尺寸Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface CustomerTemplateMapper {
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
     * 删除客户尺寸
     *
     * @param id 客户尺寸主键
     * @return 结果
     */
    int deleteCustomerTemplateById(Long id);

    /**
     * 批量删除客户尺寸
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCustomerTemplateByIds(Long[] ids);

    /**
     * 批量新增客户尺寸批量
     *
     * @param list 客户尺寸
     * @return 结果
     */
    int insertCustomerTemplateBatch(List<CustomerTemplate> list);
}
