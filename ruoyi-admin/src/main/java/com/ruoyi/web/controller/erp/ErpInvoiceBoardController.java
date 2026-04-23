package com.ruoyi.web.controller.erp;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.FactoryDataScope;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.FinInvoice;
import com.ruoyi.system.mapper.FinInvoiceMapper;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 发票对账看板 Controller
 * 路由：/erp/invoice
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/erp/invoice")
public class ErpInvoiceBoardController extends BaseController
{
    @Autowired
    private FinInvoiceMapper finInvoiceMapper;

    /**
     * GET /erp/invoice/boardStats
     * 返回看板汇总：{invoicedAmount, pendingAmount, reconcileRate}
     */
    @GetMapping("/boardStats")
    @PreAuthorize("@ss.hasPermi('erp:invoice:list')")
    public AjaxResult getBoardStats()
    {
        Long factoryId = getFactoryId();
        Map<String, Object> stats = finInvoiceMapper.selectBoardStats(factoryId);
        return AjaxResult.success(stats);
    }

    /**
     * GET /erp/invoice/list
     * 发票卡片流列表（含日期/状态/客户筛选 + 工厂隔离）
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('erp:invoice:list')")
    @FactoryDataScope(factoryAlias = "fi", userAlias = "")
    public TableDataInfo list(FinInvoice finInvoice)
    {
        startPage();
        List<FinInvoice> list = finInvoiceMapper.selectInvoiceBoardList(finInvoice);
        return getDataTable(list);
    }

    /**
     * GET /erp/invoice/{id}
     * 单条发票详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('erp:invoice:query')")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(finInvoiceMapper.selectInvoiceById(id));
    }

    /** 从当前登录用户获取工厂 ID */
    private Long getFactoryId()
    {
        try {
            return SecurityUtils.getLoginUser().getUser().getDeptId();
        } catch (Exception e) {
            return -1L;
        }
    }
}
