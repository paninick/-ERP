package com.ruoyi.erp.cost.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ErpCostSummary extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long produceJobId;
    private Long producePlanId;

    @Excel(name = "款号")
    private String styleCode;

    @Excel(name = "销售单号")
    private String salesNo;

    @Excel(name = "汇总期间")
    private String period;

    @Excel(name = "物料成本")
    private BigDecimal materialCost;

    @Excel(name = "工资成本")
    private BigDecimal wageCost;

    @Excel(name = "外协成本")
    private BigDecimal outsourceCost;

    @Excel(name = "运费成本")
    private BigDecimal freightCost;

    @Excel(name = "质量损耗")
    private BigDecimal qualityLoss;

    @Excel(name = "其他成本")
    private BigDecimal otherCost;

    @Excel(name = "合计成本")
    private BigDecimal totalCost;

    @Excel(name = "完工数量")
    private BigDecimal finishQty;

    @Excel(name = "单件成本")
    private BigDecimal unitCost;

    private Date calcTime;
    private Long factoryId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProduceJobId() { return produceJobId; }
    public void setProduceJobId(Long produceJobId) { this.produceJobId = produceJobId; }
    public Long getProducePlanId() { return producePlanId; }
    public void setProducePlanId(Long producePlanId) { this.producePlanId = producePlanId; }
    public String getStyleCode() { return styleCode; }
    public void setStyleCode(String styleCode) { this.styleCode = styleCode; }
    public String getSalesNo() { return salesNo; }
    public void setSalesNo(String salesNo) { this.salesNo = salesNo; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public BigDecimal getMaterialCost() { return materialCost; }
    public void setMaterialCost(BigDecimal materialCost) { this.materialCost = materialCost; }
    public BigDecimal getWageCost() { return wageCost; }
    public void setWageCost(BigDecimal wageCost) { this.wageCost = wageCost; }
    public BigDecimal getOutsourceCost() { return outsourceCost; }
    public void setOutsourceCost(BigDecimal outsourceCost) { this.outsourceCost = outsourceCost; }
    public BigDecimal getFreightCost() { return freightCost; }
    public void setFreightCost(BigDecimal freightCost) { this.freightCost = freightCost; }
    public BigDecimal getQualityLoss() { return qualityLoss; }
    public void setQualityLoss(BigDecimal qualityLoss) { this.qualityLoss = qualityLoss; }
    public BigDecimal getOtherCost() { return otherCost; }
    public void setOtherCost(BigDecimal otherCost) { this.otherCost = otherCost; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public BigDecimal getFinishQty() { return finishQty; }
    public void setFinishQty(BigDecimal finishQty) { this.finishQty = finishQty; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public Date getCalcTime() { return calcTime; }
    public void setCalcTime(Date calcTime) { this.calcTime = calcTime; }
    public Long getFactoryId() { return factoryId; }
    public void setFactoryId(Long factoryId) { this.factoryId = factoryId; }
}
