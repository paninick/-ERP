package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.BomMapper;
import com.ruoyi.erp.domain.Bom;
import com.ruoyi.erp.service.IBomService;

@Service
public class BomServiceImpl implements IBomService {
    @Autowired
    private BomMapper bomMapper;

    @Override
    public Bom selectBomById(Long id) {
        return bomMapper.selectBomById(id);
    }

    @Override
    public List<Bom> selectBomList(Bom bom) {
        return bomMapper.selectBomList(bom);
    }

    @Override
    public int insertBom(Bom bom) {
        bom.setCreateBy(SecurityUtils.getUserId().toString());
        bom.setCreateTime(DateUtils.getNowDate());
        return bomMapper.insertBom(bom);
    }

    @Override
    public int updateBom(Bom bom) {
        bom.setUpdateTime(DateUtils.getNowDate());
        return bomMapper.updateBom(bom);
    }

    @Override
    public int deleteBomByIds(Long[] ids) {
        return bomMapper.deleteBomByIds(ids);
    }

    @Override
    public int deleteBomById(Long id) {
        return bomMapper.deleteBomById(id);
    }

    @Override
    public int insertBomBatch(List<Bom> list) {
        return bomMapper.insertBomBatch(list);
    }

    @Override
    public String importBom(List<Bom> list, boolean updateSupport) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("导入 BOM 数据不能为空");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Bom bom : list) {
            try {
                insertBom(bom);
                successNum++;
                successMsg.append("\n").append(successNum).append("、BOM 大货款号 ")
                        .append(bom.getBulkOrderNo()).append(" 导入成功");
            } catch (Exception e) {
                failureNum++;
                failureMsg.append("\n").append(failureNum).append("、BOM 导入失败：")
                        .append(e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条（明细请在 UI 中继续维护）。");
        return successMsg.toString();
    }
}
