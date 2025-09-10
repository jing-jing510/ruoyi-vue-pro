package cn.iocoder.yudao.module.coal.service.sparepartalert;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartalert.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartalert.SparePartAlertDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 备件预警记录 Service 接口
 *
 * @author 京京
 */
public interface SparePartAlertService {

    /**
     * 创建备件预警记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSparePartAlert(@Valid SparePartAlertSaveReqVO createReqVO);

    /**
     * 更新备件预警记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSparePartAlert(@Valid SparePartAlertSaveReqVO updateReqVO);

    /**
     * 删除备件预警记录
     *
     * @param id 编号
     */
    void deleteSparePartAlert(Long id);

    /**
    * 批量删除备件预警记录
    *
    * @param ids 编号
    */
    void deleteSparePartAlertListByIds(List<Long> ids);

    /**
     * 获得备件预警记录
     *
     * @param id 编号
     * @return 备件预警记录
     */
    SparePartAlertDO getSparePartAlert(Long id);

    /**
     * 获得备件预警记录分页
     *
     * @param pageReqVO 分页查询
     * @return 备件预警记录分页
     */
    PageResult<SparePartAlertDO> getSparePartAlertPage(SparePartAlertPageReqVO pageReqVO);

    /**
     * 检查并创建预警
     *
     * @param sparePartId 备件ID
     */
    void checkAndCreateAlert(Long sparePartId);

    /**
     * 发送预警通知
     *
     * @param id 预警记录ID
     */
    void sendNotification(Long id);

}