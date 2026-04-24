package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.vo.ProductTraceVO;
import com.ruoyi.erp.service.IProductTraceService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品追溯Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/erp/productTrace")
public class ProductTraceController extends BaseController {

    @Autowired
    private IProductTraceService productTraceService;

    /**
     * 查询产品追溯列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productTrace:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductTraceVO productTraceVO) {
        startPage();
        List<ProductTraceVO> list = productTraceService.selectProductTraceList(productTraceVO);
        return getDataTable(list);
    }
}
