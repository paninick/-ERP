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
 * Distributed bill number generator backed by Redis and database fallback.
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
     * Generate a bill number with date and 5-digit sequence.
     *
     * @param prefix bill prefix such as PO, SO or SI
     * @return generated bill number
     */
    public String generate(String prefix) {
        LocalDate today = LocalDate.now();
        String dateStr = today.format(DISPLAY_FMT);
        String redisKey = "seq:erp:" + prefix + ":" + dateStr;

        Long seq;
        try {
            seq = redisTemplate.opsForValue().increment(redisKey);
            redisTemplate.expire(redisKey, 2, TimeUnit.DAYS);
            syncToDatabase(prefix, today, seq);
        } catch (Exception e) {
            log.warn("Redis unavailable, fallback to database for bill sequence generation");
            seq = incrementDatabase(prefix, today);
        }

        return String.format("%s-%s-%05d", prefix, dateStr, seq);
    }

    /**
     * Generate a style code in KN-YY-SS-NNN format.
     *
     * @param season season code SP, SU, FA or WI; null means infer from current month
     * @return generated style code
     */
    public String generateStyleCode(String season) {
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
            log.warn("Redis unavailable, fallback to database for style code generation");
            seq = incrementDatabase("KN" + yy + ss, today);
        }

        return String.format("KN-%s-%s-%03d", yy, ss, seq);
    }

    private String inferSeason(Month month) {
        int m = month.getValue();
        if (m >= 3 && m <= 5) {
            return "SP";
        }
        if (m >= 6 && m <= 8) {
            return "SU";
        }
        if (m >= 9 && m <= 11) {
            return "FA";
        }
        return "WI";
    }

    /**
     * Persist current sequence value so Redis restart can recover.
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
        }
    }

    /**
     * Database fallback using atomic insert-or-update semantics.
     */
    private Long incrementDatabase(String billType, LocalDate seqDate) {
        java.sql.Date sqlDate = java.sql.Date.valueOf(seqDate);
        billSequenceMapper.atomicIncrementOrInsert(sqlDate, billType);
        return billSequenceMapper.getLastInsertId();
    }
}
