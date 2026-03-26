package cn.iocoder.yudao.module.coal.service.marketanalysis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo.MarketAnalysisPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo.MarketAnalysisSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketanalysis.MarketAnalysisDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 市场分析 Service 接口
 *
 * @author 京京
 */
public interface MarketAnalysisService {

    /**
     * 创建市场分析
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMarketAnalysis(@Valid MarketAnalysisSaveReqVO createReqVO);

    /**
     * 更新市场分析
     *
     * @param updateReqVO 更新信息
     */
    void updateMarketAnalysis(@Valid MarketAnalysisSaveReqVO updateReqVO);

    /**
     * 删除市场分析
     *
     * @param id 编号
     */
    void deleteMarketAnalysis(Long id);

    /**
    * 批量删除市场分析
    *
    * @param ids 编号
    */
    void deleteMarketAnalysisListByIds(List<Long> ids);

    /**
     * 获得市场分析
     *
     * @param id 编号
     * @return 市场分析
     */
    MarketAnalysisDO getMarketAnalysis(Long id);

    /**
     * 获得市场分析分页
     *
     * @param pageReqVO 分页查询
     * @return 市场分析分页
     */
    PageResult<MarketAnalysisDO> getMarketAnalysisPage(MarketAnalysisPageReqVO pageReqVO);

}