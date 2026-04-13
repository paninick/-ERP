package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.CustomerTemplateMaterialMapper;
import com.ruoyi.erp.domain.CustomerTemplateMaterial;
import com.ruoyi.erp.service.ICustomerTemplateMaterialService;

/**
 * 客户模板面料Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class CustomerTemplateMaterialServiceImpl implements ICustomerTemplateMaterialService {
    @Autowired
    private CustomerTemplateMaterialMapper customerTemplateMaterialMapper;

    /**
     * 查询客户模板面料
     *
     * @param id 客户模板面料主键
     * @return 客户模板面料
     */
    @Override
    public CustomerTemplateMaterial selectCustomerTemplateMaterialById(Long id) {
        return customerTemplateMaterialMapper.selectCustomerTemplateMaterialById(id);
    }

    /**
     * 查询客户模板面料列表
     *
     * @param customerTemplateMaterial 客户模板面料
     * @return 客户模板面料
     */
    @Override
    public List<CustomerTemplateMaterial> selectCustomerTemplateMaterialList(CustomerTemplateMaterial customerTemplateMaterial) {
        return customerTemplateMaterialMapper.selectCustomerTemplateMaterialList(customerTemplateMaterial);
    }

    /**
     * 新增客户模板面料
     *
     * @param customerTemplateMaterial 客户模板面料
     * @return 结果
     */
    @Override
    public int insertCustomerTemplateMaterial(CustomerTemplateMaterial customerTemplateMaterial) {
        customerTemplateMaterial.setCreateBy(SecurityUtils.getUserId().toString());
        customerTemplateMaterial.setCreateTime(DateUtils.getNowDate());
        return customerTemplateMaterialMapper.insertCustomerTemplateMaterial(customerTemplateMaterial);
    }

    /**
     * 修改客户模板面料
     *
     * @param customerTemplateMaterial 客户模板面料
     * @return 结果
     */
    @Override
    public int updateCustomerTemplateMaterial(CustomerTemplateMaterial customerTemplateMaterial) {
        customerTemplateMaterial.setUpdateTime(DateUtils.getNowDate());
        return customerTemplateMaterialMapper.updateCustomerTemplateMaterial(customerTemplateMaterial);
    }

    /**
     * 批量删除客户模板面料
     *
     * @param ids 需要删除的客户模板面料主键
     * @return 结果
     */
    @Override
    public int deleteCustomerTemplateMaterialByIds(Long[] ids) {
        return customerTemplateMaterialMapper.deleteCustomerTemplateMaterialByIds(ids);
    }

    /**
     * 删除客户模板面料信息
     *
     * @param id 客户模板面料主键
     * @return 结果
     */
    @Override
    public int deleteCustomerTemplateMaterialById(Long id) {
        return customerTemplateMaterialMapper.deleteCustomerTemplateMaterialById(id);
    }

    /**
     * 批量新增客户模板面料
     *
     * @param list 客户模板面料
     * @return 结果
     */
    @Override
    public int insertCustomerTemplateMaterialBatch(List<CustomerTemplateMaterial> list) {
        return customerTemplateMaterialMapper.insertCustomerTemplateMaterialBatch(list);
    }
}
