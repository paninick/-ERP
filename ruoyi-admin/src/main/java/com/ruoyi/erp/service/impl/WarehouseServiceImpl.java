package com.ruoyi.erp.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.erp.mapper.WarehouseMapper;
import com.ruoyi.erp.domain.Warehouse;
import com.ruoyi.erp.service.IWarehouseService;

/**
 * 库区管理Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class WarehouseServiceImpl implements IWarehouseService {
    private static final Logger log = LoggerFactory.getLogger(WarehouseServiceImpl.class);
    @Autowired
    private WarehouseMapper warehouseMapper;

    /**
     * 查询库区管理
     *
     * @param id 库区管理主键
     * @return 库区管理
     */
    @Override
    public Warehouse selectWarehouseById(Long id) {
        return warehouseMapper.selectWarehouseById(id);
    }

    /**
     * 查询库区管理列表
     *
     * @param warehouse 库区管理
     * @return 库区管理
     */
    @Override
    public List<Warehouse> selectWarehouseList(Warehouse warehouse) {
        return warehouseMapper.selectWarehouseList(warehouse);
    }

    /**
     * 新增库区管理
     *
     * @param warehouse 库区管理
     * @return 结果
     */
    @Override
    public int insertWarehouse(Warehouse warehouse) {
        warehouse.setCreateBy(SecurityUtils.getUserId().toString());
        warehouse.setCreateTime(DateUtils.getNowDate());
        return warehouseMapper.insertWarehouse(warehouse);
    }

    /**
     * 修改库区管理
     *
     * @param warehouse 库区管理
     * @return 结果
     */
    @Override
    public int updateWarehouse(Warehouse warehouse) {
        warehouse.setUpdateTime(DateUtils.getNowDate());
        return warehouseMapper.updateWarehouse(warehouse);
    }

    /**
     * 批量删除库区管理
     *
     * @param ids 需要删除的库区管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseByIds(Long[] ids) {
        return warehouseMapper.deleteWarehouseByIds(ids);
    }

    /**
     * 删除库区管理信息
     *
     * @param id 库区管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseById(Long id) {
        return warehouseMapper.deleteWarehouseById(id);
    }

    /**
     * 批量新增库区管理
     *
     * @param list 库区管理
     * @return 结果
     */
    @Override
    public int insertWarehouseBatch(List<Warehouse> list) {
        return warehouseMapper.insertWarehouseBatch(list);
    }

    /**
     * 导入库区数据
     *
     * @param list 库区数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    @Override
    public String importWarehouse(List<Warehouse> list, boolean updateSupport) {
        log.info("========== 开始导入库区数据 ==========");
        log.info("接收数据数量: {}", list == null ? 0 : list.size());
        
        if (list == null || list.isEmpty()) {
            log.error("导入库区数据为空！");
            throw new RuntimeException("导入库区数据不能为空！");
        }
        
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        
        for (int i = 0; i < list.size(); i++) {
            Warehouse warehouse = list.get(i);
            log.info("处理第 {} 条数据: {}", i + 1, warehouse);
            
            try {
                if (warehouse.getCode() == null || warehouse.getCode().trim().isEmpty()) {
                    failureNum++;
                    String errorMsg = failureNum + "、库区编码不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) {
                    failureNum++;
                    String errorMsg = failureNum + "、库区名称不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                log.info("第 {} 条数据 - 库区编码: {}, 库区名称: {}", 
                    i + 1, warehouse.getCode(), warehouse.getName());
                
                Warehouse exist = warehouseMapper.selectWarehouseByCode(warehouse.getCode());
                log.info("第 {} 条数据 - 是否已存在: {}", i + 1, exist != null);
                
                if (exist == null) {
                    warehouse.setCreateBy(SecurityUtils.getUserId().toString());
                    warehouse.setCreateTime(DateUtils.getNowDate());
                    log.info("第 {} 条数据 - 准备新增: {}", i + 1, warehouse);
                    
                    int insertResult = warehouseMapper.insertWarehouse(warehouse);
                    log.info("第 {} 条数据 - 新增结果: {}", i + 1, insertResult);
                    
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、库区 ").append(warehouse.getName()).append(" 导入成功");
                } else if (updateSupport) {
                    warehouse.setId(exist.getId());
                    warehouse.setUpdateTime(DateUtils.getNowDate());
                    log.info("第 {} 条数据 - 准备更新: {}", i + 1, warehouse);
                    
                    int updateResult = warehouseMapper.updateWarehouse(warehouse);
                    log.info("第 {} 条数据 - 更新结果: {}", i + 1, updateResult);
                    
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、库区 ").append(warehouse.getName()).append(" 更新成功");
                } else {
                    failureNum++;
                    String errorMsg = failureNum + "、库区编号 " + warehouse.getCode() + " 已存在";
                    log.warn("第 {} 条数据: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                }
            } catch (Exception e) {
                failureNum++;
                String msg = failureNum + "、库区 " + warehouse.getName() + " 导入失败";
                log.error("第 {} 条数据异常: {}", i + 1, msg, e);
                failureMsg.append("<br/>").append(msg).append("：").append(e.getMessage());
            }
        }
        
        StringBuilder result = new StringBuilder();
        result.append("导入完成！成功 ").append(successNum).append(" 条，失败 ").append(failureNum).append(" 条。");
        if (failureNum > 0) {
            result.append("<br/>失败明细：").append(failureMsg);
        }
        if (successNum > 0) {
            result.append("<br/>成功明细：").append(successMsg);
        }
        
        log.info("========== 库区数据导入完成 ==========");
        log.info("成功: {} 条, 失败: {} 条", successNum, failureNum);
        log.info("最终结果: {}", result.toString());
        
        return result.toString();
    }

    @Override
    @Transactional
    public String importWarehouseFast(List<Warehouse> list) {
        log.info("========== 开始快速批量导入库区数据 ==========");
        log.info("接收数据数量: {}", list == null ? 0 : list.size());
        
        if (list == null || list.isEmpty()) {
            log.error("快速导入库区数据为空！");
            throw new RuntimeException("导入库区数据不能为空！");
        }
        
        int validCount = 0;
        int invalidCount = 0;
        List<Warehouse> validList = new ArrayList<>();
        StringBuilder invalidMsg = new StringBuilder();
        String userId = SecurityUtils.getUserId().toString();
        java.util.Date now = DateUtils.getNowDate();
        
        for (int i = 0; i < list.size(); i++) {
            Warehouse warehouse = list.get(i);
            log.info("验证第 {} 条数据: {}", i + 1, warehouse);
            
            try {
                if (warehouse.getCode() == null || warehouse.getCode().trim().isEmpty()) {
                    invalidCount++;
                    String errorMsg = invalidCount + "、第" + (i + 1) + "行库区编码不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    invalidMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                if (warehouse.getName() == null || warehouse.getName().trim().isEmpty()) {
                    invalidCount++;
                    String errorMsg = invalidCount + "、第" + (i + 1) + "行库区名称不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    invalidMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                warehouse.setCreateBy(userId);
                warehouse.setCreateTime(now);
                warehouse.setUpdateBy(userId);
                warehouse.setUpdateTime(now);
                
                validList.add(warehouse);
                validCount++;
                log.info("第 {} 条数据验证通过 - 库区编码: {}, 库区名称: {}", 
                    i + 1, warehouse.getCode(), warehouse.getName());
            } catch (Exception e) {
                invalidCount++;
                String msg = invalidCount + "、第" + (i + 1) + "行数据验证异常";
                log.error("第 {} 条数据异常: {}", i + 1, msg, e);
                invalidMsg.append("<br/>").append(msg).append("：").append(e.getMessage());
            }
        }
        
        log.info("数据验证完成 - 有效: {} 条, 无效: {} 条", validCount, invalidCount);
        
        int insertCount = 0;
        if (!validList.isEmpty()) {
            log.info("开始批量插入 {} 条数据...", validList.size());
            try {
                insertCount = warehouseMapper.insertWarehouseBatchSafe(validList);
                log.info("批量插入完成，影响行数: {}", insertCount);
            } catch (Exception e) {
                log.error("批量插入异常，尝试逐条插入", e);
                insertCount = 0;
                for (Warehouse warehouse : validList) {
                    try {
                        int result = warehouseMapper.insertWarehouseSafe(warehouse);
                        insertCount += result;
                        log.info("逐条插入 - 库区编码: {}, 结果: {}", warehouse.getCode(), result);
                    } catch (Exception ex) {
                        log.error("逐条插入失败 - 库区编码: {}", warehouse.getCode(), ex);
                    }
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        result.append("快速导入完成！验证通过 ").append(validCount).append(" 条，成功插入 ").append(insertCount).append(" 条，验证失败 ").append(invalidCount).append(" 条。");
        if (invalidCount > 0) {
            result.append("<br/>验证失败明细：").append(invalidMsg);
        }
        
        log.info("========== 快速批量导入库区数据完成 ==========");
        log.info("最终结果: {}", result.toString());
        
        return result.toString();
    }

    @Override
    public int insertWarehouseSafe(Warehouse warehouse) {
        return warehouseMapper.insertWarehouseSafe(warehouse);
    }

    @Override
    public int insertWarehouseBatchSafe(List<Warehouse> list) {
        return warehouseMapper.insertWarehouseBatchSafe(list);
    }
}
