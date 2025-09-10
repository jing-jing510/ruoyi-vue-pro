package cn.iocoder.yudao.module.coal.service.sparepartalert;

import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartalert.SparePartAlertDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinfo.SparePartInfoDO;

import java.util.List;

/**
 * 备件预警消息推送服务
 *
 * @author 芋道源码
 */
public interface SparePartAlertNotificationService {

    /**
     * 发送库存预警消息
     *
     * @param alert 预警信息
     * @param sparePartInfo 备件信息
     */
    void sendStockAlertNotification(SparePartAlertDO alert, SparePartInfoDO sparePartInfo);

    /**
     * 发送批量预警消息
     *
     * @param alerts 预警列表
     */
    void sendBatchAlertNotifications(List<SparePartAlertDO> alerts);

    /**
     * 发送预警解除消息
     *
     * @param alert 预警信息
     * @param sparePartInfo 备件信息
     */
    void sendAlertResolvedNotification(SparePartAlertDO alert, SparePartInfoDO sparePartInfo);

    /**
     * 发送定期预警汇总消息
     */
    void sendDailyAlertSummary();

}
