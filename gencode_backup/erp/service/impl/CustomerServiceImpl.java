package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.CustomerMapper;
import com.ruoyi.erp.domain.Customer;
import com.ruoyi.erp.service.ICustomerService;

/**
 * 业务系统-客户Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询业务系统-客户
     *
     * @param id 业务系统-客户主键
     * @return 业务系统-客户
     */
    @Override
    public Customer selectCustomerById(Long id) {
        return customerMapper.selectCustomerById(id);
    }

    /**
     * 查询业务系统-客户列表
     *
     * @param customer 业务系统-客户
     * @return 业务系统-客户
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer) {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 新增业务系统-客户
     *
     * @param customer 业务系统-客户
     * @return 结果
     */
    @Override
    public int insertCustomer(Customer customer) {
        customer.setCreateBy(SecurityUtils.getUserId().toString());
        customer.setCreateTime(DateUtils.getNowDate());
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 修改业务系统-客户
     *
     * @param customer 业务系统-客户
     * @return 结果
     */
    @Override
    public int updateCustomer(Customer customer) {
        customer.setUpdateTime(DateUtils.getNowDate());
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 批量删除业务系统-客户
     *
     * @param ids 需要删除的业务系统-客户主键
     * @return 结果
     */
    @Override
    public int deleteCustomerByIds(Long[] ids) {
        return customerMapper.deleteCustomerByIds(ids);
    }

    /**
     * 删除业务系统-客户信息
     *
     * @param id 业务系统-客户主键
     * @return 结果
     */
    @Override
    public int deleteCustomerById(Long id) {
        return customerMapper.deleteCustomerById(id);
    }

    /**
     * 批量新增业务系统-客户
     *
     * @param list 业务系统-客户
     * @return 结果
     */
    @Override
    public int insertCustomerBatch(List<Customer> list) {
        return customerMapper.insertCustomerBatch(list);
    }
}
