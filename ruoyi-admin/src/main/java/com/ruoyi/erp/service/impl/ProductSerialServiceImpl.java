package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.ProductSerial;
import com.ruoyi.erp.mapper.ProductSerialMapper;
import com.ruoyi.erp.service.IProductSerialService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单件流水号Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ProductSerialServiceImpl implements IProductSerialService {

    @Autowired
    private ProductSerialMapper productSerialMapper;

    /**
     * 查询单件流水号
     *
     * @param id 单件流水号ID
     * @return 单件流水号
     */
    @Override
    public ProductSerial selectProductSerialById(Long id) {
        return productSerialMapper.selectProductSerialById(id);
    }

    /**
     * 查询单件流水号列表
     *
     * @param productSerial 单件流水号
     * @return 单件流水号
     */
    @Override
    public List<ProductSerial> selectProductSerialList(ProductSerial productSerial) {
        return productSerialMapper.selectProductSerialList(productSerial);
    }

    /**
     * 新增单件流水号
     *
     * @param productSerial 单件流水号
     * @return 结果
     */
    @Override
    public int insertProductSerial(ProductSerial productSerial) {
        productSerial.setCreateTime(DateUtils.getNowDate());
        return productSerialMapper.insertProductSerial(productSerial);
    }

    /**
     * 批量新增单件流水号
     *
     * @param serialList 单件流水号列表
     * @return 结果
     */
    @Override
    public int batchInsertProductSerial(List<ProductSerial> serialList) {
        for (ProductSerial serial : serialList) {
            serial.setCreateTime(DateUtils.getNowDate());
        }
        return productSerialMapper.batchInsertProductSerial(serialList);
    }

    /**
     * 修改单件流水号
     *
     * @param productSerial 单件流水号
     * @return 结果
     */
    @Override
    public int updateProductSerial(ProductSerial productSerial) {
        return productSerialMapper.updateProductSerial(productSerial);
    }

    /**
     * 批量删除单件流水号
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductSerialByIds(Long[] ids) {
        return productSerialMapper.deleteProductSerialByIds(ids);
    }

    /**
     * 删除单件流水号信息
     *
     * @param id 单件流水号ID
     * @return 结果
     */
    @Override
    public int deleteProductSerialById(Long id) {
        return productSerialMapper.deleteProductSerialById(id);
    }

    @Override
    public List<ProductSerial> selectProductSerialByJobId(Long jobId) {
        return productSerialMapper.selectProductSerialByJobId(jobId);
    }

    @Override
    public ProductSerial selectProductSerialBySerialNo(String serialNo) {
        return productSerialMapper.selectProductSerialBySerialNo(serialNo);
    }

    @Override
    public int countProductSerialByJobId(Long jobId) {
        return productSerialMapper.countProductSerialByJobId(jobId);
    }
}
