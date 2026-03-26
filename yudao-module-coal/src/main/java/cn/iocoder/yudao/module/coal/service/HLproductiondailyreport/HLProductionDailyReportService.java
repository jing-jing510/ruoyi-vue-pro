package cn.iocoder.yudao.module.coal.service.HLproductiondailyreport;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionDailyReportPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionDailyReportSaveReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionPlanProgressRespVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.HLproductiondailyreport.HLProductionDailyReportDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 红林生产日报 Service 接口
 *
 * @author 京京
 */
public interface HLProductionDailyReportService {

    /**
     * 创建红林生产日报
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createHLProductionDailyReport(@Valid HLProductionDailyReportSaveReqVO createReqVO);

    /**
     * 更新红林生产日报
     *
     * @param updateReqVO 更新信息
     */
    void updateHLProductionDailyReport(@Valid HLProductionDailyReportSaveReqVO updateReqVO);

    /**
     * 删除红林生产日报
     *
     * @param id 编号
     */
    void deleteHLProductionDailyReport(Long id);

    /**
    * 批量删除红林生产日报
    *
    * @param ids 编号
    */
    void deleteHLProductionDailyReportListByIds(List<Long> ids);

    /**
     * 获得红林生产日报
     *
     * @param id 编号
     * @return 红林生产日报
     */
    HLProductionDailyReportDO getHLProductionDailyReport(Long id);

    /**
     * 获得红林生产日报分页
     *
     * @param pageReqVO 分页查询
     * @return 红林生产日报分页
     */
    PageResult<HLProductionDailyReportDO> getHLProductionDailyReportPage(HLProductionDailyReportPageReqVO pageReqVO);

    /**
     * 获得红林生产计划进展统计信息，包含年月计划完成百分比
     *
     * @return 红林生产计划进展统计
     */
    HLProductionPlanProgressRespVO getHLPlanProgress();

}