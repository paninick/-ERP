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
}
