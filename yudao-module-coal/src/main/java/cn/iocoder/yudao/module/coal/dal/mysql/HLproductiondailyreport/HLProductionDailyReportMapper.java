package cn.iocoder.yudao.module.coal.dal.mysql.HLproductiondailyreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionDailyReportPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.HLproductiondailyreport.HLProductionDailyReportDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 红林生产日报 Mapper
 *
 * @author 京京
 */
@Mapper
public interface HLProductionDailyReportMapper extends BaseMapperX<HLProductionDailyReportDO> {

    default PageResult<HLProductionDailyReportDO> selectPage(HLProductionDailyReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HLProductionDailyReportDO>()
                .betweenIfPresent(HLProductionDailyReportDO::getReportDate, reqVO.getReportDate())
                .betweenIfPresent(HLProductionDailyReportDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(HLProductionDailyReportDO::getStopTime, reqVO.getStopTime())
                .betweenIfPresent(HLProductionDailyReportDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HLProductionDailyReportDO::getId));
    }

    /**
     * 查询指定时间范围内的生产日报
     */
    @TenantIgnore
    default List<HLProductionDailyReportDO> selectReportsByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HLProductionDailyReportDO>()
                .between(HLProductionDailyReportDO::getReportDate, startTime, endTime));
    }

}