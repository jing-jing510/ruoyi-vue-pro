package cn.iocoder.yudao.module.coal.dal.mysql.energystatistics;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.energystatistics.EnergyStatisticsDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.energystatistics.vo.*;

/**
 * 能源消耗统计 Mapper
 *
 * @author 京京
 */
@Mapper
public interface EnergyStatisticsMapper extends BaseMapperX<EnergyStatisticsDO> {

    default PageResult<EnergyStatisticsDO> selectPage(EnergyStatisticsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EnergyStatisticsDO>()
                .betweenIfPresent(EnergyStatisticsDO::getStatisticsDate, reqVO.getStatisticsDate())
                .eqIfPresent(EnergyStatisticsDO::getStatisticsType, reqVO.getStatisticsType())
                .eqIfPresent(EnergyStatisticsDO::getEnergyTypeId, reqVO.getEnergyTypeId())
                .eqIfPresent(EnergyStatisticsDO::getStatisticsStatus, reqVO.getStatisticsStatus())
                .betweenIfPresent(EnergyStatisticsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EnergyStatisticsDO::getId));
    }

    /**
     * 根据能源类型ID、统计日期和统计类型查询计划值
     * 这里需要根据实际业务逻辑来实现，暂时返回null
     */
    default Double selectPlanValue(Long energyTypeId, String statisticsDate, Integer statisticsType) {
        // TODO: 这里需要根据实际的计划表来实现
        // 暂时返回null，表示没有计划数据
        return null;
    }

    /**
     * 根据能源类型ID、统计日期和统计类型查询上期值
     */
    default Double selectLastPeriodValue(Long energyTypeId, String statisticsDate, Integer statisticsType) {
        // 根据统计类型计算上期日期
        String lastPeriodDate = calculateLastPeriodDate(statisticsDate, statisticsType);
        
        EnergyStatisticsDO lastPeriod = selectOne(new LambdaQueryWrapperX<EnergyStatisticsDO>()
                .eq(EnergyStatisticsDO::getEnergyTypeId, energyTypeId)
                .eq(EnergyStatisticsDO::getStatisticsType, statisticsType)
                .eq(EnergyStatisticsDO::getStatisticsDate, lastPeriodDate)
                .orderByDesc(EnergyStatisticsDO::getId)
                .last("LIMIT 1"));
        
        return lastPeriod != null ? lastPeriod.getTotalConsumption().doubleValue() : null;
    }

    /**
     * 计算上期日期
     */
    default String calculateLastPeriodDate(String statisticsDate, Integer statisticsType) {
        // 这里需要根据统计类型来计算上期日期
        // 1-日统计，2-月统计，3-年统计，4-班统计
        // 暂时返回空字符串，实际应该根据业务逻辑计算
        return "";
    }

}