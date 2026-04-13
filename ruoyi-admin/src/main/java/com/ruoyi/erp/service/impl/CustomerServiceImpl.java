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

    /**
     * 导入客户数据
     * Excel列映射：第1列跳过，第2列客户名称，第3列客户简称，第4列贸易国别，第5列跳过，第6列业务员
     * 客户编码自动生成
     *
     * @param list 客户数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    @Override
    public String importCustomer(List<Customer> list, boolean updateSupport) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("导入客户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        int maxNo = getMaxCustomerNo();

        for (Customer customer : list) {
            try {
                if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、客户名称不能为空");
                    continue;
                }

                maxNo++;
                String customerNo = String.valueOf(maxNo);

                Customer exist = customerMapper.selectCustomerByNo(customerNo);
                if (exist == null) {
                    customer.setCustomerNo(customerNo);
                    customer.setCreateBy(SecurityUtils.getUserId().toString());
                    customer.setCreateTime(DateUtils.getNowDate());
                    customer.setNationality(convertCountryCode(customer.getNationality()));
                    this.insertCustomer(customer);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、客户 ").append(customer.getCustomerName()).append(" 导入成功，编码：").append(customerNo);
                } else if (updateSupport) {
                    customer.setCustomerNo(customerNo);
                    customer.setUpdateTime(DateUtils.getNowDate());
                    this.updateCustomer(customer);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、客户 ").append(customer.getCustomerName()).append(" 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、客户 ").append(customer.getCustomerName()).append(" 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、客户 " + customer.getCustomerName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉！导入失败，共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    private int getMaxCustomerNo() {
        Integer maxNo = customerMapper.selectMaxCustomerNo();
        return maxNo == null ? 0 : maxNo;
    }

    private String convertCountryCode(String countryName) {
        if (countryName == null || countryName.trim().isEmpty()) {
            return "";
        }
        switch (countryName.trim()) {
            case "中国": return "CN";
            case "美国": return "US";
            case "日本": return "JP";
            case "韩国": return "KR";
            case "英国": return "GB";
            case "德国": return "DE";
            case "法国": return "FR";
            case "意大利": return "IT";
            case "加拿大": return "CA";
            case "澳大利亚": return "AU";
            case "俄罗斯": return "RU";
            case "印度": return "IN";
            case "巴西": return "BR";
            case "墨西哥": return "MX";
            case "西班牙": return "ES";
            case "荷兰": return "NL";
            case "瑞士": return "CH";
            case "新加坡": return "SG";
            case "马来西亚": return "MY";
            case "泰国": return "TH";
            case "越南": return "VN";
            case "印度尼西亚": return "ID";
            case "菲律宾": return "PH";
            case "台湾": return "TW";
            case "香港": return "HK";
            case "澳门": return "MO";
            default: return countryName.trim();
        }
    }
}
