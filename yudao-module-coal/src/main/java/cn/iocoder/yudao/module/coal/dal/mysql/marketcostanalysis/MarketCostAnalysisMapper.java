package cn.iocoder.yudao.module.coal.dal.mysql.marketcostanalysis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis.vo.MarketCostAnalysisPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketcostanalysis.MarketCostAnalysisDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 综合成本核算 Mapper
 *
 * @author jingjing
 */
@Mapper
public interface MarketCostAnalysisMapper extends BaseMapperX<MarketCostAnalysisDO> {

    default PageResult<MarketCostAnalysisDO> selectPage(MarketCostAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MarketCostAnalysisDO>()
                .betweenIfPresent(MarketCostAnalysisDO::getAnalysisDate, reqVO.getAnalysisDate())
                .orderByDesc(MarketCostAnalysisDO::getId));
    }

}