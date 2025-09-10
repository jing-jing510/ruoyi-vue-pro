package cn.iocoder.yudao.module.coal.service.energyalert;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.energyalert.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.energyalert.EnergyAlertDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 能源预警记录 Service 接口
 *
 * @author 芋道源码
 */
public interface EnergyAlertService {

    /**
     * 创建能源预警记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEnergyAlert(@Valid EnergyAlertSaveReqVO createReqVO);

    /**
     * 更新能源预警记录
     *
     * @param updateReqVO 更新信息
     */
    void updateEnergyAlert(@Valid EnergyAlertSaveReqVO updateReqVO);

    /**
     * 删除能源预警记录
     *
     * @param id 编号
     */
    void deleteEnergyAlert(Long id);

    /**
    * 批量删除能源预警记录
    *
    * @param ids 编号
    */
    void deleteEnergyAlertListByIds(List<Long> ids);

    /**
     * 获得能源预警记录
     *
     * @param id 编号
     * @return 能源预警记录
     */
    EnergyAlertDO getEnergyAlert(Long id);

    /**
     * 获得能源预警记录分页
     *
     * @param pageReqVO 分页查询
     * @return 能源预警记录分页
     */
    PageResult<EnergyAlertDO> getEnergyAlertPage(EnergyAlertPageReqVO pageReqVO);

}