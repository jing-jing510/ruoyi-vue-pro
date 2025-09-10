package cn.iocoder.yudao.module.coal.service.productiondailyreport;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.productiondailyreport.ProductionDailyReportDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 现场生产日报 Service 接口
 *
 * @author 京京
 */
public interface ProductionDailyReportService {

    /**
     * 创建现场生产日报
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductionDailyReport(@Valid ProductionDailyReportSaveReqVO createReqVO);

    /**
     * 更新现场生产日报
     *
     * @param updateReqVO 更新信息
     */
    void updateProductionDailyReport(@Valid ProductionDailyReportSaveReqVO updateReqVO);

    /**
     * 删除现场生产日报
     *
     * @param id 编号
     */
    void deleteProductionDailyReport(Long id);

    /**
    * 批量删除现场生产日报
    *
    * @param ids 编号
    */
    void deleteProductionDailyReportListByIds(List<Long> ids);

    /**
     * 获得现场生产日报
     *
     * @param id 编号
     * @return 现场生产日报
     */
    ProductionDailyReportDO getProductionDailyReport(Long id);

    /**
     * 获得现场生产日报分页
     *
     * @param pageReqVO 分页查询
     * @return 现场生产日报分页
     */
    PageResult<ProductionDailyReportDO> getProductionDailyReportPage(ProductionDailyReportPageReqVO pageReqVO);

    /**
     * 获得生产日报统计信息
     *
     * @return 生产日报统计信息
     */
    ProductionDailyReportStatisticsRespVO getProductionDailyReportStatistics();

}