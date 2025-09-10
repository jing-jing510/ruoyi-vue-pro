package cn.iocoder.yudao.module.coal.service.qualityalert;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo.QualityAlertPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo.QualityAlertSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityalert.QualityAlertDO;
import cn.iocoder.yudao.module.coal.dal.mysql.qualityalert.QualityAlertMapper;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.QUALITY_ALERT_NOT_EXISTS;

/**
 * 质量预警记录 Service 实现类
 *
 * @author 京京
 */
@Slf4j
@Service
@Validated
public class QualityAlertServiceImpl implements QualityAlertService {

    @Resource
    private QualityAlertMapper qualityAlertMapper;
    
    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;
    
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createQualityAlert(QualityAlertSaveReqVO createReqVO) {
        // 插入
        QualityAlertDO qualityAlert = BeanUtils.toBean(createReqVO, QualityAlertDO.class);
        qualityAlertMapper.insert(qualityAlert);

        // 返回
        return qualityAlert.getId();
    }

    @Override
    public void updateQualityAlert(QualityAlertSaveReqVO updateReqVO) {
        // 校验存在
        validateQualityAlertExists(updateReqVO.getId());
        // 更新
        QualityAlertDO updateObj = BeanUtils.toBean(updateReqVO, QualityAlertDO.class);
        qualityAlertMapper.updateById(updateObj);
    }

    @Override
    public void deleteQualityAlert(Long id) {
        // 校验存在
        validateQualityAlertExists(id);
        // 删除
        qualityAlertMapper.deleteById(id);
    }

    @Override
        public void deleteQualityAlertListByIds(List<Long> ids) {
        // 删除
        qualityAlertMapper.deleteByIds(ids);
        }


    private void validateQualityAlertExists(Long id) {
        if (qualityAlertMapper.selectById(id) == null) {
            throw exception(QUALITY_ALERT_NOT_EXISTS);
        }
    }

    @Override
    public QualityAlertDO getQualityAlert(Long id) {
        return qualityAlertMapper.selectById(id);
    }

    @Override
    public PageResult<QualityAlertDO> getQualityAlertPage(QualityAlertPageReqVO pageReqVO) {
        return qualityAlertMapper.selectPage(pageReqVO);
    }

    @Override
    public void sendQualityAlertNotification(Long alertId, List<Long> recipientIds) {
        try {
            // 1. 获取预警记录信息
            QualityAlertDO alert = qualityAlertMapper.selectById(alertId);
            if (alert == null) {
                log.error("质量预警记录不存在，ID: {}", alertId);
                return;
            }

            // 2. 检查是否有接收人
            if (alert.getRecipients() == null || alert.getRecipients().trim().isEmpty()) {
                log.warn("质量预警记录没有设置接收人，ID: {}", alertId);
                return;
            }

            // 3. 解析接收人列表
            String[] recipientIdStrs = alert.getRecipients().split(",");
            List<Long> actualRecipientIds = new ArrayList<>();
            for (String recipientIdStr : recipientIdStrs) {
                try {
                    Long recipientId = Long.parseLong(recipientIdStr.trim());
                    actualRecipientIds.add(recipientId);
                } catch (NumberFormatException e) {
                    log.warn("无效的接收人ID: {}", recipientIdStr);
                }
            }

            if (actualRecipientIds.isEmpty()) {
                log.warn("质量预警记录没有有效的接收人，ID: {}", alertId);
                return;
            }

            // 4. 准备站内信模板参数
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("alertCode", alert.getAlertCode());
            templateParams.put("itemName", "检测项目"); // 这里需要根据qualityItemId获取项目名称
            templateParams.put("productType", getProductTypeName(alert.getProductType()));
            templateParams.put("measuredValue", alert.getMeasuredValue());
            templateParams.put("unit", "%"); // 这里需要根据检测项目获取单位
            templateParams.put("standardValue", alert.getStandardValue());
            templateParams.put("deviation", alert.getDeviation());
            templateParams.put("alertMessage", alert.getAlertMessage());
            templateParams.put("alertLevel", getAlertLevelName(alert.getAlertLevel()));
            templateParams.put("sendTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // 5. 发送站内信给每个接收人
            for (Long recipientId : actualRecipientIds) {
                try {
                    NotifySendSingleToUserReqDTO reqDTO = new NotifySendSingleToUserReqDTO()
                            .setUserId(recipientId)
                            .setTemplateCode("coal_quality_alert")
                            .setTemplateParams(templateParams);
                    
                    notifyMessageSendApi.sendSingleMessageToAdmin(reqDTO);
                    log.info("质量预警站内信发送成功，预警ID: {}, 接收人ID: {}", alertId, recipientId);
                } catch (Exception e) {
                    log.error("发送质量预警站内信失败，预警ID: {}, 接收人ID: {}, 错误: {}", alertId, recipientId, e.getMessage());
                }
            }

            // 6. 更新预警记录的发送状态
            QualityAlertDO updateAlert = new QualityAlertDO();
            updateAlert.setId(alertId);
            updateAlert.setNotificationSent(1);
            updateAlert.setNotificationTime(LocalDateTime.now());
            qualityAlertMapper.updateById(updateAlert);

        } catch (Exception e) {
            log.error("发送质量预警站内信异常，预警ID: {}, 错误: {}", alertId, e.getMessage(), e);
        }
    }

    @Override
    public Object getQualityAlertStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            
            // 获取总预警数量
            Long totalCount = qualityAlertMapper.selectCount();
            statistics.put("totalCount", totalCount);
            
            // 获取待处理预警数量
            Long pendingCount = qualityAlertMapper.selectCount("alert_status", 1);
            statistics.put("pendingCount", pendingCount);
            
            // 获取今日预警数量
            String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Long todayCount = qualityAlertMapper.selectCount("DATE(alert_time)", today);
            statistics.put("todayCount", todayCount);
            
            // 获取紧急预警数量
            Long urgentCount = qualityAlertMapper.selectCount("alert_level", 3);
            statistics.put("urgentCount", urgentCount);
            
            return statistics;
        } catch (Exception e) {
            log.error("获取质量预警统计数据失败: {}", e.getMessage(), e);
            return new HashMap<>();
        }
    }

    /**
     * 获取产品类型名称
     */
    private String getProductTypeName(Integer productType) {
        if (productType == null) return "未知";
        switch (productType) {
            case 1: return "原煤";
            case 2: return "精煤";
            case 3: return "中煤";
            case 4: return "煤泥";
            case 5: return "矸石";
            default: return "未知";
        }
    }

    /**
     * 获取预警级别名称
     */
    private String getAlertLevelName(Integer alertLevel) {
        if (alertLevel == null) return "未知";
        switch (alertLevel) {
            case 1: return "一般";
            case 2: return "重要";
            case 3: return "紧急";
            case 4: return "非常紧急";
            default: return "未知";
        }
    }

}