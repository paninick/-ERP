package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.ProductSerial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 单件流水号Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface ProductSerialMapper {
    /**
     * 查询单件流水号
     *
     * @param id 单件流水号ID
     * @return 单件流水号
     */
    public ProductSerial selectProductSerialById(Long id);

    /**
     * 查询单件流水号列表
     *
     * @param productSerial 单件流水号
     * @return 单件流水号集合
     */
    public List<ProductSerial> selectProductSerialList(ProductSerial productSerial);

    /**
     * 新增单件流水号
     *
     * @param productSerial 单件流水号
     * @return 结果
     */
    public int insertProductSerial(ProductSerial productSerial);

    /**
     * 批量新增单件流水号
     *
     * @param serialList 单件流水号列表
     * @return 结果
     */
    public int batchInsertProductSerial(@Param("list") List<ProductSerial> serialList);

    /**
     * 修改单件流水号
     *
     * @param productSerial 单件流水号
     * @return 结果
     */
    public int updateProductSerial(ProductSerial productSerial);

    /**
     * 删除单件流水号
     *
     * @param id 单件流水号ID
     * @return 结果
     */
    public int deleteProductSerialById(Long id);

    /**
     * 批量删除单件流水号
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProductSerialByIds(Long[] ids);

    /**
     * 根据工票ID查询单件流水号列表
     *
     * @param jobId 工票ID
     * @return 单件流水号列表
     */
    public List<ProductSerial> selectProductSerialByJobId(Long jobId);

    /**
     * 根据流水号查询
     *
     * @param serialNo 流水号
     * @return 单件流水号
     */
    public ProductSerial selectProductSerialBySerialNo(String serialNo);

    /**
     * 根据工票ID计数
     *
     * @param jobId 工票ID
     * @return 数量
     */
    public int countProductSerialByJobId(Long jobId);

    /**
     * 全链路追溯查询：按销售订单ID，返回 v_erp_product_trace 视图数据
     */
    @Select("SELECT * FROM v_erp_product_trace WHERE sales_order_id = #{salesOrderId} ORDER BY serial_id")
    List<Map<String, Object>> selectTraceByOrderId(@Param("salesOrderId") Long salesOrderId);

    /**
     * 按款号（style_no）追溯完整生产链路，返回汇总统计
     */
    @Select("SELECT * FROM v_erp_style_progress WHERE style_no = #{styleNo}")
    Map<String, Object> selectStyleProgress(@Param("styleNo") String styleNo);
}
