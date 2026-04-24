package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.vo.StyleProgressVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 款号进度视图Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface StyleProgressMapper {

    /**
     * 查询款号进度列表
     *
     * @param styleProgressVO 查询条件
     * @return 款号进度集合
     */
    List<StyleProgressVO> selectStyleProgressList(StyleProgressVO styleProgressVO);

    /**
     * 根据款号查询进度详情
     *
     * @param styleCode 款号
     * @return 进度详情
     */
    StyleProgressVO selectByStyleCode(@Param("styleCode") String styleCode);
}
