package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.ProductSerial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Product serial mapper.
 *
 * @author ruoyi
 */
@Mapper
public interface ProductSerialMapper {
    /**
     * Query one product serial by id.
     *
     * @param id product serial id
     * @return product serial
     */
    ProductSerial selectProductSerialById(Long id);

    /**
     * Query product serial list.
     *
     * @param productSerial filter
     * @return product serial list
     */
    List<ProductSerial> selectProductSerialList(ProductSerial productSerial);

    /**
     * Insert one product serial.
     *
     * @param productSerial product serial
     * @return affected rows
     */
    int insertProductSerial(ProductSerial productSerial);

    /**
     * Batch insert product serials.
     *
     * @param serialList product serial list
     * @return affected rows
     */
    int batchInsertProductSerial(@Param("list") List<ProductSerial> serialList);

    /**
     * Update one product serial.
     *
     * @param productSerial product serial
     * @return affected rows
     */
    int updateProductSerial(ProductSerial productSerial);

    /**
     * Delete one product serial by id.
     *
     * @param id product serial id
     * @return affected rows
     */
    int deleteProductSerialById(Long id);

    /**
     * Batch delete product serials by ids.
     *
     * @param ids ids to delete
     * @return affected rows
     */
    int deleteProductSerialByIds(Long[] ids);

    /**
     * Query product serial list by job id.
     *
     * @param jobId job id
     * @return product serial list
     */
    List<ProductSerial> selectProductSerialByJobId(Long jobId);

    /**
     * Query product serial by serial number.
     *
     * @param serialNo serial number
     * @return product serial
     */
    ProductSerial selectProductSerialBySerialNo(String serialNo);

    /**
     * Count product serials by job id.
     *
     * @param jobId job id
     * @return count
     */
    int countProductSerialByJobId(Long jobId);

    /**
     * Query full trace view by sales order id.
     */
    @Select("SELECT * FROM v_erp_product_trace WHERE sales_order_id = #{salesOrderId} ORDER BY serial_id")
    List<Map<String, Object>> selectTraceByOrderId(@Param("salesOrderId") Long salesOrderId);

    /**
     * Query aggregated style progress by style code.
     */
    @Select("SELECT * FROM v_erp_style_progress WHERE style_code = #{styleCode}")
    Map<String, Object> selectStyleProgress(@Param("styleCode") String styleCode);
}
