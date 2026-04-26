package com.ruoyi.erp.style.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.erp.style.domain.ErpStyle;
import com.ruoyi.erp.style.mapper.ErpStyleMapper;
import com.ruoyi.erp.style.service.IErpStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErpStyleServiceImpl implements IErpStyleService {

    @Autowired
    private ErpStyleMapper styleMapper;

    @Override
    public ErpStyle selectErpStyleById(Long id) {
        return styleMapper.selectErpStyleById(id);
    }

    @Override
    public ErpStyle selectErpStyleByCode(String styleCode) {
        return styleMapper.selectErpStyleByCode(styleCode);
    }

    @Override
    public List<ErpStyle> selectErpStyleList(ErpStyle style) {
        return styleMapper.selectErpStyleList(style);
    }

    @Override
    public int insertErpStyle(ErpStyle style) {
        style.setCreateTime(DateUtils.getNowDate());
        return styleMapper.insertErpStyle(style);
    }

    @Override
    public int updateErpStyle(ErpStyle style) {
        style.setUpdateTime(DateUtils.getNowDate());
        return styleMapper.updateErpStyle(style);
    }

    @Override
    public int deleteErpStyleByIds(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += styleMapper.deleteErpStyleById(id);
        }
        return rows;
    }
}
