package cn.iocoder.yudao.module.coal.service.sparepart;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartAlertPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepart.CoalSparePartAlertDO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 备件预警 Service 接口
 *
 * @author 芋道源码
 */
public interface CoalSparePartAlertService {

    /**
     * 创建预警记录
     *
     * @param sparePartId 备件ID
     * @param warehouseId 仓库ID
     * @param alertType 预警类型
     * @param currentStock 当前库存
     * @param thresholdValue 阈值
     * @param alertLevel 预警级别
     * @param alertMessage 预警信息
     * @return 预警ID
     */
    Long createAlert(Long sparePartId, Long warehouseId, Integer alertType, BigDecimal currentStock, 
                    BigDecimal thresholdValue, Integer alertLevel, String alertMessage);

    /**
     * 处理预警
     *
     * @param alertId 预警ID
     * @param handlerId 处理人ID
     * @param handleRemark 处理备注
     */
    void handleAlert(Long alertId, Long handlerId, String handleRemark);

    /**
     * 忽略预警
     *
     * @param alertId 预警ID
     * @param handlerId 处理人ID
     * @param handleRemark 处理备注
     */
    void ignoreAlert(Long alertId, Long handlerId, String handleRemark);

    /**
     * 删除预警记录
     *
     * @param alertId 预警ID
     */
    void deleteAlert(Long alertId);

    /**
     * 根据备件ID删除预警记录
     *
     * @param sparePartId 备件ID
     */
    void deleteAlertBySparePartId(Long sparePartId);

    /**
     * 根据仓库ID删除预警记录
     *
     * @param warehouseId 仓库ID
     */
    void deleteAlertByWarehouseId(Long warehouseId);

    /**
     * 查询待处理的预警记录列表
     *
     * @return 待处理预警记录列表
     */
    List<CoalSparePartAlertDO> getPendingAlerts();

    /**
     * 根据备件ID查询预警记录列表
     *
     * @param sparePartId 备件ID
     * @return 预警记录列表
     */
    List<CoalSparePartAlertDO> getAlertsBySparePartId(Long sparePartId);

    /**
     * 根据仓库ID查询预警记录列表
     *
     * @param warehouseId 仓库ID
     * @return 预警记录列表
     */
    List<CoalSparePartAlertDO> getAlertsByWarehouseId(Long warehouseId);

    /**
     * 根据预警类型查询预警记录列表
     *
     * @param alertType 预警类型
     * @return 预警记录列表
     */
    List<CoalSparePartAlertDO> getAlertsByAlertType(Integer alertType);

    /**
     * 根据预警状态查询预警记录列表
     *
     * @param alertStatus 预警状态
     * @return 预警记录列表
     */
    List<CoalSparePartAlertDO> getAlertsByAlertStatus(Integer alertStatus);

    /**
     * 检查库存预警
     * 定时任务调用，检查所有备件的库存情况
     */
    void checkStockAlerts();

    /**
     * 检查更换提醒
     * 定时任务调用，检查需要更换的备件
     */
    void checkReplacementAlerts();

    /**
     * 根据ID获取预警记录
     *
     * @param id 预警ID
     * @return 预警记录
     */
    CoalSparePartAlertDO getAlert(Long id);

    /**
     * 获得预警记录分页
     *
     * @param pageReqVO 分页查询
     * @return 预警记录分页
     */
    PageResult<CoalSparePartAlertDO> getAlertPage(CoalSparePartAlertPageReqVO pageReqVO);

}
