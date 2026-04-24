package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.vo.StyleProgressVO;
import com.ruoyi.erp.mapper.StyleProgressMapper;
import com.ruoyi.erp.service.IStyleProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 款号进度Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class StyleProgressServiceImpl implements IStyleProgressService {

    @Autowired
    private StyleProgressMapper styleProgressMapper;

    @Override
    public List<StyleProgressVO> selectStyleProgressList(StyleProgressVO styleProgressVO) {
        return styleProgressMapper.selectStyleProgressList(styleProgressVO);
    }

    @Override
    public StyleProgressVO selectByStyleCode(String styleCode) {
        return styleProgressMapper.selectByStyleCode(styleCode);
    }
}
