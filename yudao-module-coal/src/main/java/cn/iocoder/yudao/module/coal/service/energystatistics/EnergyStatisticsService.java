package cn.iocoder.yudao.module.coal.service.energystatistics;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.energystatistics.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.energystatistics.EnergyStatisticsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 能源消耗统计 Service 接口
 *
 * @author 京京
 */
public interface EnergyStatisticsService {

    /**
     * 创建能源消耗统计
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEnergyStatistics(@Valid EnergyStatisticsSaveReqVO createReqVO);

    /**
     * 更新能源消耗统计
     *
     * @param updateReqVO 更新信息
     */
    void updateEnergyStatistics(@Valid EnergyStatisticsSaveReqVO updateReqVO);

    /**
     * 删除能源消耗统计
     *
     * @param id 编号
     */
    void deleteEnergyStatistics(Long id);

    /**
    * 批量删除能源消耗统计
    *
    * @param ids 编号
    */
    void deleteEnergyStatisticsListByIds(List<Long> ids);

    /**
     * 获得能源消耗统计
     *
     * @param id 编号
     * @return 能源消耗统计
     */
    EnergyStatisticsDO getEnergyStatistics(Long id);

    /**
     * 获得能源消耗统计分页
     *
     * @param pageReqVO 分页查询
     * @return 能源消耗统计分页
     */
    PageResult<EnergyStatisticsDO> getEnergyStatisticsPage(EnergyStatisticsPageReqVO pageReqVO);

    /**
     * 计算与计划对比百分比
     *
     * @param energyTypeId 能源类型ID
     * @param statisticsDate 统计日期
     * @param statisticsType 统计类型
     * @param actualValue 实际值
     * @return 与计划对比百分比
     */
    Double calculateComparisonWithPlan(Long energyTypeId, String statisticsDate, Integer statisticsType, Double actualValue);

    /**
     * 计算与上期对比百分比
     *
     * @param energyTypeId 能源类型ID
     * @param statisticsDate 统计日期
     * @param statisticsType 统计类型
     * @param currentValue 当前值
     * @return 与上期对比百分比
     */
    Double calculateComparisonWithLastPeriod(Long energyTypeId, String statisticsDate, Integer statisticsType, Double currentValue);

}