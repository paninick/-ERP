package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.CustomerTemplateMapper;
import com.ruoyi.erp.domain.CustomerTemplate;
import com.ruoyi.erp.service.ICustomerTemplateService;

/**
 * 客户尺寸Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class CustomerTemplateServiceImpl implements ICustomerTemplateService {
    private final CustomerTemplateMapper customerTemplateMapper;

    /**
     * 查询客户尺寸
     *
     * @param id 客户尺寸主键
     * @return 客户尺寸
     */
    @Override
    public CustomerTemplate selectCustomerTemplateById(Long id) {
        return customerTemplateMapper.selectCustomerTemplateById(id);
    }

    /**
     * 查询客户尺寸列表
     *
     * @param customerTemplate 客户尺寸
     * @return 客户尺寸
     */
    @Override
    public List<CustomerTemplate> selectCustomerTemplateList(CustomerTemplate customerTemplate) {
        return customerTemplateMapper.selectCustomerTemplateList(customerTemplate);
    }

    /**
     * 新增客户尺寸
     *
     * @param customerTemplate 客户尺寸
     * @return 结果
     */
    @Override
    public int insertCustomerTemplate(CustomerTemplate customerTemplate) {
        customerTemplate.setCreateBy(SecurityUtils.getUserIdStr());
        customerTemplate.setCreateTime(DateUtils.getNowDate());
        return customerTemplateMapper.insertCustomerTemplate(customerTemplate);
    }

    /**
     * 修改客户尺寸
     *
     * @param customerTemplate 客户尺寸
     * @return 结果
     */
    @Override
    public int updateCustomerTemplate(CustomerTemplate customerTemplate) {
        customerTemplate.setUpdateTime(DateUtils.getNowDate());
        return customerTemplateMapper.updateCustomerTemplate(customerTemplate);
    }

    /**
     * 批量删除客户尺寸
     *
     * @param ids 需要删除的客户尺寸主键
     * @return 结果
     */
    @Override
    public int deleteCustomerTemplateByIds(Long[] ids) {
        return customerTemplateMapper.deleteCustomerTemplateByIds(ids);
    }

    /**
     * 删除客户尺寸信息
     *
     * @param id 客户尺寸主键
     * @return 结果
     */
    @Override
    public int deleteCustomerTemplateById(Long id) {
        return customerTemplateMapper.deleteCustomerTemplateById(id);
    }

    /**
     * 批量新增客户尺寸
     *
     * @param list 客户尺寸
     * @return 结果
     */
    @Override
    public int insertCustomerTemplateBatch(List<CustomerTemplate> list) {
        return customerTemplateMapper.insertCustomerTemplateBatch(list);
    }
}
