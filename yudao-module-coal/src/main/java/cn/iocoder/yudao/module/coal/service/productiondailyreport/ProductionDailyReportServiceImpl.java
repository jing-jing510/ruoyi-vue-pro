package cn.iocoder.yudao.module.coal.service.productiondailyreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo.ProductionDailyReportPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo.ProductionDailyReportSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.productiondailyreport.ProductionDailyReportDO;
import cn.iocoder.yudao.module.coal.dal.mysql.productiondailyreport.ProductionDailyReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.PRODUCTION_DAILY_REPORT_NOT_EXISTS;

/**
 * 现场生产日报 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class ProductionDailyReportServiceImpl implements ProductionDailyReportService {

    @Resource
    private ProductionDailyReportMapper productionDailyReportMapper;

    @Override
    public Long createProductionDailyReport(ProductionDailyReportSaveReqVO createReqVO) {
        // 插入
        ProductionDailyReportDO productionDailyReport = BeanUtils.toBean(createReqVO, ProductionDailyReportDO.class);
        productionDailyReportMapper.insert(productionDailyReport);

        // 返回
        return productionDailyReport.getId();
    }

    @Override
    public void updateProductionDailyReport(ProductionDailyReportSaveReqVO updateReqVO) {
        // 校验存在
        validateProductionDailyReportExists(updateReqVO.getId());
        // 更新
        ProductionDailyReportDO updateObj = BeanUtils.toBean(updateReqVO, ProductionDailyReportDO.class);
        productionDailyReportMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductionDailyReport(Long id) {
        // 校验存在
        validateProductionDailyReportExists(id);
        // 删除
        productionDailyReportMapper.deleteById(id);
    }

    @Override
        public void deleteProductionDailyReportListByIds(List<Long> ids) {
        // 删除
        productionDailyReportMapper.deleteByIds(ids);
        }


    private void validateProductionDailyReportExists(Long id) {
        if (productionDailyReportMapper.selectById(id) == null) {
            throw exception(PRODUCTION_DAILY_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public ProductionDailyReportDO getProductionDailyReport(Long id) {
        return productionDailyReportMapper.selectById(id);
    }

    @Override
    public PageResult<ProductionDailyReportDO> getProductionDailyReportPage(ProductionDailyReportPageReqVO pageReqVO) {
        return productionDailyReportMapper.selectPage(pageReqVO);
    }

}