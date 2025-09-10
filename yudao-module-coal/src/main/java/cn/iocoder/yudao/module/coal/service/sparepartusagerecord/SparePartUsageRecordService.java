package cn.iocoder.yudao.module.coal.service.sparepartusagerecord;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartusagerecord.SparePartUsageRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 备件使用记录 Service 接口
 *
 * @author 芋道源码
 */
public interface SparePartUsageRecordService {

    /**
     * 创建备件使用记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSparePartUsageRecord(@Valid SparePartUsageRecordSaveReqVO createReqVO);

    /**
     * 更新备件使用记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSparePartUsageRecord(@Valid SparePartUsageRecordSaveReqVO updateReqVO);

    /**
     * 删除备件使用记录
     *
     * @param id 编号
     */
    void deleteSparePartUsageRecord(Long id);

    /**
    * 批量删除备件使用记录
    *
    * @param ids 编号
    */
    void deleteSparePartUsageRecordListByIds(List<Long> ids);

    /**
     * 获得备件使用记录
     *
     * @param id 编号
     * @return 备件使用记录
     */
    SparePartUsageRecordDO getSparePartUsageRecord(Long id);

    /**
     * 获得备件使用记录分页
     *
     * @param pageReqVO 分页查询
     * @return 备件使用记录分页
     */
    PageResult<SparePartUsageRecordDO> getSparePartUsageRecordPage(SparePartUsageRecordPageReqVO pageReqVO);

}