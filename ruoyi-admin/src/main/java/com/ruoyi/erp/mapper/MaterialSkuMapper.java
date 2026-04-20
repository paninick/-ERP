package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.MaterialSku;

/**
 * 物料SKU Mapper接口
 *
 * @author ruoyi
 * @date 2026-04-16
 */
public interface MaterialSkuMapper {
    /**
     * 查询物料SKU
     *
     * @param id SKU主键
     * @return 物料SKU
     */
    public MaterialSku selectMaterialSkuById(Long id);

    /**
     * 查询物料SKU列表
     *
     * @param materialSku 物料SKU
     * @return 物料SKU集合
     */
    public List<MaterialSku> selectMaterialSkuList(MaterialSku materialSku);

    /**
     * 查询物料下的所有SKU
     *
     * @param materialId 物料ID
     * @return SKU列表
     */
    public List<MaterialSku> selectMaterialSkuByMaterialId(Long materialId);

    /**
     * 检查SKU编码是否唯一
     *
     * @param skuCode SKU编码
     * @return 结果
     */
    public int checkSkuCodeUnique(String skuCode);

    /**
     * 新增物料SKU
     *
     * @param materialSku 物料SKU
     * @return 结果
     */
    public int insertMaterialSku(MaterialSku materialSku);

    /**
     * 批量新增物料SKU
     *
     * @param skuList SKU列表
     * @return 结果
     */
    public int insertMaterialSkuBatch(List<MaterialSku> skuList);

    /**
     * 修改物料SKU
     *
     * @param materialSku 物料SKU
     * @return 结果
     */
    public int updateMaterialSku(MaterialSku materialSku);

    /**
     * 删除物料SKU
     *
     * @param id SKU主键
     * @return 结果
     */
    public int deleteMaterialSkuById(Long id);

    /**
     * 批量删除物料SKU
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialSkuByIds(Long[] ids);

    /**
     * 逻辑删除物料下的所有SKU
     *
     * @param materialId 物料ID
     * @return 结果
     */
    public int deleteMaterialSkuByMaterialId(Long materialId);
}
