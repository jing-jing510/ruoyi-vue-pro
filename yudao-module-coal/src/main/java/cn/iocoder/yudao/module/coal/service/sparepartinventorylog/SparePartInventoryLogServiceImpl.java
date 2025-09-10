package cn.iocoder.yudao.module.coal.service.sparepartinventorylog;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog.vo.SparePartInventoryLogPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog.vo.SparePartInventoryLogSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinventorylog.SparePartInventoryLogDO;
import cn.iocoder.yudao.module.coal.dal.mysql.sparepartinventorylog.SparePartInventoryLogMapper;
import cn.iocoder.yudao.module.coal.service.sparepartstock.SparePartStockService;
import cn.iocoder.yudao.module.coal.service.sparepartalert.SparePartAlertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SPARE_PART_INVENTORY_LOG_NOT_EXISTS;

/**
 * 备件出入库记录 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class SparePartInventoryLogServiceImpl implements SparePartInventoryLogService {

    @Resource
    private SparePartInventoryLogMapper sparePartInventoryLogMapper;
    
    @Resource
    private SparePartStockService sparePartStockService;
    
    @Resource
    private SparePartAlertService sparePartAlertService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSparePartInventoryLog(SparePartInventoryLogSaveReqVO createReqVO) {
        // 1. 保存出入库记录
        SparePartInventoryLogDO sparePartInventoryLog = BeanUtils.toBean(createReqVO, SparePartInventoryLogDO.class);
        sparePartInventoryLogMapper.insert(sparePartInventoryLog);

        // 2. 自动更新库存
        updateStockQuantity(sparePartInventoryLog);
        
        // 3. 检查预警
        checkAndCreateAlert(sparePartInventoryLog.getSparePartId());

        // 返回
        return sparePartInventoryLog.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSparePartInventoryLog(SparePartInventoryLogSaveReqVO updateReqVO) {
        // 校验存在
        SparePartInventoryLogDO oldLog = validateSparePartInventoryLogExists(updateReqVO.getId());
        
        // 更新
        SparePartInventoryLogDO updateObj = BeanUtils.toBean(updateReqVO, SparePartInventoryLogDO.class);
        sparePartInventoryLogMapper.updateById(updateObj);
        
        // 调整库存：先回滚旧记录的影响，再应用新记录的影响
        adjustStockForUpdate(oldLog, updateObj);
        
        // 检查预警
        checkAndCreateAlert(updateObj.getSparePartId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSparePartInventoryLog(Long id) {
        // 校验存在并获取记录
        SparePartInventoryLogDO log = validateSparePartInventoryLogExists(id);
        
        // 回滚库存影响
        rollbackStockQuantity(log);
        
        // 删除记录
        sparePartInventoryLogMapper.deleteById(id);
        
        // 检查预警
        checkAndCreateAlert(log.getSparePartId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSparePartInventoryLogListByIds(List<Long> ids) {
        // 获取所有要删除的记录
        List<SparePartInventoryLogDO> logs = sparePartInventoryLogMapper.selectByIds(ids);
        
        // 回滚每个记录的库存影响
        for (SparePartInventoryLogDO log : logs) {
            rollbackStockQuantity(log);
        }
        
        // 删除记录
        sparePartInventoryLogMapper.deleteByIds(ids);
        
        // 检查预警（对涉及的备件）
        logs.stream()
            .map(SparePartInventoryLogDO::getSparePartId)
            .distinct()
            .forEach(this::checkAndCreateAlert);
    }


    private SparePartInventoryLogDO validateSparePartInventoryLogExists(Long id) {
        SparePartInventoryLogDO log = sparePartInventoryLogMapper.selectById(id);
        if (log == null) {
            throw exception(SPARE_PART_INVENTORY_LOG_NOT_EXISTS);
        }
        return log;
    }

    @Override
    public SparePartInventoryLogDO getSparePartInventoryLog(Long id) {
        return sparePartInventoryLogMapper.selectById(id);
    }

    @Override
    public PageResult<SparePartInventoryLogDO> getSparePartInventoryLogPage(SparePartInventoryLogPageReqVO pageReqVO) {
        return sparePartInventoryLogMapper.selectPage(pageReqVO);
    }

    /**
     * 根据出入库记录更新库存数量
     */
    private void updateStockQuantity(SparePartInventoryLogDO log) {
        // 根据数量的正负来判断是入库还是出库
        if (log.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
            // 正数：入库
            sparePartStockService.increaseStock(log.getSparePartId(), log.getQuantity());
        } else if (log.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            // 负数：出库，需要转换为正数
            sparePartStockService.decreaseStock(log.getSparePartId(), log.getQuantity().abs());
        }
    }
    
    /**
     * 检查并创建预警
     */
    private void checkAndCreateAlert(Long sparePartId) {
        try {
            sparePartAlertService.checkAndCreateAlert(sparePartId);
        } catch (Exception e) {
            // 预警检查失败不影响主流程，记录日志即可
            // log.error("预警检查失败，备件ID: {}", sparePartId, e);
        }
    }
    
    /**
     * 调整库存（用于更新操作）
     */
    private void adjustStockForUpdate(SparePartInventoryLogDO oldLog, SparePartInventoryLogDO newLog) {
        // 先回滚旧记录的影响
        rollbackStockQuantity(oldLog);
        
        // 再应用新记录的影响
        updateStockQuantity(newLog);
    }
    
    /**
     * 回滚库存数量（用于删除操作）
     */
    private void rollbackStockQuantity(SparePartInventoryLogDO log) {
        // 根据数量的正负来判断回滚操作
        if (log.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
            // 原入库（正数），现在要回滚（减少库存）
            sparePartStockService.decreaseStock(log.getSparePartId(), log.getQuantity());
        } else if (log.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            // 原出库（负数），现在要回滚（增加库存）
            sparePartStockService.increaseStock(log.getSparePartId(), log.getQuantity().abs());
        }
    }

}