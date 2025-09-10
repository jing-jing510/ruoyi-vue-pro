package cn.iocoder.yudao.module.coal.service.qualityalert;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo.QualityAlertPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo.QualityAlertSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityalert.QualityAlertDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 质量预警记录 Service 接口
 *
 * @author 京京
 */
public interface QualityAlertService {

    /**
     * 创建质量预警记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQualityAlert(@Valid QualityAlertSaveReqVO createReqVO);

    /**
     * 更新质量预警记录
     *
     * @param updateReqVO 更新信息
     */
    void updateQualityAlert(@Valid QualityAlertSaveReqVO updateReqVO);

    /**
     * 删除质量预警记录
     *
     * @param id 编号
     */
    void deleteQualityAlert(Long id);

    /**
    * 批量删除质量预警记录
    *
    * @param ids 编号
    */
    void deleteQualityAlertListByIds(List<Long> ids);

    /**
     * 获得质量预警记录
     *
     * @param id 编号
     * @return 质量预警记录
     */
    QualityAlertDO getQualityAlert(Long id);

    /**
     * 获得质量预警记录分页
     *
     * @param pageReqVO 分页查询
     * @return 质量预警记录分页
     */
    PageResult<QualityAlertDO> getQualityAlertPage(QualityAlertPageReqVO pageReqVO);

    /**
     * 发送质量预警站内信通知
     *
     * @param alertId 预警记录ID
     * @param recipientIds 接收人ID列表
     */
    void sendQualityAlertNotification(Long alertId, List<Long> recipientIds);

    /**
     * 获取质量预警统计数据
     *
     * @return 统计数据
     */
    Object getQualityAlertStatistics();

}