package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.CustomerTemplateSizeMapper;
import com.ruoyi.erp.domain.CustomerTemplateSize;
import com.ruoyi.erp.service.ICustomerTemplateSizeService;

/**
 * 客户尺寸细节Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class CustomerTemplateSizeServiceImpl implements ICustomerTemplateSizeService {
    private final CustomerTemplateSizeMapper customerTemplateSizeMapper;

    /**
     * 查询客户尺寸细节
     *
     * @param id 客户尺寸细节主键
     * @return 客户尺寸细节
     */
    @Override
    public CustomerTemplateSize selectCustomerTemplateSizeById(Long id) {
        return customerTemplateSizeMapper.selectCustomerTemplateSizeById(id);
    }

    /**
     * 查询客户尺寸细节列表
     *
     * @param customerTemplateSize 客户尺寸细节
     * @return 客户尺寸细节
     */
    @Override
    public List<CustomerTemplateSize> selectCustomerTemplateSizeList(CustomerTemplateSize customerTemplateSize) {
        return customerTemplateSizeMapper.selectCustomerTemplateSizeList(customerTemplateSize);
    }

    /**
     * 新增客户尺寸细节
     *
     * @param customerTemplateSize 客户尺寸细节
     * @return 结果
     */
    @Override
    public int insertCustomerTemplateSize(CustomerTemplateSize customerTemplateSize) {
        customerTemplateSize.setCreateBy(SecurityUtils.getUserIdStr());
        customerTemplateSize.setCreateTime(DateUtils.getNowDate());
        return customerTemplateSizeMapper.insertCustomerTemplateSize(customerTemplateSize);
    }

    /**
     * 修改客户尺寸细节
     *
     * @param customerTemplateSize 客户尺寸细节
     * @return 结果
     */
    @Override
    public int updateCustomerTemplateSize(CustomerTemplateSize customerTemplateSize) {
        customerTemplateSize.setUpdateTime(DateUtils.getNowDate());
        return customerTemplateSizeMapper.updateCustomerTemplateSize(customerTemplateSize);
    }

    /**
     * 批量删除客户尺寸细节
     *
     * @param ids 需要删除的客户尺寸细节主键
     * @return 结果
     */
    @Override
    public int deleteCustomerTemplateSizeByIds(Long[] ids) {
        return customerTemplateSizeMapper.deleteCustomerTemplateSizeByIds(ids);
    }

    /**
     * 删除客户尺寸细节信息
     *
     * @param id 客户尺寸细节主键
     * @return 结果
     */
    @Override
    public int deleteCustomerTemplateSizeById(Long id) {
        return customerTemplateSizeMapper.deleteCustomerTemplateSizeById(id);
    }

    /**
     * 批量新增客户尺寸细节
     *
     * @param list 客户尺寸细节
     * @return 结果
     */
    @Override
    public int insertCustomerTemplateSizeBatch(List<CustomerTemplateSize> list) {
        return customerTemplateSizeMapper.insertCustomerTemplateSizeBatch(list);
    }
}
