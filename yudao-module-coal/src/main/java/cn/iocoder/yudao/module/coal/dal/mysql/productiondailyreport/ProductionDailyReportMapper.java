package cn.iocoder.yudao.module.coal.dal.mysql.productiondailyreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo.ProductionDailyReportPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.productiondailyreport.ProductionDailyReportDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 现场生产日报 Mapper
 *
 * @author 京京
 */
@Mapper
public interface ProductionDailyReportMapper extends BaseMapperX<ProductionDailyReportDO> {

    default PageResult<ProductionDailyReportDO> selectPage(ProductionDailyReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductionDailyReportDO>()
                .betweenIfPresent(ProductionDailyReportDO::getReportDate, reqVO.getReportDate())
                .eqIfPresent(ProductionDailyReportDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(ProductionDailyReportDO::getShiftLeaderId, reqVO.getShiftLeaderId())
                .betweenIfPresent(ProductionDailyReportDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(ProductionDailyReportDO::getStopTime, reqVO.getStopTime())
                .betweenIfPresent(ProductionDailyReportDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductionDailyReportDO::getId));
    }

    /**
     * 查询今日生产日报数据
     */
    @Select("SELECT * FROM coal_production_daily_report " +
            "WHERE report_date >= #{startTime} AND report_date <= #{endTime} " +
            "AND deleted = 0 " +
            "ORDER BY report_date DESC")
    List<ProductionDailyReportDO> selectTodayReports(@Param("startTime") LocalDateTime startTime,
                                                     @Param("endTime") LocalDateTime endTime);

    /**
     * 查询月度生产日报数据
     */
    @Select("SELECT * FROM coal_production_daily_report " +
            "WHERE report_date >= #{startTime} AND report_date <= #{endTime} " +
            "AND deleted = 0 " +
            "ORDER BY report_date DESC")
    List<ProductionDailyReportDO> selectMonthlyReports(@Param("startTime") LocalDateTime startTime,
                                                       @Param("endTime") LocalDateTime endTime);

    /**
     * 查询月度生产天数（基于日报日期去重）
     */
    @Select("SELECT COUNT(DISTINCT DATE(report_date)) FROM coal_production_daily_report " +
            "WHERE report_date >= #{startTime} AND report_date <= #{endTime} " +
            "AND deleted = 0")
    int selectProductionDaysCount(@Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);

    /**
     * 查询总生产天数（基于日报数量）
     */
    @Select("SELECT COUNT(DISTINCT DATE(report_date)) FROM coal_production_daily_report " +
            "WHERE deleted = 0")
    int selectTotalProductionDaysCount();

}