package cn.iocoder.yudao.module.coal.service.sparepartalert;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartalert.vo.SparePartAlertPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartalert.vo.SparePartAlertSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartalert.SparePartAlertDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinfo.SparePartInfoDO;
import cn.iocoder.yudao.module.coal.dal.mysql.sparepartalert.SparePartAlertMapper;
import cn.iocoder.yudao.module.coal.service.sparepartinfo.SparePartInfoService;
import cn.iocoder.yudao.module.coal.service.sparepartstock.SparePartStockService;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 备件预警记录 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class SparePartAlertServiceImpl implements SparePartAlertService {

    private static final Logger log = LoggerFactory.getLogger(SparePartAlertServiceImpl.class);

    @Resource
    private SparePartAlertMapper sparePartAlertMapper;
    
    @Resource
    private SparePartInfoService sparePartInfoService;
    
    @Resource
    private SparePartStockService sparePartStockService;
    
    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Override
    public Long createSparePartAlert(SparePartAlertSaveReqVO createReqVO) {
        // 插入
        SparePartAlertDO sparePartAlert = BeanUtils.toBean(createReqVO, SparePartAlertDO.class);
        sparePartAlertMapper.insert(sparePartAlert);

        // 返回
        return sparePartAlert.getId();
    }

    @Override
    public void updateSparePartAlert(SparePartAlertSaveReqVO updateReqVO) {
        // 校验存在
        validateSparePartAlertExists(updateReqVO.getId());
        // 更新
        SparePartAlertDO updateObj = BeanUtils.toBean(updateReqVO, SparePartAlertDO.class);
        sparePartAlertMapper.updateById(updateObj);
    }

    @Override
    public void deleteSparePartAlert(Long id) {
        // 校验存在
        validateSparePartAlertExists(id);
        // 删除
        sparePartAlertMapper.deleteById(id);
    }

    @Override
        public void deleteSparePartAlertListByIds(List<Long> ids) {
        // 删除
        sparePartAlertMapper.deleteByIds(ids);
        }


    private void validateSparePartAlertExists(Long id) {
        if (sparePartAlertMapper.selectById(id) == null) {
            throw exception(SPARE_PART_ALERT_NOT_EXISTS);
        }
    }

    @Override
    public SparePartAlertDO getSparePartAlert(Long id) {
        return sparePartAlertMapper.selectById(id);
    }

    @Override
    public PageResult<SparePartAlertDO> getSparePartAlertPage(SparePartAlertPageReqVO pageReqVO) {
        return sparePartAlertMapper.selectPage(pageReqVO);
    }

    @Override
    public void checkAndCreateAlert(Long sparePartId) {
        // 1. 获取备件基础信息
        SparePartInfoDO sparePart = sparePartInfoService.getSparePartInfo(sparePartId);
        if (sparePart == null) {
            return;
        }
        
        // 2. 获取当前库存
        BigDecimal currentStock = sparePartStockService.getCurrentStock(sparePartId);
        
        // 3. 检查库存不足预警
        if (currentStock.compareTo(sparePart.getMinStock()) <= 0) {
            createLowStockAlert(sparePart, currentStock);
        }
        
        // 4. 检查安全库存预警
        if (currentStock.compareTo(sparePart.getSafetyStock()) <= 0) {
            createSafetyStockAlert(sparePart, currentStock);
        }
    }
    
    /**
     * 创建库存不足预警
     */
    private void createLowStockAlert(SparePartInfoDO sparePart, BigDecimal currentStock) {
        // 检查是否已存在未处理的库存不足预警
        if (hasUnprocessedAlert(sparePart.getId(), 1)) {
            return;
        }
        
        SparePartAlertSaveReqVO alert = new SparePartAlertSaveReqVO();
        alert.setSparePartId(sparePart.getId());
        alert.setAlertType(1); // 库存不足
        alert.setAlertLevel(calculateAlertLevel(currentStock, sparePart.getMinStock()));
        alert.setAlertStatus(1); // 未处理
        alert.setAlertMessage(String.format("备件 %s 库存不足，当前库存：%s，最低库存：%s", 
            sparePart.getSparePartName(), currentStock, sparePart.getMinStock()));
        alert.setIsSent(false); // 未发送
        
        createSparePartAlert(alert);
    }
    
    /**
     * 创建安全库存预警
     */
    private void createSafetyStockAlert(SparePartInfoDO sparePart, BigDecimal currentStock) {
        // 检查是否已存在未处理的安全库存预警
        if (hasUnprocessedAlert(sparePart.getId(), 2)) {
            return;
        }
        
        SparePartAlertSaveReqVO alert = new SparePartAlertSaveReqVO();
        alert.setSparePartId(sparePart.getId());
        alert.setAlertType(2); // 安全库存预警
        alert.setAlertLevel(2); // 中等预警
        alert.setAlertStatus(1); // 未处理
        alert.setAlertMessage(String.format("备件 %s 库存接近安全库存，当前库存：%s，安全库存：%s", 
            sparePart.getSparePartName(), currentStock, sparePart.getSafetyStock()));
        alert.setIsSent(false); // 未发送
        
        createSparePartAlert(alert);
    }
    
    /**
     * 计算预警级别
     */
    private Integer calculateAlertLevel(BigDecimal currentStock, BigDecimal minStock) {
        if (currentStock.compareTo(BigDecimal.ZERO) <= 0) {
            return 4; // 严重
        } else if (currentStock.compareTo(minStock.divide(new BigDecimal("2"))) <= 0) {
            return 3; // 高
        } else if (currentStock.compareTo(minStock) <= 0) {
            return 2; // 中
        } else {
            return 1; // 低
        }
    }
    
    /**
     * 检查是否存在未处理的预警
     */
    private boolean hasUnprocessedAlert(Long sparePartId, Integer alertType) {
        // 这里可以添加查询逻辑，检查是否已存在未处理的同类型预警
        // 暂时返回false，表示可以创建新预警
        return false;
    }

    @Override
    public void sendNotification(Long id) {
        // 1. 获取预警记录
        SparePartAlertDO alert = sparePartAlertMapper.selectById(id);
        if (alert == null) {
            throw new ServiceException(SPARE_PART_ALERT_NOT_EXISTS);
        }
        
        // 2. 检查是否已发送
        if (alert.getIsSent()) {
            throw new ServiceException(SPARE_PART_ALERT_ALREADY_SENT);
        }
        
        // 3. 检查是否有接收人
        if (alert.getRecipients() == null || alert.getRecipients().trim().isEmpty()) {
            throw new ServiceException(SPARE_PART_ALERT_NO_RECIPIENTS);
        }
        
        // 4. 发送站内信通知
        try {
            sendInternalMessage(alert);
            
            // 5. 更新发送状态
            alert.setIsSent(true);
            alert.setSendTime(LocalDateTime.now());
            sparePartAlertMapper.updateById(alert);
            
        } catch (Exception e) {
            log.error("发送预警通知失败，预警ID: {}", id, e);
            throw new ServiceException(SPARE_PART_ALERT_SEND_FAILED);
        }
    }
    
    /**
     * 发送站内信通知
     */
    private void sendInternalMessage(SparePartAlertDO alert) {
        // 解析接收人ID列表
        String[] recipientIds = alert.getRecipients().split(",");
        
        // 获取备件信息
        SparePartInfoDO sparePart = sparePartInfoService.getSparePartInfo(alert.getSparePartId());
        String sparePartName = sparePart != null ? sparePart.getSparePartName() : "未知备件";
        
        // 构建通知内容
        String title = String.format("备件预警通知 - %s", alert.getAlertTitle());
        String content = String.format(
            "备件名称：%s\n" +
            "预警类型：%s\n" +
            "预警级别：%s\n" +
            "预警信息：%s\n" +
            "当前库存：%s\n" +
            "阈值：%s\n" +
            "请及时处理！",
            sparePartName,
            getAlertTypeName(alert.getAlertType()),
            getAlertLevelName(alert.getAlertLevel()),
            alert.getAlertMessage(),
            alert.getCurrentStock(),
            alert.getThresholdValue()
        );
        
        // 发送给每个接收人
        for (String recipientIdStr : recipientIds) {
            try {
                Long recipientId = Long.parseLong(recipientIdStr.trim());
                
                // 构建模板参数
                Map<String, Object> templateParams = new HashMap<>();
                templateParams.put("alertTitle", alert.getAlertTitle());
                templateParams.put("sparePartName", sparePartName);
                templateParams.put("alertType", getAlertTypeName(alert.getAlertType()));
                templateParams.put("alertLevel", getAlertLevelName(alert.getAlertLevel()));
                templateParams.put("alertMessage", alert.getAlertMessage());
                templateParams.put("currentStock", alert.getCurrentStock());
                templateParams.put("thresholdValue", alert.getThresholdValue());
                templateParams.put("sendTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                
                // 发送站内信
                notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO()
                        .setUserId(recipientId)
                        .setTemplateCode("spare_part_alert")
                        .setTemplateParams(templateParams));
                
                log.info("成功发送预警通知给用户ID: {}, 标题: {}", recipientId, title);
                
            } catch (NumberFormatException e) {
                log.warn("无效的接收人ID: {}", recipientIdStr);
            } catch (Exception e) {
                log.error("发送站内信失败，用户ID: {}, 错误: {}", recipientIdStr, e.getMessage(), e);
                throw e; // 重新抛出异常，让上层处理
            }
        }
    }
    
    /**
     * 获取预警类型名称
     */
    private String getAlertTypeName(Integer alertType) {
        switch (alertType) {
            case 1: return "库存不足";
            case 2: return "安全库存";
            case 3: return "过期预警";
            default: return "未知类型";
        }
    }
    
    /**
     * 获取预警级别名称
     */
    private String getAlertLevelName(Integer alertLevel) {
        switch (alertLevel) {
            case 1: return "低";
            case 2: return "中";
            case 3: return "高";
            case 4: return "紧急";
            default: return "未知级别";
        }
    }

}