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
import com.ruoyi.erp.mapper.MainMaterialMapper;
import com.ruoyi.erp.domain.MainMaterial;
import com.ruoyi.erp.service.IMainMaterialService;

/**
 * 主料Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class MainMaterialServiceImpl implements IMainMaterialService {
    private static final Logger log = LoggerFactory.getLogger(MainMaterialServiceImpl.class);
    @Autowired
    private MainMaterialMapper mainMaterialMapper;

    /**
     * 查询主料
     *
     * @param id 主料主键
     * @return 主料
     */
    @Override
    public MainMaterial selectMainMaterialById(Long id) {
        return mainMaterialMapper.selectMainMaterialById(id);
    }

    /**
     * 查询主料列表
     *
     * @param mainMaterial 主料
     * @return 主料
     */
    @Override
    public List<MainMaterial> selectMainMaterialList(MainMaterial mainMaterial) {
        return mainMaterialMapper.selectMainMaterialList(mainMaterial);
    }

    /**
     * 新增主料
     *
     * @param mainMaterial 主料
     * @return 结果
     */
    @Override
    @Transactional
    public int insertMainMaterial(MainMaterial mainMaterial) {
        mainMaterial.setCreateBy(SecurityUtils.getUserId().toString());
        mainMaterial.setCreateTime(DateUtils.getNowDate());
        return mainMaterialMapper.insertMainMaterial(mainMaterial);
    }

    /**
     * 修改主料
     *
     * @param mainMaterial 主料
     * @return 结果
     */
    @Override
    @Transactional
    public int updateMainMaterial(MainMaterial mainMaterial) {
        mainMaterial.setUpdateTime(DateUtils.getNowDate());
        return mainMaterialMapper.updateMainMaterial(mainMaterial);
    }

    /**
     * 批量删除主料
     *
     * @param ids 需要删除的主料主键
     * @return 结果
     */
    @Override
    public int deleteMainMaterialByIds(Long[] ids) {
        return mainMaterialMapper.deleteMainMaterialByIds(ids);
    }

    /**
     * 删除主料信息
     *
     * @param id 主料主键
     * @return 结果
     */
    @Override
    public int deleteMainMaterialById(Long id) {
        return mainMaterialMapper.deleteMainMaterialById(id);
    }

    /**
     * 批量新增主料
     *
     * @param list 主料
     * @return 结果
     */
    @Override
    public int insertMainMaterialBatch(List<MainMaterial> list) {
        return mainMaterialMapper.insertMainMaterialBatch(list);
    }

    /**
     * 导入主料数据
     *
     * @param list 主料数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    @Override
    public String importMaterial(List<MainMaterial> list, boolean updateSupport) {
        log.info("========== 开始导入主料数据 ==========");
        log.info("接收数据数量: {}", list == null ? 0 : list.size());
        
        if (list == null || list.isEmpty()) {
            log.error("导入主料数据为空！");
            throw new RuntimeException("导入主料数据不能为空！");
        }
        
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        
        for (int i = 0; i < list.size(); i++) {
            MainMaterial material = list.get(i);
            log.info("处理第 {} 条数据: {}", i + 1, material);
            
            try {
                // 验证主料编号
                if (material.getMainMaterialNo() == null || material.getMainMaterialNo().trim().isEmpty()) {
                    failureNum++;
                    String errorMsg = failureNum + "、主料编号不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                // 验证主料品名
                if (material.getName() == null || material.getName().trim().isEmpty()) {
                    failureNum++;
                    String errorMsg = failureNum + "、主料品名不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                log.info("第 {} 条数据 - 主料编号: {}, 主料品名: {}, 厂供编号: {}", 
                    i + 1, material.getMainMaterialNo(), material.getName(), material.getFactoryNo());
                
                // 检查是否已存在
                MainMaterial exist = mainMaterialMapper.selectMainMaterialByNo(material.getMainMaterialNo());
                log.info("第 {} 条数据 - 是否已存在: {}", i + 1, exist != null);
                
                if (exist == null) {
                    // 新增
                    material.setCreateBy(SecurityUtils.getUserId().toString());
                    material.setCreateTime(DateUtils.getNowDate());
                    log.info("第 {} 条数据 - 准备新增: {}", i + 1, material);
                    
                    int insertResult = mainMaterialMapper.insertMainMaterial(material);
                    log.info("第 {} 条数据 - 新增结果: {}", i + 1, insertResult);
                    
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、主料 ").append(material.getName()).append(" 导入成功");
                } else if (updateSupport) {
                    // 更新
                    material.setId(exist.getId());
                    material.setUpdateTime(DateUtils.getNowDate());
                    log.info("第 {} 条数据 - 准备更新: {}", i + 1, material);
                    
                    int updateResult = mainMaterialMapper.updateMainMaterial(material);
                    log.info("第 {} 条数据 - 更新结果: {}", i + 1, updateResult);
                    
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、主料 ").append(material.getName()).append(" 更新成功");
                } else {
                    // 已存在且不更新
                    failureNum++;
                    String errorMsg = failureNum + "、主料编号 " + material.getMainMaterialNo() + " 已存在";
                    log.warn("第 {} 条数据: {}", i + 1, errorMsg);
                    failureMsg.append("<br/>").append(errorMsg);
                }
            } catch (Exception e) {
                failureNum++;
                String msg = failureNum + "、主料 " + material.getName() + " 导入失败";
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
        
        log.info("========== 主料数据导入完成 ==========");
        log.info("成功: {} 条, 失败: {} 条", successNum, failureNum);
        log.info("最终结果: {}", result.toString());
        
        return result.toString();
    }

    @Override
    @Transactional
    public String importMaterialFast(List<MainMaterial> list) {
        log.info("========== 开始快速批量导入主料数据 ==========");
        log.info("接收数据数量: {}", list == null ? 0 : list.size());
        
        if (list == null || list.isEmpty()) {
            log.error("快速导入主料数据为空！");
            throw new RuntimeException("导入主料数据不能为空！");
        }
        
        int validCount = 0;
        int invalidCount = 0;
        List<MainMaterial> validList = new ArrayList<>();
        StringBuilder invalidMsg = new StringBuilder();
        String userId = SecurityUtils.getUserId().toString();
        Date now = DateUtils.getNowDate();
        
        for (int i = 0; i < list.size(); i++) {
            MainMaterial material = list.get(i);
            log.info("验证第 {} 条数据: {}", i + 1, material);
            
            try {
                // 验证主料编号
                if (material.getMainMaterialNo() == null || material.getMainMaterialNo().trim().isEmpty()) {
                    invalidCount++;
                    String errorMsg = invalidCount + "、第" + (i + 1) + "行主料编号不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    invalidMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                // 验证主料品名
                if (material.getName() == null || material.getName().trim().isEmpty()) {
                    invalidCount++;
                    String errorMsg = invalidCount + "、第" + (i + 1) + "行主料品名不能为空";
                    log.warn("第 {} 条数据验证失败: {}", i + 1, errorMsg);
                    invalidMsg.append("<br/>").append(errorMsg);
                    continue;
                }
                
                // 设置创建信息
                material.setCreateBy(userId);
                material.setCreateTime(now);
                material.setUpdateBy(userId);
                material.setUpdateTime(now);
                
                validList.add(material);
                validCount++;
                log.info("第 {} 条数据验证通过 - 主料编号: {}, 主料品名: {}", 
                    i + 1, material.getMainMaterialNo(), material.getName());
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
                insertCount = mainMaterialMapper.insertMainMaterialBatchSafe(validList);
                log.info("批量插入完成，影响行数: {}", insertCount);
            } catch (Exception e) {
                log.error("批量插入异常，尝试逐条插入", e);
                // 批量失败时，尝试逐条安全插入
                insertCount = 0;
                for (MainMaterial material : validList) {
                    try {
                        int result = mainMaterialMapper.insertMainMaterialSafe(material);
                        insertCount += result;
                        log.info("逐条插入 - 主料编号: {}, 结果: {}", material.getMainMaterialNo(), result);
                    } catch (Exception ex) {
                        log.error("逐条插入失败 - 主料编号: {}", material.getMainMaterialNo(), ex);
                    }
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        result.append("快速导入完成！验证通过 ").append(validCount).append(" 条，成功插入 ").append(insertCount).append(" 条，验证失败 ").append(invalidCount).append(" 条。");
        if (invalidCount > 0) {
            result.append("<br/>验证失败明细：").append(invalidMsg);
        }
        
        log.info("========== 快速批量导入主料数据完成 ==========");
        log.info("最终结果: {}", result.toString());
        
        return result.toString();
    }

    @Override
    public int insertMainMaterialSafe(MainMaterial mainMaterial) {
        return mainMaterialMapper.insertMainMaterialSafe(mainMaterial);
    }

    @Override
    public int insertMainMaterialBatchSafe(List<MainMaterial> list) {
        return mainMaterialMapper.insertMainMaterialBatchSafe(list);
    }
}
