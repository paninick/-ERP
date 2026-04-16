package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.ErpEmployee;

/**
 * 员工Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface ErpEmployeeMapper {
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
     * 删除员工
     *
     * @param id 员工主键
     * @return 结果
     */
    public int deleteErpEmployeeById(Long id);

    /**
     * 批量删除员工
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpEmployeeByIds(Long[] ids);

    /**
     * 根据员工编号查询
     *
     * @param employeeCode 员工编号
     * @return 员工信息
     */
    public ErpEmployee selectErpEmployeeByCode(String employeeCode);
}
