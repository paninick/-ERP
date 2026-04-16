package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ErpEmployeeMapper;
import com.ruoyi.erp.domain.ErpEmployee;
import com.ruoyi.erp.service.IErpEmployeeService;

/**
 * 员工Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class ErpEmployeeServiceImpl implements IErpEmployeeService {
    @Autowired
    private ErpEmployeeMapper erpEmployeeMapper;

    /**
     * 查询员工
     *
     * @param id 员工主键
     * @return 员工
     */
    @Override
    public ErpEmployee selectErpEmployeeById(Long id) {
        return erpEmployeeMapper.selectErpEmployeeById(id);
    }

    /**
     * 查询员工列表
     *
     * @param erpEmployee 员工
     * @return 员工
     */
    @Override
    public List<ErpEmployee> selectErpEmployeeList(ErpEmployee erpEmployee) {
        return erpEmployeeMapper.selectErpEmployeeList(erpEmployee);
    }

    /**
     * 新增员工
     *
     * @param erpEmployee 员工
     * @return 结果
     */
    @Override
    public int insertErpEmployee(ErpEmployee erpEmployee) {
        erpEmployee.setCreateBy(SecurityUtils.getUserId().toString());
        erpEmployee.setCreateTime(DateUtils.getNowDate());
        return erpEmployeeMapper.insertErpEmployee(erpEmployee);
    }

    /**
     * 修改员工
     *
     * @param erpEmployee 员工
     * @return 结果
     */
    @Override
    public int updateErpEmployee(ErpEmployee erpEmployee) {
        erpEmployee.setUpdateTime(DateUtils.getNowDate());
        return erpEmployeeMapper.updateErpEmployee(erpEmployee);
    }

    /**
     * 批量删除员工
     *
     * @param ids 需要删除的员工主键
     * @return 结果
     */
    @Override
    public int deleteErpEmployeeByIds(Long[] ids) {
        return erpEmployeeMapper.deleteErpEmployeeByIds(ids);
    }

    /**
     * 删除员工信息
     *
     * @param id 员工主键
     * @return 结果
     */
    @Override
    public int deleteErpEmployeeById(Long id) {
        return erpEmployeeMapper.deleteErpEmployeeById(id);
    }
}
