package cn.iocoder.yudao.module.coal.service.marketwashingreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo.MarketWashingReportPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo.MarketWashingReportSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketwashingreport.MarketWashingReportDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 洗选分析报告 Service 接口
 *
 * @author jingjing
 */
public interface MarketWashingReportService {

    /**
     * 创建洗选分析报告
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMarketWashingReport(@Valid MarketWashingReportSaveReqVO createReqVO);

    /**
     * 更新洗选分析报告
     *
     * @param updateReqVO 更新信息
     */
    void updateMarketWashingReport(@Valid MarketWashingReportSaveReqVO updateReqVO);

    /**
     * 删除洗选分析报告
     *
     * @param id 编号
     */
    void deleteMarketWashingReport(Long id);

    /**
    * 批量删除洗选分析报告
    *
    * @param ids 编号
    */
    void deleteMarketWashingReportListByIds(List<Long> ids);

    /**
     * 获得洗选分析报告
     *
     * @param id 编号
     * @return 洗选分析报告
     */
    MarketWashingReportDO getMarketWashingReport(Long id);

    /**
     * 获得洗选分析报告分页
     *
     * @param pageReqVO 分页查询
     * @return 洗选分析报告分页
     */
    PageResult<MarketWashingReportDO> getMarketWashingReportPage(MarketWashingReportPageReqVO pageReqVO);

}