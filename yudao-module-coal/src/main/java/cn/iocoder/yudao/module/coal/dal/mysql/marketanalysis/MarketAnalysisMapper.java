package cn.iocoder.yudao.module.coal.dal.mysql.marketanalysis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo.MarketAnalysisPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketanalysis.MarketAnalysisDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 市场分析 Mapper
 *
 * @author 京京
 */
@Mapper
public interface MarketAnalysisMapper extends BaseMapperX<MarketAnalysisDO> {

    default PageResult<MarketAnalysisDO> selectPage(MarketAnalysisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MarketAnalysisDO>()
                .betweenIfPresent(MarketAnalysisDO::getAnalysisDate, reqVO.getAnalysisDate())
                .eqIfPresent(MarketAnalysisDO::getRemark, reqVO.getRemark())
                .orderByDesc(MarketAnalysisDO::getId));
    }

}