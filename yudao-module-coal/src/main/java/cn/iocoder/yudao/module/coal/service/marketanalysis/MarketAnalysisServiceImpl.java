package cn.iocoder.yudao.module.coal.service.marketanalysis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo.MarketAnalysisPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo.MarketAnalysisSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketanalysis.MarketAnalysisDO;
import cn.iocoder.yudao.module.coal.dal.mysql.marketanalysis.MarketAnalysisMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.MARKET_ANALYSIS_NOT_EXISTS;

/**
 * 市场分析 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class MarketAnalysisServiceImpl implements MarketAnalysisService {

    @Resource
    private MarketAnalysisMapper marketAnalysisMapper;

    @Override
    public Long createMarketAnalysis(MarketAnalysisSaveReqVO createReqVO) {
        // 插入
        MarketAnalysisDO marketAnalysis = BeanUtils.toBean(createReqVO, MarketAnalysisDO.class);
        marketAnalysisMapper.insert(marketAnalysis);

        // 返回
        return marketAnalysis.getId();
    }

    @Override
    public void updateMarketAnalysis(MarketAnalysisSaveReqVO updateReqVO) {
        // 校验存在
        validateMarketAnalysisExists(updateReqVO.getId());
        // 更新
        MarketAnalysisDO updateObj = BeanUtils.toBean(updateReqVO, MarketAnalysisDO.class);
        marketAnalysisMapper.updateById(updateObj);
    }

    @Override
    public void deleteMarketAnalysis(Long id) {
        // 校验存在
        validateMarketAnalysisExists(id);
        // 删除
        marketAnalysisMapper.deleteById(id);
    }

    @Override
        public void deleteMarketAnalysisListByIds(List<Long> ids) {
        // 删除
        marketAnalysisMapper.deleteByIds(ids);
        }


    private void validateMarketAnalysisExists(Long id) {
        if (marketAnalysisMapper.selectById(id) == null) {
            throw exception(MARKET_ANALYSIS_NOT_EXISTS);
        }
    }

    @Override
    public MarketAnalysisDO getMarketAnalysis(Long id) {
        return marketAnalysisMapper.selectById(id);
    }

    @Override
    public PageResult<MarketAnalysisDO> getMarketAnalysisPage(MarketAnalysisPageReqVO pageReqVO) {
        return marketAnalysisMapper.selectPage(pageReqVO);
    }

}