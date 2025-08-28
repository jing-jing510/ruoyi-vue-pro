package cn.iocoder.yudao.module.coal.dal.mysql.productiondailyreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo.ProductionDailyReportPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.productiondailyreport.ProductionDailyReportDO;
import org.apache.ibatis.annotations.Mapper;

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

}