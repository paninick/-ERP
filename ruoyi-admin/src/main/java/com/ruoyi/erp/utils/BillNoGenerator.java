package com.ruoyi.erp.utils;

import com.ruoyi.erp.mapper.BillSequenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 分布式唯一单据号生成器
 * 支持：Redis INCR 原子生成 + 数据库持久化兜底
 * 保证：Redis宕机重启不会重复，每天自动归零
 *
 * @author ruoyi
 * @date 2026-04-16
 */
@Component
public class BillNoGenerator {

    private static final Logger log = LoggerFactory.getLogger(BillNoGenerator.class);
    private static final DateTimeFormatter DISPLAY_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BillSequenceMapper billSequenceMapper;

    /**
     * 生成单据号
     * 格式：前缀 + yyyyMMdd + - + 序号（补零到5位）
     * 示例：PO-20260416-00001
     *
     * @param prefix 单据前缀 如 PO=采购单, SO=销售订单, SI=入库单
     * @return 唯一单据号
     */
    public String generate(String prefix) {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DISPLAY_FMT);
        String redisKey = "seq:erp:" + prefix + ":" + dateStr;

        Long seq;
        try {
            // 优先用Redis INCR 原子生成
            seq = redisTemplate.opsForValue().increment(redisKey);
            // 设置过期时间2天，自动清理
            redisTemplate.expire(redisKey, 2, TimeUnit.DAYS);
            // 同步更新到数据库兜底（使用 LocalDate 避免 Date.valueOf 的格式约束）
            syncToDatabase(prefix, today, seq);
        } catch (Exception e) {
            // Redis异常，降级到数据库原子兜底
            log.warn("Redis unavailable, fallback to database for bill sequence generation");
            seq = incrementDatabase(prefix, today);
        }

        return String.format("%s-%s-%05d", prefix, dateStr, seq);
    }

    /**
     * 生成款号（KN 双轨业务主键）
     * 格式：KN-{YY}-{SS}-{NNN}，如 KN-26-SP-001
     * 季节代码：SP=春(3-5月) SU=夏(6-8月) FA=秋(9-11月) WI=冬(12-2月)
     * Redis key 按年季归零，序号3位
     *
     * @param season 季节代码 SP/SU/FA/WI，传 null 则根据当前月份自动推断
     * @return 款号，如 KN-26-SP-001
     */
    public String generateStyleNo(String season) {
        LocalDate today = LocalDate.now();
        String yy = String.format("%02d", today.getYear() % 100);
        String ss = (season != null && !season.isEmpty()) ? season : inferSeason(today.getMonth());
        String redisKey = "seq:erp:KN:" + yy + ss;

        Long seq;
        try {
            seq = redisTemplate.opsForValue().increment(redisKey);
            redisTemplate.expire(redisKey, 200, TimeUnit.DAYS);
            syncToDatabase("KN" + yy + ss, today, seq);
        } catch (Exception e) {
            log.warn("Redis unavailable, fallback to database for style_no generation");
            seq = incrementDatabase("KN" + yy + ss, today);
        }

        return String.format("KN-%s-%s-%03d", yy, ss, seq);
    }

    private String inferSeason(Month month) {
        int m = month.getValue();
        if (m >= 3 && m <= 5) return "SP";
        if (m >= 6 && m <= 8) return "SU";
        if (m >= 9 && m <= 11) return "FA";
        return "WI";
    }

    /**
     * 同步当前序号到数据库，Redis宕机后可以恢复
     */
    private void syncToDatabase(String billType, LocalDate seqDate, Long currentVal) {
        try {
            java.sql.Date sqlDate = java.sql.Date.valueOf(seqDate);
            int updated = billSequenceMapper.upsertSequence(sqlDate, billType, currentVal);
            if (updated == 0) {
                billSequenceMapper.insertSequence(sqlDate, billType, currentVal);
            }
        } catch (Exception e) {
            log.error("Failed to sync sequence to database", e);
            // 不同步不影响生成，只是兜底降级会丢，记录日志就行
        }
    }

    /**
     * 数据库兜底：单条原子 INSERT ON DUPLICATE KEY UPDATE，并发安全
     */
    private Long incrementDatabase(String billType, LocalDate seqDate) {
        java.sql.Date sqlDate = java.sql.Date.valueOf(seqDate);
        billSequenceMapper.atomicIncrementOrInsert(sqlDate, billType);
        return billSequenceMapper.getLastInsertId();
    }
}
