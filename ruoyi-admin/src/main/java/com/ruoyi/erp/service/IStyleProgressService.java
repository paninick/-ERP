package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.vo.StyleProgressVO;

import java.util.List;

/**
 * 款号进度Service接口
 *
 * @author ruoyi
 */
public interface IStyleProgressService {

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
    StyleProgressVO selectByStyleCode(String styleCode);
}
