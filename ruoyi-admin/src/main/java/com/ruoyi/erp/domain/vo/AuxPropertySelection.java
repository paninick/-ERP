package com.ruoyi.erp.domain.vo;

import java.util.List;

/**
 * SKC笛卡尔积生成 - 辅助属性选择VO
 * 前端选择辅助属性（颜色列表、尺码列表），传给后端生成笛卡尔积
 *
 * @author ruoyi
 * @date 2026-04-16
 */
public class AuxPropertySelection {

    /**
     * 辅助属性类型：1=color, 2=size, 3=batch
     */
    private Integer auxType;

    /**
     * 属性名称
     */
    private String auxName;

    /**
     * 用户选中的属性值ID列表
     */
    private List<Long> selectedValueIds;

    public Integer getAuxType() {
        return auxType;
    }

    public void setAuxType(Integer auxType) {
        this.auxType = auxType;
    }

    public String getAuxName() {
        return auxName;
    }

    public void setAuxName(String auxName) {
        this.auxName = auxName;
    }

    public List<Long> getSelectedValueIds() {
        return selectedValueIds;
    }

    public void setSelectedValueIds(List<Long> selectedValueIds) {
        this.selectedValueIds = selectedValueIds;
    }
}
