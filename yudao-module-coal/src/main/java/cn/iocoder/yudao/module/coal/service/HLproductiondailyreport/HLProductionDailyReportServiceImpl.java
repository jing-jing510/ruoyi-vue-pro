package cn.iocoder.yudao.module.coal.service.HLproductiondailyreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionDailyReportPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionDailyReportSaveReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionPlanProgressRespVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.HLproductiondailyreport.HLProductionDailyReportDO;
import cn.iocoder.yudao.module.coal.dal.mysql.HLproductiondailyreport.HLProductionDailyReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.HL_PRODUCTION_DAILY_REPORT_NOT_EXISTS;

/**
 * 红林生产日报 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class HLProductionDailyReportServiceImpl implements HLProductionDailyReportService {

    @Resource
    private HLProductionDailyReportMapper hLProductionDailyReportMapper;

    @Override
    public Long createHLProductionDailyReport(HLProductionDailyReportSaveReqVO createReqVO) {
        // 插入
        HLProductionDailyReportDO hLProductionDailyReport = BeanUtils.toBean(createReqVO, HLProductionDailyReportDO.class);
        hLProductionDailyReportMapper.insert(hLProductionDailyReport);

        // 返回
        return hLProductionDailyReport.getId();
    }

    @Override
    public void updateHLProductionDailyReport(HLProductionDailyReportSaveReqVO updateReqVO) {
        // 校验存在
        validateHLProductionDailyReportExists(updateReqVO.getId());
        // 更新
        HLProductionDailyReportDO updateObj = BeanUtils.toBean(updateReqVO, HLProductionDailyReportDO.class);
        hLProductionDailyReportMapper.updateById(updateObj);
    }

    @Override
    public void deleteHLProductionDailyReport(Long id) {
        // 校验存在
        validateHLProductionDailyReportExists(id);
        // 删除
        hLProductionDailyReportMapper.deleteById(id);
    }

    @Override
        public void deleteHLProductionDailyReportListByIds(List<Long> ids) {
        // 删除
        hLProductionDailyReportMapper.deleteByIds(ids);
        }


    private void validateHLProductionDailyReportExists(Long id) {
        if (hLProductionDailyReportMapper.selectById(id) == null) {
            throw exception(HL_PRODUCTION_DAILY_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public HLProductionDailyReportDO getHLProductionDailyReport(Long id) {
        return hLProductionDailyReportMapper.selectById(id);
    }

    @Override
    public PageResult<HLProductionDailyReportDO> getHLProductionDailyReportPage(HLProductionDailyReportPageReqVO pageReqVO) {
        return hLProductionDailyReportMapper.selectPage(pageReqVO);
    }

    @Override
    @TenantIgnore
    public HLProductionPlanProgressRespVO getHLPlanProgress() {
        HLProductionPlanProgressRespVO progress = new HLProductionPlanProgressRespVO();
        
        // 获取当前年月
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        
        progress.setYear(currentYear);
        progress.setMonth(currentMonth);
        
        try {
            // 计算月度计划进展
            calculateMonthlyProgress(progress, currentYear, currentMonth);
            
            // 计算年度计划进展
            calculateYearlyProgress(progress, currentYear);
            
        } catch (Exception e) {
            System.out.println("计算红林生产计划进展失败: " + e.getMessage());
            // 设置默认值为0
            progress.setMonthlyPlan(BigDecimal.ZERO);
            progress.setMonthlyActual(BigDecimal.ZERO);
            progress.setMonthlyProgressPercentage(BigDecimal.ZERO);
            progress.setYearlyPlan(BigDecimal.ZERO);
            progress.setYearlyActual(BigDecimal.ZERO);
            progress.setYearlyProgressPercentage(BigDecimal.ZERO);
        }
        
        return progress;
    }
    
    /**
     * 计算月度计划进展
     */
    private void calculateMonthlyProgress(HLProductionPlanProgressRespVO progress, int year, int month) {
        // 注意：红林生产日报没有计划表，所以计划值设为0
        // 只计算实际产量（入洗煤量）
        BigDecimal monthlyPlan = BigDecimal.ZERO;
        
        // 获取当月实际产量（入洗煤量）
        LocalDateTime monthStart = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime monthEnd = LocalDateTime.of(year, month, LocalDate.now().getDayOfMonth(), 23, 59, 59);
        List<HLProductionDailyReportDO> monthlyReports = hLProductionDailyReportMapper.selectReportsByDateRange(monthStart, monthEnd);
        
        BigDecimal monthlyActual = monthlyReports.stream()
            .filter(report -> report.getRawCoalInput() != null)
            .map(HLProductionDailyReportDO::getRawCoalInput)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 计算月度完成百分比
        BigDecimal monthlyProgressPercentage = BigDecimal.ZERO;
        if (monthlyPlan.compareTo(BigDecimal.ZERO) > 0) {
            monthlyProgressPercentage = monthlyActual.divide(monthlyPlan, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        
        progress.setMonthlyPlan(monthlyPlan);
        progress.setMonthlyActual(monthlyActual);
        progress.setMonthlyProgressPercentage(monthlyProgressPercentage);
    }
    
    /**
     * 计算年度计划进展
     */
    private void calculateYearlyProgress(HLProductionPlanProgressRespVO progress, int year) {
        // 注意：红林生产日报没有计划表，所以计划值设为0
        // 只计算实际产量（入洗煤量）
        BigDecimal yearlyPlan = BigDecimal.ZERO;
        
        // 获取当年实际产量（本年1月1日到现在的累计产量）
        LocalDateTime yearStart = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        List<HLProductionDailyReportDO> yearlyReports = hLProductionDailyReportMapper.selectReportsByDateRange(yearStart, now);
        
        BigDecimal yearlyActual = yearlyReports.stream()
            .filter(report -> report.getRawCoalInput() != null)
            .map(HLProductionDailyReportDO::getRawCoalInput)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 计算年度完成百分比
        BigDecimal yearlyProgressPercentage = BigDecimal.ZERO;
        if (yearlyPlan.compareTo(BigDecimal.ZERO) > 0) {
            yearlyProgressPercentage = yearlyActual.divide(yearlyPlan, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        
        progress.setYearlyPlan(yearlyPlan);
        progress.setYearlyActual(yearlyActual);
        progress.setYearlyProgressPercentage(yearlyProgressPercentage);
    }

}