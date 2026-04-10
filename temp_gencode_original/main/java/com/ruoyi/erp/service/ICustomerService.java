package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.Customer;

/**
 * 业务系统-客户Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface ICustomerService {
    /**
     * 查询业务系统-客户
     *
     * @param id 业务系统-客户主键
     * @return 业务系统-客户
     */
    Customer selectCustomerById(Long id);

    /**
     * 查询业务系统-客户列表
     *
     * @param customer 业务系统-客户
     * @return 业务系统-客户集合
     */
    List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增业务系统-客户
     *
     * @param customer 业务系统-客户
     * @return 结果
     */
    int insertCustomer(Customer customer);

    /**
     * 修改业务系统-客户
     *
     * @param customer 业务系统-客户
     * @return 结果
     */
    int updateCustomer(Customer customer);

    /**
     * 批量删除业务系统-客户
     *
     * @param ids 需要删除的业务系统-客户主键集合
     * @return 结果
     */
    int deleteCustomerByIds(Long[] ids);

    /**
     * 删除业务系统-客户信息
     *
     * @param id 业务系统-客户主键
     * @return 结果
     */
    int deleteCustomerById(Long id);

    /**
     * 新增业务系统-客户批量
     *
     * @param list 业务系统-客户
     * @return 结果
     */
    int insertCustomerBatch(List<Customer> list);
}
