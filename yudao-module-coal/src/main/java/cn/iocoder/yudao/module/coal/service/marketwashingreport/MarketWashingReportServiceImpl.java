package cn.iocoder.yudao.module.coal.service.marketwashingreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo.MarketWashingReportPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo.MarketWashingReportSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketwashingreport.MarketWashingReportDO;
import cn.iocoder.yudao.module.coal.dal.mysql.marketwashingreport.MarketWashingReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.MARKET_WASHING_REPORT_NOT_EXISTS;

/**
 * 洗选分析报告 Service 实现类
 *
 * @author jingjing
 */
@Service
@Validated
public class MarketWashingReportServiceImpl implements MarketWashingReportService {

    @Resource
    private MarketWashingReportMapper marketWashingReportMapper;

    @Override
    public Long createMarketWashingReport(MarketWashingReportSaveReqVO createReqVO) {
        // 插入
        MarketWashingReportDO marketWashingReport = BeanUtils.toBean(createReqVO, MarketWashingReportDO.class);
        marketWashingReportMapper.insert(marketWashingReport);

        // 返回
        return marketWashingReport.getId();
    }

    @Override
    public void updateMarketWashingReport(MarketWashingReportSaveReqVO updateReqVO) {
        // 校验存在
        validateMarketWashingReportExists(updateReqVO.getId());
        // 更新
        MarketWashingReportDO updateObj = BeanUtils.toBean(updateReqVO, MarketWashingReportDO.class);
        marketWashingReportMapper.updateById(updateObj);
    }

    @Override
    public void deleteMarketWashingReport(Long id) {
        // 校验存在
        validateMarketWashingReportExists(id);
        // 删除
        marketWashingReportMapper.deleteById(id);
    }

    @Override
        public void deleteMarketWashingReportListByIds(List<Long> ids) {
        // 删除
        marketWashingReportMapper.deleteByIds(ids);
        }


    private void validateMarketWashingReportExists(Long id) {
        if (marketWashingReportMapper.selectById(id) == null) {
            throw exception(MARKET_WASHING_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public MarketWashingReportDO getMarketWashingReport(Long id) {
        return marketWashingReportMapper.selectById(id);
    }

    @Override
    public PageResult<MarketWashingReportDO> getMarketWashingReportPage(MarketWashingReportPageReqVO pageReqVO) {
        return marketWashingReportMapper.selectPage(pageReqVO);
    }

}