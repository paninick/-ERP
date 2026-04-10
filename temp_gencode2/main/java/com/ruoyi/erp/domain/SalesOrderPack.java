package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 大货订单包装纸箱明细对象 t_erp_sales_order_pack
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class SalesOrderPack extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 销售订单id */
    private Long salesOrderId;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date packDate;

    /** 成衣品番 */
    @Excel(name = "成衣品番")
    private String clotheNo;

    /** 胶袋种类 */
    @Excel(name = "胶袋种类")
    private String tapeType;

    /** 胶袋尺寸 */
    @Excel(name = "胶袋尺寸")
    private String tapeSize;

    /** 胶袋数量 */
    @Excel(name = "胶袋数量")
    private Long tapeNum;

    /** 纸箱唛头 */
    @Excel(name = "纸箱唛头")
    private String cartonMark;

    /** 契约番号 */
    @Excel(name = "契约番号")
    private String contractNo;

    /** 纸箱尺寸 */
    @Excel(name = "纸箱尺寸")
    private String cartonSize;

    /** 纸箱数量 */
    @Excel(name = "纸箱数量")
    private Long cartonNum;

    /** 每箱数量 */
    @Excel(name = "每箱数量")
    private String perNum;

    /** 单件重量 */
    @Excel(name = "单件重量")
    private BigDecimal singleWeight;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }
    public void setPackDate(Date packDate) {
        this.packDate = packDate;
    }

    public Date getPackDate() {
        return packDate;
    }
    public void setClotheNo(String clotheNo) {
        this.clotheNo = clotheNo;
    }

    public String getClotheNo() {
        return clotheNo;
    }
    public void setTapeType(String tapeType) {
        this.tapeType = tapeType;
    }

    public String getTapeType() {
        return tapeType;
    }
    public void setTapeSize(String tapeSize) {
        this.tapeSize = tapeSize;
    }

    public String getTapeSize() {
        return tapeSize;
    }
    public void setTapeNum(Long tapeNum) {
        this.tapeNum = tapeNum;
    }

    public Long getTapeNum() {
        return tapeNum;
    }
    public void setCartonMark(String cartonMark) {
        this.cartonMark = cartonMark;
    }

    public String getCartonMark() {
        return cartonMark;
    }
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractNo() {
        return contractNo;
    }
    public void setCartonSize(String cartonSize) {
        this.cartonSize = cartonSize;
    }

    public String getCartonSize() {
        return cartonSize;
    }
    public void setCartonNum(Long cartonNum) {
        this.cartonNum = cartonNum;
    }

    public Long getCartonNum() {
        return cartonNum;
    }
    public void setPerNum(String perNum) {
        this.perNum = perNum;
    }

    public String getPerNum() {
        return perNum;
    }
    public void setSingleWeight(BigDecimal singleWeight) {
        this.singleWeight = singleWeight;
    }

    public BigDecimal getSingleWeight() {
        return singleWeight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("salesOrderId", getSalesOrderId())
            .append("packDate", getPackDate())
            .append("clotheNo", getClotheNo())
            .append("tapeType", getTapeType())
            .append("tapeSize", getTapeSize())
            .append("tapeNum", getTapeNum())
            .append("cartonMark", getCartonMark())
            .append("contractNo", getContractNo())
            .append("cartonSize", getCartonSize())
            .append("cartonNum", getCartonNum())
            .append("perNum", getPerNum())
            .append("singleWeight", getSingleWeight())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
