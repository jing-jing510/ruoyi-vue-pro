package cn.iocoder.yudao.module.coal.service.sparepartinventorylog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinventorylog.SparePartInventoryLogDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 备件出入库记录 Service 接口
 *
 * @author 京京
 */
public interface SparePartInventoryLogService {

    /**
     * 创建备件出入库记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSparePartInventoryLog(@Valid SparePartInventoryLogSaveReqVO createReqVO);

    /**
     * 更新备件出入库记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSparePartInventoryLog(@Valid SparePartInventoryLogSaveReqVO updateReqVO);

    /**
     * 删除备件出入库记录
     *
     * @param id 编号
     */
    void deleteSparePartInventoryLog(Long id);

    /**
    * 批量删除备件出入库记录
    *
    * @param ids 编号
    */
    void deleteSparePartInventoryLogListByIds(List<Long> ids);

    /**
     * 获得备件出入库记录
     *
     * @param id 编号
     * @return 备件出入库记录
     */
    SparePartInventoryLogDO getSparePartInventoryLog(Long id);

    /**
     * 获得备件出入库记录分页
     *
     * @param pageReqVO 分页查询
     * @return 备件出入库记录分页
     */
    PageResult<SparePartInventoryLogDO> getSparePartInventoryLogPage(SparePartInventoryLogPageReqVO pageReqVO);

}