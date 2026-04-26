package com.ruoyi.erp.controller.production;

import com.ruoyi.erp.domain.vo.StyleProgressVO;
import com.ruoyi.erp.service.IStyleProgressService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 款号进度视图Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/erp/styleProgress")
public class StyleProgressController extends BaseController {

    @Autowired
    private IStyleProgressService styleProgressService;

    /**
     * 查询款号进度列表
     */
    @PreAuthorize("@ss.hasPermi('erp:styleProgress:list')")
    @GetMapping("/list")
    public TableDataInfo list(StyleProgressVO styleProgressVO) {
        startPage();
        List<StyleProgressVO> list = styleProgressService.selectStyleProgressList(styleProgressVO);
        return getDataTable(list);
    }

    /**
     * 获取款号进度详情
     */
    @PreAuthorize("@ss.hasPermi('erp:styleProgress:query')")
    @GetMapping(value = "/{styleCode}")
    public AjaxResult getInfo(@PathVariable String styleCode) {
        return AjaxResult.success(styleProgressService.selectByStyleCode(styleCode));
    }
}
