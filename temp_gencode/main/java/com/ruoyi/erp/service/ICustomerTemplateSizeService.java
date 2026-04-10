package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.CustomerTemplateSize;

/**
 * 客户尺寸细节Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface ICustomerTemplateSizeService {
    /**
     * 查询客户尺寸细节
     *
     * @param id 客户尺寸细节主键
     * @return 客户尺寸细节
     */
    CustomerTemplateSize selectCustomerTemplateSizeById(Long id);

    /**
     * 查询客户尺寸细节列表
     *
     * @param customerTemplateSize 客户尺寸细节
     * @return 客户尺寸细节集合
     */
    List<CustomerTemplateSize> selectCustomerTemplateSizeList(CustomerTemplateSize customerTemplateSize);

    /**
     * 新增客户尺寸细节
     *
     * @param customerTemplateSize 客户尺寸细节
     * @return 结果
     */
    int insertCustomerTemplateSize(CustomerTemplateSize customerTemplateSize);

    /**
     * 修改客户尺寸细节
     *
     * @param customerTemplateSize 客户尺寸细节
     * @return 结果
     */
    int updateCustomerTemplateSize(CustomerTemplateSize customerTemplateSize);

    /**
     * 批量删除客户尺寸细节
     *
     * @param ids 需要删除的客户尺寸细节主键集合
     * @return 结果
     */
    int deleteCustomerTemplateSizeByIds(Long[] ids);

    /**
     * 删除客户尺寸细节信息
     *
     * @param id 客户尺寸细节主键
     * @return 结果
     */
    int deleteCustomerTemplateSizeById(Long id);

    /**
     * 新增客户尺寸细节批量
     *
     * @param list 客户尺寸细节
     * @return 结果
     */
    int insertCustomerTemplateSizeBatch(List<CustomerTemplateSize> list);
}
