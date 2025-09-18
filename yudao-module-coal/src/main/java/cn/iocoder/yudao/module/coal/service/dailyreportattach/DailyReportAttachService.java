package cn.iocoder.yudao.module.coal.service.dailyreportattach;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo.DailyReportAttachPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo.DailyReportAttachSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.dailyreportattach.DailyReportAttachDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 生产日报附件上传 Service 接口
 *
 * @author 京京
 */
public interface DailyReportAttachService {

    /**
     * 创建生产日报附件上传
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDailyReportAttach(@Valid DailyReportAttachSaveReqVO createReqVO);

    /**
     * 更新生产日报附件上传
     *
     * @param updateReqVO 更新信息
     */
    void updateDailyReportAttach(@Valid DailyReportAttachSaveReqVO updateReqVO);

    /**
     * 删除生产日报附件上传
     *
     * @param id 编号
     */
    void deleteDailyReportAttach(Long id);

    /**
    * 批量删除生产日报附件上传
    *
    * @param ids 编号
    */
    void deleteDailyReportAttachListByIds(List<Long> ids);

    /**
     * 获得生产日报附件上传
     *
     * @param id 编号
     * @return 生产日报附件上传
     */
    DailyReportAttachDO getDailyReportAttach(Long id);

    /**
     * 获得生产日报附件上传分页
     *
     * @param pageReqVO 分页查询
     * @return 生产日报附件上传分页
     */
    PageResult<DailyReportAttachDO> getDailyReportAttachPage(DailyReportAttachPageReqVO pageReqVO);

}