package cn.iocoder.yudao.module.coal.service.energyconsumption;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.energyconsumption.EnergyConsumptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 能源消耗记录 Service 接口
 *
 * @author 京京
 */
public interface EnergyConsumptionService {

    /**
     * 创建能源消耗记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEnergyConsumption(@Valid EnergyConsumptionSaveReqVO createReqVO);

    /**
     * 更新能源消耗记录
     *
     * @param updateReqVO 更新信息
     */
    void updateEnergyConsumption(@Valid EnergyConsumptionSaveReqVO updateReqVO);

    /**
     * 删除能源消耗记录
     *
     * @param id 编号
     */
    void deleteEnergyConsumption(Long id);

    /**
    * 批量删除能源消耗记录
    *
    * @param ids 编号
    */
    void deleteEnergyConsumptionListByIds(List<Long> ids);

    /**
     * 获得能源消耗记录
     *
     * @param id 编号
     * @return 能源消耗记录
     */
    EnergyConsumptionDO getEnergyConsumption(Long id);

    /**
     * 获得能源消耗记录分页
     *
     * @param pageReqVO 分页查询
     * @return 能源消耗记录分页
     */
    PageResult<EnergyConsumptionDO> getEnergyConsumptionPage(EnergyConsumptionPageReqVO pageReqVO);

    /**
     * 获得能源消耗记录统计信息
     *
     * @return 能源消耗记录统计信息
     */
    EnergyConsumptionStatisticsRespVO getEnergyConsumptionStatistics();

}