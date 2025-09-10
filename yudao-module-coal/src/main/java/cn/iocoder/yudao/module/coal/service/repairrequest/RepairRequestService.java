package cn.iocoder.yudao.module.coal.service.repairrequest;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.repairrequest.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.repairrequest.RepairRequestDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 报修单 Service 接口
 *
 * @author 芋道源码
 */
public interface RepairRequestService {

    /**
     * 创建报修单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRepairRequest(@Valid RepairRequestSaveReqVO createReqVO);

    /**
     * 更新报修单
     *
     * @param updateReqVO 更新信息
     */
    void updateRepairRequest(@Valid RepairRequestSaveReqVO updateReqVO);

    /**
     * 删除报修单
     *
     * @param id 编号
     */
    void deleteRepairRequest(Long id);

    /**
    * 批量删除报修单
    *
    * @param ids 编号
    */
    void deleteRepairRequestListByIds(List<Long> ids);

    /**
     * 获得报修单
     *
     * @param id 编号
     * @return 报修单
     */
    RepairRequestDO getRepairRequest(Long id);

    /**
     * 获得报修单分页
     *
     * @param pageReqVO 分页查询
     * @return 报修单分页
     */
    PageResult<RepairRequestDO> getRepairRequestPage(RepairRequestPageReqVO pageReqVO);

}