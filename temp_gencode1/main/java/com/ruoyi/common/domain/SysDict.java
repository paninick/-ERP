package com.ruoyi.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 字典数据对象 sys_dict
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class SysDict extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 编码 */
    private Long id;

    /** 父级编号 */
    @Excel(name = "父级编号")
    private Long parentId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String parentIds;

    /** 字典键值 */
    @Excel(name = "字典键值")
    private String name;

    /** 字典类型 */
    @Excel(name = "字典类型")
    private String type;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long orderNum;

    /** 状态（0正常 1停用） */
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getParentIds() {
        return parentIds;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() {
        return orderNum;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("parentIds", getParentIds())
            .append("name", getName())
            .append("type", getType())
            .append("orderNum", getOrderNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
