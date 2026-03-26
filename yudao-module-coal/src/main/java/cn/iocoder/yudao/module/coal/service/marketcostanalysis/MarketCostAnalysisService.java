package cn.iocoder.yudao.module.coal.service.marketcostanalysis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis.vo.MarketCostAnalysisPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis.vo.MarketCostAnalysisSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketcostanalysis.MarketCostAnalysisDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 综合成本核算 Service 接口
 *
 * @author jingjing
 */
public interface MarketCostAnalysisService {

    /**
     * 创建综合成本核算
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMarketCostAnalysis(@Valid MarketCostAnalysisSaveReqVO createReqVO);

    /**
     * 更新综合成本核算
     *
     * @param updateReqVO 更新信息
     */
    void updateMarketCostAnalysis(@Valid MarketCostAnalysisSaveReqVO updateReqVO);

    /**
     * 删除综合成本核算
     *
     * @param id 编号
     */
    void deleteMarketCostAnalysis(Long id);

    /**
    * 批量删除综合成本核算
    *
    * @param ids 编号
    */
    void deleteMarketCostAnalysisListByIds(List<Long> ids);

    /**
     * 获得综合成本核算
     *
     * @param id 编号
     * @return 综合成本核算
     */
    MarketCostAnalysisDO getMarketCostAnalysis(Long id);

    /**
     * 获得综合成本核算分页
     *
     * @param pageReqVO 分页查询
     * @return 综合成本核算分页
     */
    PageResult<MarketCostAnalysisDO> getMarketCostAnalysisPage(MarketCostAnalysisPageReqVO pageReqVO);

}