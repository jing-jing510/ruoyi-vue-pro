package cn.iocoder.yudao.module.coal.service.marketcostanalysis;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketcostanalysis.MarketCostAnalysisDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.coal.dal.mysql.marketcostanalysis.MarketCostAnalysisMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 综合成本核算 Service 实现类
 *
 * @author jingjing
 */
@Service
@Validated
public class MarketCostAnalysisServiceImpl implements MarketCostAnalysisService {

    @Resource
    private MarketCostAnalysisMapper marketCostAnalysisMapper;

    @Override
    public Long createMarketCostAnalysis(MarketCostAnalysisSaveReqVO createReqVO) {
        // 插入
        MarketCostAnalysisDO marketCostAnalysis = BeanUtils.toBean(createReqVO, MarketCostAnalysisDO.class);
        marketCostAnalysisMapper.insert(marketCostAnalysis);

        // 返回
        return marketCostAnalysis.getId();
    }

    @Override
    public void updateMarketCostAnalysis(MarketCostAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateMarketCostAnalysisExists(updateReqVO.getId());
        // 更新
        MarketCostAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, MarketCostAnalysisDO.class);
        marketCostAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteMarketCostAnalysis(Long id) {
        // 校验存在
        validateMarketCostAnalysisExists(id);
        // 删除
        marketCostAnalysisMapper.deleteById(id);
    }

    @Override
        public void deleteMarketCostAnalysisListByIds(List<Long> ids) {
        // 删除
        marketCostAnalysisMapper.deleteByIds(ids);
        }


    private void validateMarketCostAnalysisExists(Long id) {
        if (marketCostAnalysisMapper.selectById(id) == null) {
            throw exception(MARKET_COST_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public MarketCostAnalysisDO getMarketCostAnalysis(Long id) {
        return marketCostAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<MarketCostAnalysisDO> getMarketCostAnalysisPage(MarketCostAnalysisPageReqVO pageReqVO) {
        return marketCostAnalysisMapper.selectPage(pageReqVO);
    }

}