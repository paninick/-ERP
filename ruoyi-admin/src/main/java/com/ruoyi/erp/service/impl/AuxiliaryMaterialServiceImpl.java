package com.ruoyi.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.erp.mapper.AuxiliaryMaterialMapper;
import com.ruoyi.erp.domain.AuxiliaryMaterial;
import com.ruoyi.erp.service.IAuxiliaryMaterialService;

/**
 * 辅料Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class AuxiliaryMaterialServiceImpl implements IAuxiliaryMaterialService {
    private static final Logger log = LoggerFactory.getLogger(AuxiliaryMaterialServiceImpl.class);
    @Autowired
    private AuxiliaryMaterialMapper auxiliaryMaterialMapper;

    /**
     * 查询辅料
     *
     * @param id 辅料主键
     * @return 辅料
     */
    @Override
    public AuxiliaryMaterial selectAuxiliaryMaterialById(Long id) {
        return auxiliaryMaterialMapper.selectAuxiliaryMaterialById(id);
    }

    /**
     * 查询辅料列表
     *
     * @param auxiliaryMaterial 辅料
     * @return 辅料
     */
    @Override
    public List<AuxiliaryMaterial> selectAuxiliaryMaterialList(AuxiliaryMaterial auxiliaryMaterial) {
        return auxiliaryMaterialMapper.selectAuxiliaryMaterialList(auxiliaryMaterial);
    }

    /**
     * 新增辅料
     *
     * @param auxiliaryMaterial 辅料
     * @return 结果
     */
    @Override
    public int insertAuxiliaryMaterial(AuxiliaryMaterial auxiliaryMaterial) {
        auxiliaryMaterial.setCreateBy(SecurityUtils.getUserId().toString());
        auxiliaryMaterial.setCreateTime(DateUtils.getNowDate());
        return auxiliaryMaterialMapper.insertAuxiliaryMaterial(auxiliaryMaterial);
    }

    /**
     * 修改辅料
     *
     * @param auxiliaryMaterial 辅料
     * @return 结果
     */
    @Override
    public int updateAuxiliaryMaterial(AuxiliaryMaterial auxiliaryMaterial) {
        auxiliaryMaterial.setUpdateTime(DateUtils.getNowDate());
        return auxiliaryMaterialMapper.updateAuxiliaryMaterial(auxiliaryMaterial);
    }

    /**
     * 批量删除辅料
     *
     * @param ids 需要删除的辅料主键
     * @return 结果
     */
    @Override
    public int deleteAuxiliaryMaterialByIds(Long[] ids) {
        return auxiliaryMaterialMapper.deleteAuxiliaryMaterialByIds(ids);
    }

    /**
     * 删除辅料信息
     *
     * @param id 辅料主键
     * @return 结果
     */
    @Override
    public int deleteAuxiliaryMaterialById(Long id) {
        return auxiliaryMaterialMapper.deleteAuxiliaryMaterialById(id);
    }

    /**
     * 批量新增辅料
     *
     * @param list 辅料
     * @return 结果
     */
    @Override
    public int insertAuxiliaryMaterialBatch(List<AuxiliaryMaterial> list) {
        return auxiliaryMaterialMapper.insertAuxiliaryMaterialBatch(list);
    }

    /**
     * 导入辅料数据
     *
     * @param list 辅料数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    @Override
    public String importAuxiliary(List<AuxiliaryMaterial> list, boolean updateSupport) {
        log.info("========== 开始导入辅料数据 ==========");
        log.info("接收数据数量: {}", list == null ? 0 : list.size());
        
        if (list == null || list.isEmpty()) {
            log.error("导入辅料数据为空！");
            throw new RuntimeException("导入辅料数据不能为空！");
        }
        
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        
        for (int i = 0; i < list.size(); i++) {
            AuxiliaryMaterial material = list.get(i);
            log.info("处理第 {} 条数据: {}", i + 1, material);
            
            try {
                if (material.getAuxiliaryMaterialNo() == null || material.getAuxiliaryMaterialNo().trim().isEmpty()) {
                    failureNum++;
                    String errorMsg = failureNum + "、辅料编号不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                if (material.getName() == null || material.getName().trim().isEmpty()) {
                    failureNum++;
                    String errorMsg = failureNum + "、辅料品名不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                log.info("第 {} 条数据 - 辅料编号: {}, 辅料品名: {}", 
                    i + 1, material.getAuxiliaryMaterialNo(), material.getName());
                
                AuxiliaryMaterial exist = auxiliaryMaterialMapper.selectAuxiliaryMaterialByNo(material.getAuxiliaryMaterialNo());
                log.info("第 {} 条数据 - 是否已存在: {}", i + 1, exist != null);
                
                if (exist == null) {
                    material.setCreateBy(SecurityUtils.getUserId().toString());
                    material.setCreateTime(DateUtils.getNowDate());
                    log.info("第 {} 条数据 - 准备新增: {}", i + 1, material);
                    
                    int insertResult = auxiliaryMaterialMapper.insertAuxiliaryMaterial(material);
                    log.info("第 {} 条数据 - 新增结果: {}", i + 1, insertResult);
                    
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、辅料 ").append(material.getName()).append(" 导入成功");
                } else if (updateSupport) {
                    material.setId(exist.getId());
                    material.setUpdateTime(DateUtils.getNowDate());
                    log.info("第 {} 条数据 - 准备更新: {}", i + 1, material);
                    
                    int updateResult = auxiliaryMaterialMapper.updateAuxiliaryMaterial(material);
                    log.info("第 {} 条数据 - 更新结果: {}", i + 1, updateResult);
                    
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、辅料 ").append(material.getName()).append(" 更新成功");
                } else {
                    failureNum++;
                    String errorMsg = failureNum + "、辅料编号 " + material.getAuxiliaryMaterialNo() + " 已存在";
                    log.warn("第 {} 条数据: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                }
            } catch (Exception e) {
                failureNum++;
                String msg = failureNum + "、辅料 " + material.getName() + " 导入失败";
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
        
        log.info("========== 辅料数据导入完成 ==========");
        log.info("成功: {} 条, 失败: {} 条", successNum, failureNum);
        log.info("最终结果: {}", result.toString());
        
        return result.toString();
    }

    @Override
    @Transactional
    public String importAuxiliaryFast(List<AuxiliaryMaterial> list) {
        log.info("========== 开始快速批量导入辅料数据 ==========");
        log.info("接收数据数量: {}", list == null ? 0 : list.size());
        
        if (list == null || list.isEmpty()) {
            log.error("快速导入辅料数据为空！");
            throw new RuntimeException("导入辅料数据不能为空！");
        }
        
        int validCount = 0;
        int invalidCount = 0;
        List<AuxiliaryMaterial> validList = new ArrayList<>();
        StringBuilder invalidMsg = new StringBuilder();
        String userId = SecurityUtils.getUserId().toString();
        Date now = DateUtils.getNowDate();
        
        for (int i = 0; i < list.size(); i++) {
            AuxiliaryMaterial material = list.get(i);
            log.info("验证第 {} 条数据: {}", i + 1, material);
            
            try {
                if (material.getAuxiliaryMaterialNo() == null || material.getAuxiliaryMaterialNo().trim().isEmpty()) {
                    invalidCount++;
                    String errorMsg = invalidCount + "、第" + (i + 1) + "行辅料编号不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    invalidMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                if (material.getName() == null || material.getName().trim().isEmpty()) {
                    invalidCount++;
                    String errorMsg = invalidCount + "、第" + (i + 1) + "行辅料品名不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    invalidMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                material.setCreateBy(userId);
                material.setCreateTime(now);
                material.setUpdateBy(userId);
                material.setUpdateTime(now);
                
                validList.add(material);
                validCount++;
                log.info("第 {} 条数据验证通过 - 辅料编号: {}, 辅料品名: {}", 
                    i + 1, material.getAuxiliaryMaterialNo(), material.getName());
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
                insertCount = auxiliaryMaterialMapper.insertAuxiliaryMaterialBatchSafe(validList);
                log.info("批量插入完成，影响行数: {}", insertCount);
            } catch (Exception e) {
                log.error("批量插入异常，尝试逐条插入", e);
                insertCount = 0;
                for (AuxiliaryMaterial material : validList) {
                    try {
                        int result = auxiliaryMaterialMapper.insertAuxiliaryMaterialSafe(material);
                        insertCount += result;
                        log.info("逐条插入 - 辅料编号: {}, 结果: {}", material.getAuxiliaryMaterialNo(), result);
                    } catch (Exception ex) {
                        log.error("逐条插入失败 - 辅料编号: {}", material.getAuxiliaryMaterialNo(), ex);
                    }
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        result.append("快速导入完成！验证通过 ").append(validCount).append(" 条，成功插入 ").append(insertCount).append(" 条，验证失败 ").append(invalidCount).append(" 条。");
        if (invalidCount > 0) {
            result.append("<br/>验证失败明细：").append(invalidMsg);
        }
        
        log.info("========== 快速批量导入辅料数据完成 ==========");
        log.info("最终结果: {}", result.toString());
        
        return result.toString();
    }

    @Override
    public int insertAuxiliaryMaterialSafe(AuxiliaryMaterial auxiliaryMaterial) {
        return auxiliaryMaterialMapper.insertAuxiliaryMaterialSafe(auxiliaryMaterial);
    }

    @Override
    public int insertAuxiliaryMaterialBatchSafe(List<AuxiliaryMaterial> list) {
        return auxiliaryMaterialMapper.insertAuxiliaryMaterialBatchSafe(list);
    }
}
