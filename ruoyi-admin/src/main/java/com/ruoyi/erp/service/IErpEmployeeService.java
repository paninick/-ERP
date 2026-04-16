package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.ErpEmployee;

/**
 * 员工Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface IErpEmployeeService {
    /**
     * 查询员工
     *
     * @param id 员工主键
     * @return 员工
     */
    public ErpEmployee selectErpEmployeeById(Long id);

    /**
     * 查询员工列表
     *
     * @param erpEmployee 员工
     * @return 员工集合
     */
    public List<ErpEmployee> selectErpEmployeeList(ErpEmployee erpEmployee);

    /**
     * 新增员工
     *
     * @param erpEmployee 员工
     * @return 结果
     */
    public int insertErpEmployee(ErpEmployee erpEmployee);

    /**
     * 修改员工
     *
     * @param erpEmployee 员工
     * @return 结果
     */
    public int updateErpEmployee(ErpEmployee erpEmployee);

    /**
     * 批量删除员工
     *
     * @param ids 需要删除的员工主键集合
     * @return 结果
     */
    public int deleteErpEmployeeByIds(Long[] ids);

    /**
     * 删除员工信息
     *
     * @param id 员工主键
     * @return 结果
     */
    public int deleteErpEmployeeById(Long id);
}
