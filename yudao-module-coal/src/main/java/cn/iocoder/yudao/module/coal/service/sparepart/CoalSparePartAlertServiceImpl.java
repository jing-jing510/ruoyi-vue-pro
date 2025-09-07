package cn.iocoder.yudao.module.coal.service.sparepart;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartAlertPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepart.CoalSparePartAlertDO;
import cn.iocoder.yudao.module.coal.dal.mysql.sparepart.CoalSparePartAlertMapper;
import cn.iocoder.yudao.module.erp.dal.dataobject.product.ErpProductDO;
import cn.iocoder.yudao.module.erp.dal.dataobject.stock.ErpStockDO;
import cn.iocoder.yudao.module.erp.dal.mysql.product.ErpProductMapper;
import cn.iocoder.yudao.module.erp.dal.mysql.stock.ErpStockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SPARE_PART_ALERT_NOT_EXISTS;

/**
 * 备件预警 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class CoalSparePartAlertServiceImpl implements CoalSparePartAlertService {

    @Resource
    private CoalSparePartAlertMapper sparePartAlertMapper;
    
    @Resource
    private ErpProductMapper erpProductMapper;
    
    @Resource
    private ErpStockMapper erpStockMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAlert(Long sparePartId, Long warehouseId, Integer alertType, BigDecimal currentStock, 
                           BigDecimal thresholdValue, Integer alertLevel, String alertMessage) {
        // 创建预警记录
        CoalSparePartAlertDO alert = CoalSparePartAlertDO.builder()
                .sparePartId(sparePartId)
                .warehouseId(warehouseId)
                .alertType(alertType)
                .currentStock(currentStock)
                .thresholdValue(thresholdValue)
                .alertLevel(alertLevel != null ? alertLevel : 1)
                .alertStatus(1) // 1=待处理
                .alertMessage(alertMessage)
                .build();
        
        sparePartAlertMapper.insert(alert);
        return alert.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAlert(Long alertId, Long handlerId, String handleRemark) {
        // 校验存在
        CoalSparePartAlertDO alert = sparePartAlertMapper.selectById(alertId);
        if (alert == null) {
            throw exception(SPARE_PART_ALERT_NOT_EXISTS);
        }

        // 更新处理状态
        CoalSparePartAlertDO updateObj = CoalSparePartAlertDO.builder()
                .id(alertId)
                .alertStatus(2) // 2=已处理
                .handlerId(handlerId)
                .handleTime(LocalDateTime.now())
                .handleRemark(handleRemark)
                .build();
        
        sparePartAlertMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ignoreAlert(Long alertId, Long handlerId, String handleRemark) {
        // 校验存在
        CoalSparePartAlertDO alert = sparePartAlertMapper.selectById(alertId);
        if (alert == null) {
            throw exception(SPARE_PART_ALERT_NOT_EXISTS);
        }

        // 更新忽略状态
        CoalSparePartAlertDO updateObj = CoalSparePartAlertDO.builder()
                .id(alertId)
                .alertStatus(3) // 3=已忽略
                .handlerId(handlerId)
                .handleTime(LocalDateTime.now())
                .handleRemark(handleRemark)
                .build();
        
        sparePartAlertMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAlert(Long alertId) {
        // 校验存在
        CoalSparePartAlertDO alert = sparePartAlertMapper.selectById(alertId);
        if (alert == null) {
            throw exception(SPARE_PART_ALERT_NOT_EXISTS);
        }

        // 删除
        sparePartAlertMapper.deleteById(alertId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAlertBySparePartId(Long sparePartId) {
        sparePartAlertMapper.deleteBySparePartId(sparePartId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAlertByWarehouseId(Long warehouseId) {
        sparePartAlertMapper.deleteByWarehouseId(warehouseId);
    }

    @Override
    public List<CoalSparePartAlertDO> getPendingAlerts() {
        return sparePartAlertMapper.selectPendingAlerts();
    }

    @Override
    public List<CoalSparePartAlertDO> getAlertsBySparePartId(Long sparePartId) {
        return sparePartAlertMapper.selectListBySparePartId(sparePartId);
    }

    @Override
    public List<CoalSparePartAlertDO> getAlertsByWarehouseId(Long warehouseId) {
        return sparePartAlertMapper.selectListByWarehouseId(warehouseId);
    }

    @Override
    public List<CoalSparePartAlertDO> getAlertsByAlertType(Integer alertType) {
        return sparePartAlertMapper.selectListByAlertType(alertType);
    }

    @Override
    public List<CoalSparePartAlertDO> getAlertsByAlertStatus(Integer alertStatus) {
        return sparePartAlertMapper.selectListByAlertStatus(alertStatus);
    }

    @Override
    // @Scheduled(cron = "0 0 8 * * ?") // 每天8点检查 - 暂时注释，避免在开发环境频繁执行
    public void checkStockAlerts() {
        log.info("[checkStockAlerts][开始检查库存预警]");
        
        try {
            // 1. 查询所有有备件类型的产品
            List<ErpProductDO> sparePartList = erpProductMapper.selectList(
                new LambdaQueryWrapperX<ErpProductDO>()
                    .isNotNull(ErpProductDO::getSparePartType)
            );
            
            int alertCount = 0;
            for (ErpProductDO sparePart : sparePartList) {
                // 2. 查询该备件的库存信息
                List<ErpStockDO> stockList = erpStockMapper.selectList(
                    new LambdaQueryWrapperX<ErpStockDO>()
                        .eq(ErpStockDO::getProductId, sparePart.getId())
                );
                
                for (ErpStockDO stock : stockList) {
                    BigDecimal currentStock = stock.getCount();
                    BigDecimal safetyStock = sparePart.getSafetyStock();
                    BigDecimal minStock = sparePart.getMinStock();
                    BigDecimal maxStock = sparePart.getMaxStock();
                    
                    // 3. 检查库存不足预警
                    if (safetyStock != null && currentStock.compareTo(safetyStock) < 0) {
                        // 检查是否已存在相同的未处理预警
                        List<CoalSparePartAlertDO> existingAlerts = sparePartAlertMapper.selectListBySparePartIdAndWarehouseIdAndStatus(
                            sparePart.getId(), stock.getWarehouseId(), 1); // 1=待处理
                        
                        if (existingAlerts.isEmpty()) {
                            String alertMessage = String.format("备件 %s 库存不足，当前库存：%s，安全库存：%s", 
                                sparePart.getName(), currentStock, safetyStock);
                            
                            createAlert(sparePart.getId(), stock.getWarehouseId(), 1, // 1=库存不足
                                currentStock, safetyStock, determineAlertLevel(sparePart), alertMessage);
                            alertCount++;
                        }
                    }
                    
                    // 4. 检查库存过多预警（可选）
                    if (maxStock != null && currentStock.compareTo(maxStock) > 0) {
                        List<CoalSparePartAlertDO> existingAlerts = sparePartAlertMapper.selectListBySparePartIdAndWarehouseIdAndStatus(
                            sparePart.getId(), stock.getWarehouseId(), 1);
                        
                        if (existingAlerts.isEmpty()) {
                            String alertMessage = String.format("备件 %s 库存过多，当前库存：%s，最大库存：%s", 
                                sparePart.getName(), currentStock, maxStock);
                            
                            createAlert(sparePart.getId(), stock.getWarehouseId(), 4, // 4=库存过多
                                currentStock, maxStock, 1, alertMessage); // 1=低级预警
                            alertCount++;
                        }
                    }
                }
            }
            
            log.info("[checkStockAlerts][库存预警检查完成，生成预警记录：{}条]", alertCount);
        } catch (Exception e) {
            log.error("[checkStockAlerts][库存预警检查失败]", e);
        }
    }

    @Override
    // @Scheduled(cron = "0 0 9 * * ?") // 每天9点检查 - 暂时注释，避免在开发环境频繁执行
    public void checkReplacementAlerts() {
        log.info("[checkReplacementAlerts][开始检查更换提醒]");
        
        try {
            // 1. 查询所有有更换周期的备件
            List<ErpProductDO> sparePartList = erpProductMapper.selectList(
                new LambdaQueryWrapperX<ErpProductDO>()
                    .isNotNull(ErpProductDO::getReplacementCycle)
            );
            
            int alertCount = 0;
            LocalDate today = LocalDate.now();
            
            for (ErpProductDO sparePart : sparePartList) {
                LocalDate nextReplacementDate = sparePart.getNextReplacementDate();
                
                if (nextReplacementDate != null) {
                    // 2. 检查是否需要提前30天提醒
                    long daysUntilReplacement = java.time.temporal.ChronoUnit.DAYS.between(today, nextReplacementDate);
                    
                    if (daysUntilReplacement <= 30 && daysUntilReplacement >= 0) {
                        // 检查是否已存在相同的未处理预警
                        List<CoalSparePartAlertDO> existingAlerts = sparePartAlertMapper.selectListBySparePartIdAndAlertTypeAndStatus(
                            sparePart.getId(), 2, 1); // 2=到期更换, 1=待处理
                        
                        if (existingAlerts.isEmpty()) {
                            String alertMessage = String.format("备件 %s 即将到期更换，距离更换还有 %d 天，计划更换日期：%s", 
                                sparePart.getName(), daysUntilReplacement, nextReplacementDate);
                            
                            // 获取主仓库ID（默认为1，实际应该从配置获取）
                            Long defaultWarehouseId = 1L;
                            
                            createAlert(sparePart.getId(), defaultWarehouseId, 2, // 2=到期更换
                                BigDecimal.valueOf(daysUntilReplacement), BigDecimal.valueOf(30), 
                                determineAlertLevel(sparePart), alertMessage);
                            alertCount++;
                        }
                    }
                    
                    // 3. 检查是否已超期未更换
                    if (daysUntilReplacement < 0) {
                        List<CoalSparePartAlertDO> existingAlerts = sparePartAlertMapper.selectListBySparePartIdAndAlertTypeAndStatus(
                            sparePart.getId(), 3, 1); // 3=超期未更换, 1=待处理
                        
                        if (existingAlerts.isEmpty()) {
                            String alertMessage = String.format("备件 %s 已超期未更换，超期 %d 天，计划更换日期：%s", 
                                sparePart.getName(), Math.abs(daysUntilReplacement), nextReplacementDate);
                            
                            Long defaultWarehouseId = 1L;
                            
                            createAlert(sparePart.getId(), defaultWarehouseId, 3, // 3=超期未更换
                                BigDecimal.valueOf(Math.abs(daysUntilReplacement)), BigDecimal.ZERO, 
                                3, alertMessage); // 3=高级预警
                            alertCount++;
                        }
                    }
                }
            }
            
            log.info("[checkReplacementAlerts][更换提醒检查完成，生成预警记录：{}条]", alertCount);
        } catch (Exception e) {
            log.error("[checkReplacementAlerts][更换提醒检查失败]", e);
        }
    }

    @Override
    public CoalSparePartAlertDO getAlert(Long id) {
        return sparePartAlertMapper.selectById(id);
    }

    @Override
    public PageResult<CoalSparePartAlertDO> getAlertPage(CoalSparePartAlertPageReqVO pageReqVO) {
        return sparePartAlertMapper.selectPage(pageReqVO);
    }

    /**
     * 根据备件重要程度确定预警级别
     * 
     * @param sparePart 备件信息
     * @return 预警级别 1-低 2-中 3-高
     */
    private Integer determineAlertLevel(ErpProductDO sparePart) {
        // 根据备件类型和重要程度确定预警级别
        Integer sparePartType = sparePart.getSparePartType();
        
        if (sparePartType == null) {
            return 1; // 默认低级预警
        }
        
        // 1=易损件 2=标准件 3=电气件 等
        switch (sparePartType) {
            case 1: // 易损件 - 中等预警
                return 2;
            case 3: // 电气件 - 高级预警  
                return 3;
            default: // 其他 - 低级预警
                return 1;
        }
    }

}
