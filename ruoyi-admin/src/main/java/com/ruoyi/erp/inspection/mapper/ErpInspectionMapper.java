package com.ruoyi.erp.inspection.mapper;

import java.util.List;
import com.ruoyi.erp.inspection.domain.ErpInspectionCompany;
import com.ruoyi.erp.inspection.domain.ErpInspectionBooking;

public interface ErpInspectionMapper {
    ErpInspectionCompany selectCompanyById(Long id);
    List<ErpInspectionCompany> selectCompanyList(ErpInspectionCompany company);
    int insertCompany(ErpInspectionCompany company);
    int updateCompany(ErpInspectionCompany company);
    int deleteCompanyById(Long id);
    int deleteCompanyByIds(Long[] ids);

    ErpInspectionBooking selectBookingById(Long id);
    List<ErpInspectionBooking> selectBookingList(ErpInspectionBooking booking);
    int insertBooking(ErpInspectionBooking booking);
    int updateBooking(ErpInspectionBooking booking);
    int deleteBookingById(Long id);
    int deleteBookingByIds(Long[] ids);
}
