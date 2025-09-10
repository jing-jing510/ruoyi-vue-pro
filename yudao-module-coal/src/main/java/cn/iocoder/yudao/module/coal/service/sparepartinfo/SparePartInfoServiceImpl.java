package cn.iocoder.yudao.module.coal.service.sparepartinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartInfoPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartInfoSaveReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartStockStatisticsRespVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinfo.SparePartInfoDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartusagerecord.SparePartUsageRecordDO;
import cn.iocoder.yudao.module.coal.dal.mysql.sparepartinfo.SparePartInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SPARE_PART_INFO_NOT_EXISTS;

/**
 * 备件基础信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SparePartInfoServiceImpl implements SparePartInfoService {

    @Resource
    private SparePartInfoMapper sparePartInfoMapper;

    @Resource
    private cn.iocoder.yudao.module.coal.service.sparepartstock.SparePartStockService sparePartStockService;

    @Resource
    private cn.iocoder.yudao.module.coal.service.sparepartcategory.SparePartCategoryService sparePartCategoryService;

    @Resource
    private cn.iocoder.yudao.module.coal.dal.mysql.sparepartusagerecord.SparePartUsageRecordMapper sparePartUsageRecordMapper;

    @Override
    public Long createSparePartInfo(SparePartInfoSaveReqVO createReqVO) {
        // 插入
        SparePartInfoDO sparePartInfo = BeanUtils.toBean(createReqVO, SparePartInfoDO.class);
        sparePartInfoMapper.insert(sparePartInfo);

        // 返回
        return sparePartInfo.getId();
    }

    @Override
    public void updateSparePartInfo(SparePartInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateSparePartInfoExists(updateReqVO.getId());
        // 更新
        SparePartInfoDO updateObj = BeanUtils.toBean(updateReqVO, SparePartInfoDO.class);
        sparePartInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteSparePartInfo(Long id) {
        // 校验存在
        validateSparePartInfoExists(id);
        // 删除
        sparePartInfoMapper.deleteById(id);
    }

    @Override
        public void deleteSparePartInfoListByIds(List<Long> ids) {
        // 删除
        sparePartInfoMapper.deleteByIds(ids);
        }


    private void validateSparePartInfoExists(Long id) {
        if (sparePartInfoMapper.selectById(id) == null) {
            throw exception(SPARE_PART_INFO_NOT_EXISTS);
        }
    }

    @Override
    public SparePartInfoDO getSparePartInfo(Long id) {
        return sparePartInfoMapper.selectById(id);
    }

    @Override
    public PageResult<SparePartInfoDO> getSparePartInfoPage(SparePartInfoPageReqVO pageReqVO) {
        return sparePartInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SparePartInfoDO> getSimpleSparePartList() {
        return sparePartInfoMapper.selectList();
    }

    @Override
    public SparePartStockStatisticsRespVO getStockStatistics() {
        SparePartStockStatisticsRespVO statistics = new SparePartStockStatisticsRespVO();
        
        // 获取所有备件信息
        List<SparePartInfoDO> allSpareParts = sparePartInfoMapper.selectList();
        
        // 计算库存概览
        statistics.setOverview(calculateStockOverview(allSpareParts));
        
        // 计算库存预警统计
        statistics.setAlertStatistics(calculateAlertStatistics(allSpareParts));
        
        // 计算分类统计
        statistics.setCategoryStatistics(calculateCategoryStatistics(allSpareParts));
        
        // 计算趋势数据（最近30天）
        statistics.setTrendData(calculateTrendData());
        
        // 计算ABC分类统计
        statistics.setAbcStatistics(calculateABCStatistics(allSpareParts));
        
        return statistics;
    }

    /**
     * 计算库存概览
     */
    private SparePartStockStatisticsRespVO.StockOverview calculateStockOverview(List<SparePartInfoDO> spareParts) {
        SparePartStockStatisticsRespVO.StockOverview overview = new SparePartStockStatisticsRespVO.StockOverview();
        
        overview.setTotalSparePartTypes(spareParts.size());
        
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalValue = BigDecimal.ZERO;
        int healthyCount = 0;
        
        for (SparePartInfoDO sparePart : spareParts) {
            try {
                BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePart.getId());
                if (currentStock != null) {
                    totalQuantity = totalQuantity.add(currentStock);
                }
                
                if (sparePart.getUnitPrice() != null && currentStock != null) {
                    BigDecimal stockValue = currentStock.multiply(sparePart.getUnitPrice());
                    totalValue = totalValue.add(stockValue);
                }
                
                // 判断库存健康度 - 添加null值检查
                if (currentStock != null && sparePart.getMinStock() != null && sparePart.getMaxStock() != null) {
                    if (currentStock.compareTo(sparePart.getMinStock()) > 0 && 
                        currentStock.compareTo(sparePart.getMaxStock()) <= 0) {
                        healthyCount++;
                    }
                }
            } catch (Exception e) {
                // 记录异常但继续处理其他备件
                System.err.println("处理备件 " + sparePart.getId() + " 时发生异常: " + e.getMessage());
            }
        }
        
        overview.setTotalStockQuantity(totalQuantity);
        overview.setTotalStockValue(totalValue);
        overview.setAverageTurnoverRate(BigDecimal.ZERO); // TODO: 实现周转率计算
        overview.setHealthScore(spareParts.isEmpty() ? 0 : (healthyCount * 100 / spareParts.size()));
        
        return overview;
    }

    /**
     * 计算库存预警统计
     */
    private SparePartStockStatisticsRespVO.StockAlertStatistics calculateAlertStatistics(List<SparePartInfoDO> spareParts) {
        SparePartStockStatisticsRespVO.StockAlertStatistics alertStats = new SparePartStockStatisticsRespVO.StockAlertStatistics();
        
        int lowStockCount = 0;
        int zeroStockCount = 0;
        int overStockCount = 0;
        int stagnantStockCount = 0;
        
        for (SparePartInfoDO sparePart : spareParts) {
            try {
                BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePart.getId());
                
                if (currentStock != null) {
                    if (currentStock.compareTo(BigDecimal.ZERO) == 0) {
                        zeroStockCount++;
                    } else {
                        // 检查低库存 - 需要minStock不为null
                        if (sparePart.getMinStock() != null && currentStock.compareTo(sparePart.getMinStock()) <= 0) {
                            lowStockCount++;
                        }
                        
                        // 检查超库存 - 需要maxStock不为null
                        if (sparePart.getMaxStock() != null && currentStock.compareTo(sparePart.getMaxStock()) > 0) {
                            overStockCount++;
                        }
                    }
                }
                
                // TODO: 实现呆滞库存判断逻辑
            } catch (Exception e) {
                // 记录异常但继续处理其他备件
                System.err.println("处理备件 " + sparePart.getId() + " 预警统计时发生异常: " + e.getMessage());
            }
        }
        
        alertStats.setLowStockCount(lowStockCount);
        alertStats.setZeroStockCount(zeroStockCount);
        alertStats.setOverStockCount(overStockCount);
        alertStats.setStagnantStockCount(stagnantStockCount);
        
        return alertStats;
    }

    /**
     * 计算分类统计
     */
    private List<SparePartStockStatisticsRespVO.CategoryStatistics> calculateCategoryStatistics(List<SparePartInfoDO> spareParts) {
        Map<Long, List<SparePartInfoDO>> categoryMap = spareParts.stream()
                .collect(Collectors.groupingBy(SparePartInfoDO::getCategoryId));
        
        List<SparePartStockStatisticsRespVO.CategoryStatistics> categoryStats = new ArrayList<>();
        
        for (Map.Entry<Long, List<SparePartInfoDO>> entry : categoryMap.entrySet()) {
            SparePartStockStatisticsRespVO.CategoryStatistics stat = new SparePartStockStatisticsRespVO.CategoryStatistics();
            stat.setCategoryId(entry.getKey());
            stat.setSparePartCount(entry.getValue().size());
            
            BigDecimal totalQuantity = BigDecimal.ZERO;
            BigDecimal totalValue = BigDecimal.ZERO;
            
            for (SparePartInfoDO sparePart : entry.getValue()) {
                try {
                    BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePart.getId());
                    if (currentStock != null) {
                        totalQuantity = totalQuantity.add(currentStock);
                    }
                    
                    if (sparePart.getUnitPrice() != null && currentStock != null) {
                        BigDecimal stockValue = currentStock.multiply(sparePart.getUnitPrice());
                        totalValue = totalValue.add(stockValue);
                    }
                } catch (Exception e) {
                    // 记录异常但继续处理其他备件
                    System.err.println("处理备件 " + sparePart.getId() + " 分类统计时发生异常: " + e.getMessage());
                }
            }
            
            stat.setStockQuantity(totalQuantity);
            stat.setStockValue(totalValue);
            stat.setPercentage(BigDecimal.ZERO); // TODO: 计算占比
            
            categoryStats.add(stat);
        }
        
        return categoryStats;
    }

    /**
     * 计算趋势数据
     */
    private List<SparePartStockStatisticsRespVO.TrendData> calculateTrendData() {
        List<SparePartStockStatisticsRespVO.TrendData> trendData = new ArrayList<>();
        
        // 生成最近30天的趋势数据
        LocalDate endDate = LocalDate.now();
        for (int i = 29; i >= 0; i--) {
            LocalDate date = endDate.minusDays(i);
            SparePartStockStatisticsRespVO.TrendData data = new SparePartStockStatisticsRespVO.TrendData();
            data.setDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            data.setInQuantity(BigDecimal.ZERO); // TODO: 实现实际数据查询
            data.setOutQuantity(BigDecimal.ZERO);
            data.setStockQuantity(BigDecimal.ZERO);
            data.setStockValue(BigDecimal.ZERO);
            trendData.add(data);
        }
        
        return trendData;
    }

    /**
     * 计算ABC分类统计
     */
    private List<SparePartStockStatisticsRespVO.ABCCategoryStatistics> calculateABCStatistics(List<SparePartInfoDO> spareParts) {
        List<SparePartStockStatisticsRespVO.ABCCategoryStatistics> abcStats = new ArrayList<>();
        
        try {
            // 计算每个备件的库存价值，添加异常处理
            List<SparePartInfoDO> sparePartsWithValue = new ArrayList<>();
            for (SparePartInfoDO sparePart : spareParts) {
                try {
                    if (sparePart.getUnitPrice() != null) {
                        BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePart.getId());
                        if (currentStock != null) {
                            sparePartsWithValue.add(sparePart);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("处理备件 " + sparePart.getId() + " ABC分类时发生异常: " + e.getMessage());
                }
            }
            
            if (sparePartsWithValue.isEmpty()) {
                return abcStats;
            }
            
            // 按库存价值排序
            sparePartsWithValue.sort((a, b) -> {
                try {
                    BigDecimal valueA = sparePartStockService.getCurrentStock(a.getId()).multiply(a.getUnitPrice());
                    BigDecimal valueB = sparePartStockService.getCurrentStock(b.getId()).multiply(b.getUnitPrice());
                    return valueB.compareTo(valueA);
                } catch (Exception e) {
                    return 0;
                }
            });
            
            BigDecimal totalValue = BigDecimal.ZERO;
            for (SparePartInfoDO sparePart : sparePartsWithValue) {
                try {
                    BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePart.getId());
                    if (currentStock != null) {
                        totalValue = totalValue.add(currentStock.multiply(sparePart.getUnitPrice()));
                    }
                } catch (Exception e) {
                    System.err.println("计算总价值时处理备件 " + sparePart.getId() + " 发生异常: " + e.getMessage());
                }
            }
            
            // A类：前20%的备件
            int aCount = Math.max(1, sparePartsWithValue.size() * 20 / 100);
            SparePartStockStatisticsRespVO.ABCCategoryStatistics aStats = new SparePartStockStatisticsRespVO.ABCCategoryStatistics();
            aStats.setCategory("A类");
            aStats.setSparePartCount(aCount);
            
            BigDecimal aValue = BigDecimal.ZERO;
            for (int i = 0; i < aCount; i++) {
                try {
                    SparePartInfoDO sparePart = sparePartsWithValue.get(i);
                    BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePart.getId());
                    if (currentStock != null) {
                        aValue = aValue.add(currentStock.multiply(sparePart.getUnitPrice()));
                    }
                } catch (Exception e) {
                    System.err.println("计算A类价值时发生异常: " + e.getMessage());
                }
            }
            aStats.setStockValue(aValue);
            aStats.setValuePercentage(totalValue.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : 
                    aValue.multiply(BigDecimal.valueOf(100)).divide(totalValue, 2, BigDecimal.ROUND_HALF_UP));
            aStats.setQuantityPercentage(BigDecimal.valueOf(aCount * 100).divide(BigDecimal.valueOf(sparePartsWithValue.size()), 2, BigDecimal.ROUND_HALF_UP));
            abcStats.add(aStats);
            
            // B类：中间30%的备件
            int bCount = Math.max(1, sparePartsWithValue.size() * 30 / 100);
            SparePartStockStatisticsRespVO.ABCCategoryStatistics bStats = new SparePartStockStatisticsRespVO.ABCCategoryStatistics();
            bStats.setCategory("B类");
            bStats.setSparePartCount(bCount);
            
            BigDecimal bValue = BigDecimal.ZERO;
            for (int i = aCount; i < Math.min(aCount + bCount, sparePartsWithValue.size()); i++) {
                try {
                    SparePartInfoDO sparePart = sparePartsWithValue.get(i);
                    BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePart.getId());
                    if (currentStock != null) {
                        bValue = bValue.add(currentStock.multiply(sparePart.getUnitPrice()));
                    }
                } catch (Exception e) {
                    System.err.println("计算B类价值时发生异常: " + e.getMessage());
                }
            }
            bStats.setStockValue(bValue);
            bStats.setValuePercentage(totalValue.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : 
                    bValue.multiply(BigDecimal.valueOf(100)).divide(totalValue, 2, BigDecimal.ROUND_HALF_UP));
            bStats.setQuantityPercentage(BigDecimal.valueOf(bCount * 100).divide(BigDecimal.valueOf(sparePartsWithValue.size()), 2, BigDecimal.ROUND_HALF_UP));
            abcStats.add(bStats);
            
            // C类：剩余50%的备件
            int cCount = sparePartsWithValue.size() - aCount - bCount;
            if (cCount > 0) {
                SparePartStockStatisticsRespVO.ABCCategoryStatistics cStats = new SparePartStockStatisticsRespVO.ABCCategoryStatistics();
                cStats.setCategory("C类");
                cStats.setSparePartCount(cCount);
                
                BigDecimal cValue = BigDecimal.ZERO;
                for (int i = aCount + bCount; i < sparePartsWithValue.size(); i++) {
                    try {
                        SparePartInfoDO sparePart = sparePartsWithValue.get(i);
                        BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePart.getId());
                        if (currentStock != null) {
                            cValue = cValue.add(currentStock.multiply(sparePart.getUnitPrice()));
                        }
                    } catch (Exception e) {
                        System.err.println("计算C类价值时发生异常: " + e.getMessage());
                    }
                }
                cStats.setStockValue(cValue);
                cStats.setValuePercentage(totalValue.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : 
                        cValue.multiply(BigDecimal.valueOf(100)).divide(totalValue, 2, BigDecimal.ROUND_HALF_UP));
                cStats.setQuantityPercentage(BigDecimal.valueOf(cCount * 100).divide(BigDecimal.valueOf(sparePartsWithValue.size()), 2, BigDecimal.ROUND_HALF_UP));
                abcStats.add(cStats);
            }
        } catch (Exception e) {
            System.err.println("计算ABC分类统计时发生异常: " + e.getMessage());
        }
        
        return abcStats;
    }

    @Override
    public SparePartStockStatisticsRespVO.UsageFrequencyStatistics getUsageFrequencyAnalysis() {
        SparePartStockStatisticsRespVO.UsageFrequencyStatistics statistics = 
            new SparePartStockStatisticsRespVO.UsageFrequencyStatistics();
        
        List<SparePartInfoDO> allSpareParts = sparePartInfoMapper.selectList();
        List<SparePartStockStatisticsRespVO.UsageFrequencyAnalysis> frequencyAnalysis = new ArrayList<>();
        
        int highFrequencyCount = 0;
        int mediumFrequencyCount = 0;
        int lowFrequencyCount = 0;
        int unusedCount = 0;
        
        for (SparePartInfoDO sparePart : allSpareParts) {
            try {
                SparePartStockStatisticsRespVO.UsageFrequencyAnalysis analysis = 
                    calculateUsageFrequencyForSparePart(sparePart);
                
                if (analysis != null) {
                    frequencyAnalysis.add(analysis);
                    
                    // 统计各频率等级数量
                    switch (analysis.getFrequencyLevel()) {
                        case "高频":
                            highFrequencyCount++;
                            break;
                        case "中频":
                            mediumFrequencyCount++;
                            break;
                        case "低频":
                            lowFrequencyCount++;
                            break;
                        case "未使用":
                            unusedCount++;
                            break;
                    }
                }
            } catch (Exception e) {
                System.err.println("分析备件 " + sparePart.getId() + " 使用频率时发生异常: " + e.getMessage());
            }
        }
        
        statistics.setHighFrequencyCount(highFrequencyCount);
        statistics.setMediumFrequencyCount(mediumFrequencyCount);
        statistics.setLowFrequencyCount(lowFrequencyCount);
        statistics.setUnusedCount(unusedCount);
        statistics.setFrequencyAnalysis(frequencyAnalysis);
        
        return statistics;
    }

    /**
     * 计算单个备件的使用频率
     */
    private SparePartStockStatisticsRespVO.UsageFrequencyAnalysis calculateUsageFrequencyForSparePart(SparePartInfoDO sparePart) {
        SparePartStockStatisticsRespVO.UsageFrequencyAnalysis analysis = 
            new SparePartStockStatisticsRespVO.UsageFrequencyAnalysis();
        
        analysis.setSparePartId(sparePart.getId());
        analysis.setSparePartName(sparePart.getSparePartName());
        
        // 获取使用记录
        List<SparePartUsageRecordDO> usageRecords = sparePartUsageRecordMapper.selectBySparePartId(sparePart.getId());
        
        if (usageRecords == null || usageRecords.isEmpty()) {
            // 未使用
            analysis.setUsageCount(0);
            analysis.setFrequencyLevel("未使用");
            analysis.setLastUsageDate(null);
            analysis.setAverageUsageInterval(BigDecimal.ZERO);
            analysis.setUsageTrend("无使用记录");
            return analysis;
        }
        
        // 计算使用次数
        int usageCount = usageRecords.size();
        analysis.setUsageCount(usageCount);
        
        // 获取最后使用时间
        LocalDateTime lastUsageDate = usageRecords.stream()
            .map(SparePartUsageRecordDO::getUsageDate)
            .max(LocalDateTime::compareTo)
            .orElse(null);
        analysis.setLastUsageDate(lastUsageDate);
        
        // 计算使用频率等级
        String frequencyLevel = calculateFrequencyLevel(usageCount, lastUsageDate);
        analysis.setFrequencyLevel(frequencyLevel);
        
        // 计算平均使用间隔
        BigDecimal averageInterval = calculateAverageUsageInterval(usageRecords);
        analysis.setAverageUsageInterval(averageInterval);
        
        // 分析使用趋势
        String usageTrend = analyzeUsageTrend(usageRecords);
        analysis.setUsageTrend(usageTrend);
        
        return analysis;
    }

    /**
     * 计算使用频率等级
     */
    private String calculateFrequencyLevel(int usageCount, LocalDateTime lastUsageDate) {
        if (usageCount == 0) {
            return "未使用";
        }
        
        // 计算距离最后使用时间的天数
        long daysSinceLastUsage = 0;
        if (lastUsageDate != null) {
            daysSinceLastUsage = ChronoUnit.DAYS.between(lastUsageDate, LocalDateTime.now());
        }
        
        // 根据使用次数和最后使用时间判断频率等级
        if (usageCount >= 10 && daysSinceLastUsage <= 30) {
            return "高频";
        } else if (usageCount >= 5 && daysSinceLastUsage <= 60) {
            return "中频";
        } else if (usageCount >= 1 && daysSinceLastUsage <= 180) {
            return "低频";
        } else {
            return "未使用";
        }
    }

    /**
     * 计算平均使用间隔
     */
    private BigDecimal calculateAverageUsageInterval(List<SparePartUsageRecordDO> usageRecords) {
        if (usageRecords.size() < 2) {
            return BigDecimal.ZERO;
        }
        
        // 按使用时间排序
        List<SparePartUsageRecordDO> sortedRecords = usageRecords.stream()
            .sorted(Comparator.comparing(SparePartUsageRecordDO::getUsageDate))
            .collect(Collectors.toList());
        
        long totalDays = 0;
        int intervalCount = 0;
        
        for (int i = 1; i < sortedRecords.size(); i++) {
            LocalDateTime previousDate = sortedRecords.get(i - 1).getUsageDate();
            LocalDateTime currentDate = sortedRecords.get(i).getUsageDate();
            
            long days = ChronoUnit.DAYS.between(previousDate, currentDate);
            if (days > 0) {
                totalDays += days;
                intervalCount++;
            }
        }
        
        if (intervalCount == 0) {
            return BigDecimal.ZERO;
        }
        
        return BigDecimal.valueOf(totalDays).divide(BigDecimal.valueOf(intervalCount), 2, RoundingMode.HALF_UP);
    }

    /**
     * 分析使用趋势
     */
    private String analyzeUsageTrend(List<SparePartUsageRecordDO> usageRecords) {
        if (usageRecords.size() < 3) {
            return "数据不足";
        }
        
        // 按使用时间排序
        List<SparePartUsageRecordDO> sortedRecords = usageRecords.stream()
            .sorted(Comparator.comparing(SparePartUsageRecordDO::getUsageDate))
            .collect(Collectors.toList());
        
        // 计算最近3个月和之前3个月的使用次数
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        
        long recentCount = sortedRecords.stream()
            .filter(record -> record.getUsageDate().isAfter(threeMonthsAgo))
            .count();
        
        long previousCount = sortedRecords.stream()
            .filter(record -> record.getUsageDate().isAfter(sixMonthsAgo) && 
                             record.getUsageDate().isBefore(threeMonthsAgo))
            .count();
        
        if (recentCount > previousCount * 1.2) {
            return "上升";
        } else if (recentCount < previousCount * 0.8) {
            return "下降";
        } else {
            return "稳定";
        }
    }

}