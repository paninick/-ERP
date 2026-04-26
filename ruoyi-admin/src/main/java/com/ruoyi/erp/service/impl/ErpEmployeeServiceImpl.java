package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.erp.mapper.ErpEmployeeMapper;
import com.ruoyi.erp.orgunit.mapper.OrgUnitMapper;
import com.ruoyi.erp.orgunit.domain.OrgUnit;
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

    @Autowired
    private OrgUnitMapper orgUnitMapper;

    @Override
    public ErpEmployee selectErpEmployeeById(Long id) {
        return erpEmployeeMapper.selectErpEmployeeById(id);
    }

    @Override
    public List<ErpEmployee> selectErpEmployeeList(ErpEmployee erpEmployee) {
        return erpEmployeeMapper.selectErpEmployeeList(erpEmployee);
    }

    private void validateOrgFields(ErpEmployee employee) {
        Long orgUnitId = employee.getOrgUnitId();
        if (orgUnitId != null) {
            OrgUnit orgUnit = orgUnitMapper.selectOrgUnitById(orgUnitId);
            if (orgUnit == null) {
                throw new ServiceException("所属组织节点不存在");
            }
        }
        Long workshopId = employee.getWorkshopId();
        if (workshopId != null) {
            OrgUnit orgUnit = orgUnitMapper.selectOrgUnitById(workshopId);
            if (orgUnit == null) {
                throw new ServiceException("所属车间不存在");
            }
            if (!"WORKSHOP".equals(orgUnit.getOrgType())) {
                throw new ServiceException("所选车间组织类型不正确，当前类型：" + orgUnit.getOrgType());
            }
        }
        Long teamId = employee.getTeamId();
        if (teamId != null) {
            OrgUnit orgUnit = orgUnitMapper.selectOrgUnitById(teamId);
            if (orgUnit == null) {
                throw new ServiceException("所属班组不存在");
            }
            if (!"TEAM".equals(orgUnit.getOrgType())) {
                throw new ServiceException("所选班组组织类型不正确，当前类型：" + orgUnit.getOrgType());
            }
        }
        Long stationId = employee.getStationId();
        if (stationId != null) {
            OrgUnit orgUnit = orgUnitMapper.selectOrgUnitById(stationId);
            if (orgUnit == null) {
                throw new ServiceException("所属工位不存在");
            }
            if (!"STATION".equals(orgUnit.getOrgType())) {
                throw new ServiceException("所选工位组织类型不正确，当前类型：" + orgUnit.getOrgType());
            }
        }
    }

    @Override
    public int insertErpEmployee(ErpEmployee erpEmployee) {
        validateOrgFields(erpEmployee);
        erpEmployee.setCreateBy(SecurityUtils.getUserId().toString());
        erpEmployee.setCreateTime(DateUtils.getNowDate());
        return erpEmployeeMapper.insertErpEmployee(erpEmployee);
    }

    @Override
    public int updateErpEmployee(ErpEmployee erpEmployee) {
        validateOrgFields(erpEmployee);
        erpEmployee.setUpdateTime(DateUtils.getNowDate());
        return erpEmployeeMapper.updateErpEmployee(erpEmployee);
    }

    @Override
    public int deleteErpEmployeeByIds(Long[] ids) {
        return erpEmployeeMapper.deleteErpEmployeeByIds(ids);
    }

    @Override
    public int deleteErpEmployeeById(Long id) {
        return erpEmployeeMapper.deleteErpEmployeeById(id);
    }
}
