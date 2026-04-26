package com.ruoyi.erp.inspection.service;

import java.util.List;
import com.ruoyi.erp.inspection.domain.ErpInspectionCompany;
import com.ruoyi.erp.inspection.domain.ErpInspectionBooking;

public interface IErpInspectionService {
    ErpInspectionCompany selectCompanyById(Long id);
    List<ErpInspectionCompany> selectCompanyList(ErpInspectionCompany company);
    int insertCompany(ErpInspectionCompany company);
    int updateCompany(ErpInspectionCompany company);
    int deleteCompanyByIds(Long[] ids);

    ErpInspectionBooking selectBookingById(Long id);
    List<ErpInspectionBooking> selectBookingList(ErpInspectionBooking booking);
    int insertBooking(ErpInspectionBooking booking);
    int updateBooking(ErpInspectionBooking booking);
    int deleteBookingByIds(Long[] ids);
}
