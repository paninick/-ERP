package com.ruoyi.erp.inspection.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.erp.inspection.domain.ErpInspectionBooking;
import com.ruoyi.erp.inspection.domain.ErpInspectionCompany;
import com.ruoyi.erp.inspection.mapper.ErpInspectionMapper;
import com.ruoyi.erp.inspection.service.IErpInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErpInspectionServiceImpl implements IErpInspectionService {

    @Autowired
    private ErpInspectionMapper inspectionMapper;

    @Override
    public ErpInspectionCompany selectCompanyById(Long id) {
        return inspectionMapper.selectCompanyById(id);
    }

    @Override
    public List<ErpInspectionCompany> selectCompanyList(ErpInspectionCompany company) {
        return inspectionMapper.selectCompanyList(company);
    }

    @Override
    public int insertCompany(ErpInspectionCompany company) {
        company.setCreateTime(DateUtils.getNowDate());
        return inspectionMapper.insertCompany(company);
    }

    @Override
    public int updateCompany(ErpInspectionCompany company) {
        company.setUpdateTime(DateUtils.getNowDate());
        return inspectionMapper.updateCompany(company);
    }

    @Override
    public int deleteCompanyByIds(Long[] ids) {
        int rows = 0;
        for (Long id : ids) rows += inspectionMapper.deleteCompanyById(id);
        return rows;
    }

    @Override
    public ErpInspectionBooking selectBookingById(Long id) {
        return inspectionMapper.selectBookingById(id);
    }

    @Override
    public List<ErpInspectionBooking> selectBookingList(ErpInspectionBooking booking) {
        return inspectionMapper.selectBookingList(booking);
    }

    @Override
    public int insertBooking(ErpInspectionBooking booking) {
        booking.setCreateTime(DateUtils.getNowDate());
        return inspectionMapper.insertBooking(booking);
    }

    @Override
    public int updateBooking(ErpInspectionBooking booking) {
        booking.setUpdateTime(DateUtils.getNowDate());
        return inspectionMapper.updateBooking(booking);
    }

    @Override
    public int deleteBookingByIds(Long[] ids) {
        int rows = 0;
        for (Long id : ids) rows += inspectionMapper.deleteBookingById(id);
        return rows;
    }
}
