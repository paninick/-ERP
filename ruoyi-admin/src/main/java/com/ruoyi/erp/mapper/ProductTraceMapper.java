package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.vo.ProductTraceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品追溯视图Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface ProductTraceMapper {

    /**
     * 查询产品追溯列表
     *
     * @param productTraceVO 查询条件
     * @return 产品追溯集合
     */
    List<ProductTraceVO> selectProductTraceList(ProductTraceVO productTraceVO);
}
