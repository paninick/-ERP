package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.vo.ProductTraceVO;
import com.ruoyi.erp.mapper.ProductTraceMapper;
import com.ruoyi.erp.service.IProductTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品追溯Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ProductTraceServiceImpl implements IProductTraceService {

    @Autowired
    private ProductTraceMapper productTraceMapper;

    @Override
    public List<ProductTraceVO> selectProductTraceList(ProductTraceVO productTraceVO) {
        return productTraceMapper.selectProductTraceList(productTraceVO);
    }
}
