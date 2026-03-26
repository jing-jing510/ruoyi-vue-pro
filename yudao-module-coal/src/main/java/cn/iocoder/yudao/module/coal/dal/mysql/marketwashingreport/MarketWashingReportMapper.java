package cn.iocoder.yudao.module.coal.dal.mysql.marketwashingreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo.MarketWashingReportPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketwashingreport.MarketWashingReportDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 洗选分析报告 Mapper
 *
 * @author jingjing
 */
@Mapper
public interface MarketWashingReportMapper extends BaseMapperX<MarketWashingReportDO> {

    default PageResult<MarketWashingReportDO> selectPage(MarketWashingReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MarketWashingReportDO>()
                .betweenIfPresent(MarketWashingReportDO::getReportDate, reqVO.getReportDate())
                .eqIfPresent(MarketWashingReportDO::getRemark, reqVO.getRemark())
                .orderByDesc(MarketWashingReportDO::getId));
    }

}