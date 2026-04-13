package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SupplierMapper;
import com.ruoyi.erp.domain.Supplier;
import com.ruoyi.erp.service.ISupplierService;

/**
 * 供应商Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 查询供应商
     *
     * @param id 供应商主键
     * @return 供应商
     */
    @Override
    public Supplier selectSupplierById(Long id) {
        return supplierMapper.selectSupplierById(id);
    }

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商
     * @return 供应商
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier) {
        return supplierMapper.selectSupplierList(supplier);
    }

    /**
     * 新增供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int insertSupplier(Supplier supplier) {
        supplier.setCreateBy(SecurityUtils.getUserId().toString());
        supplier.setCreateTime(DateUtils.getNowDate());
        return supplierMapper.insertSupplier(supplier);
    }

    /**
     * 修改供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int updateSupplier(Supplier supplier) {
        supplier.setUpdateTime(DateUtils.getNowDate());
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierByIds(Long[] ids) {
        return supplierMapper.deleteSupplierByIds(ids);
    }

    /**
     * 删除供应商信息
     *
     * @param id 供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierById(Long id) {
        return supplierMapper.deleteSupplierById(id);
    }

    /**
     * 批量新增供应商
     *
     * @param list 供应商
     * @return 结果
     */
    @Override
    public int insertSupplierBatch(List<Supplier> list) {
        return supplierMapper.insertSupplierBatch(list);
    }

    /**
     * 导入供应商数据
     *
     * @param list 供应商数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    @Override
    public String importSupplier(List<Supplier> list, boolean updateSupport) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("导入供应商数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Supplier supplier : list) {
            try {
                if (supplier.getSupplierName() == null || supplier.getSupplierName().trim().isEmpty()) {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、供应商名称不能为空");
                    continue;
                }
                Supplier exist = supplierMapper.selectSupplierByNo(supplier.getSupplierNo());
                if (exist == null) {
                    supplier.setCreateBy(SecurityUtils.getUserId().toString());
                    supplier.setCreateTime(DateUtils.getNowDate());
                    this.insertSupplier(supplier);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、供应商 ").append(supplier.getSupplierName()).append(" 导入成功");
                } else if (updateSupport) {
                    supplier.setUpdateTime(DateUtils.getNowDate());
                    this.updateSupplier(supplier);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、供应商 ").append(supplier.getSupplierName()).append(" 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、供应商编号 ").append(supplier.getSupplierNo()).append(" 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、供应商 " + supplier.getSupplierName() + " 导入失败：";
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
}
