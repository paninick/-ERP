package com.ruoyi.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.demo.domain.DemoSchedule;
import com.ruoyi.demo.mapper.DemoScheduleMapper;
import com.ruoyi.demo.service.IDemoScheduleService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DemoScheduleServiceImpl extends ServiceImpl<DemoScheduleMapper, DemoSchedule> implements IDemoScheduleService {

    @Override
    public IPage<DemoSchedule> selectDemoSchedulePage(DemoSchedule demoSchedule, Integer page, Integer size) {
        Page<DemoSchedule> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<DemoSchedule> wrapper = buildQueryWrapper(demoSchedule);
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<DemoSchedule> selectDemoScheduleList(DemoSchedule demoSchedule) {
        return this.list(buildQueryWrapper(demoSchedule));
    }

    @Override
    public DemoSchedule selectDemoScheduleById(Long id) {
        return this.getById(id);
    }

    @Override
    public boolean insertDemoSchedule(DemoSchedule demoSchedule) {
        recalcLoad(demoSchedule);
        if (demoSchedule.getScheduleStatus() == null) demoSchedule.setScheduleStatus("1");
        if (demoSchedule.getConflictFlag() == null) demoSchedule.setConflictFlag("0");
        if (demoSchedule.getPriority() == null) demoSchedule.setPriority(5);
        return this.save(demoSchedule);
    }

    @Override
    public boolean updateDemoSchedule(DemoSchedule demoSchedule) {
        recalcLoad(demoSchedule);
        return this.updateById(demoSchedule);
    }

    @Override
    public boolean deleteDemoScheduleById(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteDemoScheduleByIds(Long[] ids) {
        if (ids == null || ids.length == 0) return false;
        return this.removeBatchByIds(List.of(ids));
    }

    @Override
    public BigDecimal calculateLoadRate(DemoSchedule demoSchedule) {
        if (demoSchedule.getPlanQty() == null || demoSchedule.getCapacity() == null || demoSchedule.getCapacity() == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(demoSchedule.getPlanQty())
                .divide(BigDecimal.valueOf(demoSchedule.getCapacity()), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public boolean isFullLoad(DemoSchedule demoSchedule) {
        return calculateLoadRate(demoSchedule).compareTo(new BigDecimal("100")) > 0;
    }

    @Override
    public boolean isIdleLoad(DemoSchedule demoSchedule) {
        return calculateLoadRate(demoSchedule).compareTo(new BigDecimal("60")) < 0;
    }

    @Override
    public BigDecimal calculateCapacityUtilization(DemoSchedule demoSchedule) {
        BigDecimal rate = calculateLoadRate(demoSchedule);
        return rate.compareTo(new BigDecimal("100")) > 0 ? new BigDecimal("100") : rate;
    }

    @Override
    public List<DemoSchedule> selectGanttList(String startDate, String endDate, String process) {
        LambdaQueryWrapper<DemoSchedule> wrapper = new LambdaQueryWrapper<>();
        if (startDate != null && !startDate.isEmpty()) {
            wrapper.ge(DemoSchedule::getStartDate, LocalDate.parse(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            wrapper.le(DemoSchedule::getDueDate, LocalDate.parse(endDate));
        }
        if (process != null && !process.isEmpty()) {
            wrapper.like(DemoSchedule::getProcess, process);
        }
        wrapper.orderByAsc(DemoSchedule::getPriority).orderByAsc(DemoSchedule::getStartDate);
        return this.list(wrapper);
    }

    @Override
    public boolean detectAndMarkConflicts(Long id) {
        DemoSchedule s = this.getById(id);
        if (s == null) return false;
        String flag = "0";
        if (s.getLoad() != null && s.getLoad().compareTo(new BigDecimal("100")) > 0) {
            flag = "1"; // 产能冲突
        } else if (s.getDueDate() != null && s.getDueDate().isBefore(LocalDate.now())
                && !"3".equals(s.getScheduleStatus())) {
            flag = "2"; // 日期冲突
        }
        s.setConflictFlag(flag);
        this.updateById(s);
        return !"0".equals(flag);
    }

    @Override
    public int batchDetectConflicts() {
        LambdaQueryWrapper<DemoSchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(DemoSchedule::getScheduleStatus, "3");
        List<DemoSchedule> list = this.list(wrapper);
        int count = 0;
        for (DemoSchedule s : list) {
            if (detectAndMarkConflicts(s.getId())) count++;
        }
        return count;
    }

    @Override
    public boolean reschedule(Long id, String newStartDate, String newDueDate) {
        DemoSchedule s = this.getById(id);
        if (s == null) return false;
        if (newStartDate != null && !newStartDate.isEmpty()) s.setStartDate(LocalDate.parse(newStartDate));
        if (newDueDate != null && !newDueDate.isEmpty()) s.setDueDate(LocalDate.parse(newDueDate));
        s.setScheduleStatus("1");
        s.setConflictFlag("0");
        this.updateById(s);
        detectAndMarkConflicts(id);
        return true;
    }

    private void recalcLoad(DemoSchedule s) {
        if (s.getPlanQty() != null && s.getCapacity() != null && s.getCapacity() > 0) {
            s.setLoad(calculateLoadRate(s));
        }
    }

    private LambdaQueryWrapper<DemoSchedule> buildQueryWrapper(DemoSchedule demoSchedule) {
        LambdaQueryWrapper<DemoSchedule> wrapper = new LambdaQueryWrapper<>();
        if (demoSchedule.getOrderId() != null) wrapper.eq(DemoSchedule::getOrderId, demoSchedule.getOrderId());
        if (demoSchedule.getProcess() != null && !demoSchedule.getProcess().isEmpty())
            wrapper.like(DemoSchedule::getProcess, demoSchedule.getProcess());
        if (demoSchedule.getWorkDay() != null && !demoSchedule.getWorkDay().isEmpty())
            wrapper.eq(DemoSchedule::getWorkDay, demoSchedule.getWorkDay());
        if (demoSchedule.getScheduleStatus() != null && !demoSchedule.getScheduleStatus().isEmpty())
            wrapper.eq(DemoSchedule::getScheduleStatus, demoSchedule.getScheduleStatus());
        wrapper.orderByAsc(DemoSchedule::getPriority).orderByAsc(DemoSchedule::getStartDate);
        return wrapper;
    }
}
