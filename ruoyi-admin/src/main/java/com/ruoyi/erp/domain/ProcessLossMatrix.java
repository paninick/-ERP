package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工序损耗矩阵对象 t_erp_process_loss_matrix
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class ProcessLossMatrix extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 矩阵ID */
    private Long id;

    /** 主料类型 */
    @Excel(name = "主料类型")
    private String materialAType;

    /** 辅料类型 */
    @Excel(name = "辅料类型")
    private String materialBType;

    /** 工艺代码 */
    @Excel(name = "工艺代码")
    private String processCode;

    /** 标准损耗率（%） */
    @Excel(name = "标准损耗率（%）")
    private Double standardLossRate;

    /** 历史实际平均损耗 */
    @Excel(name = "历史实际平均损耗")
    private Double actualAvgLoss;

    /** 备注 */
    private String remark;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMaterialAType(String materialAType) {
        this.materialAType = materialAType;
    }

    public String getMaterialAType() {
        return materialAType;
    }

    public void setMaterialBType(String materialBType) {
        this.materialBType = materialBType;
    }

    public String getMaterialBType() {
        return materialBType;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setStandardLossRate(Double standardLossRate) {
        this.standardLossRate = standardLossRate;
    }

    public Double getStandardLossRate() {
        return standardLossRate;
    }

    public void setActualAvgLoss(Double actualAvgLoss) {
        this.actualAvgLoss = actualAvgLoss;
    }

    public Double getActualAvgLoss() {
        return actualAvgLoss;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialAType", getMaterialAType())
            .append("materialBType", getMaterialBType())
            .append("processCode", getProcessCode())
            .append("standardLossRate", getStandardLossRate())
            .append("actualAvgLoss", getActualAvgLoss())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
