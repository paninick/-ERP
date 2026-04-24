package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.vo.ProductTraceVO;

import java.util.List;

/**
 * 产品追溯Service接口
 *
 * @author ruoyi
 */
public interface IProductTraceService {

    /**
     * 查询产品追溯列表
     *
     * @param productTraceVO 查询条件
     * @return 产品追溯集合
     */
    List<ProductTraceVO> selectProductTraceList(ProductTraceVO productTraceVO);
}
