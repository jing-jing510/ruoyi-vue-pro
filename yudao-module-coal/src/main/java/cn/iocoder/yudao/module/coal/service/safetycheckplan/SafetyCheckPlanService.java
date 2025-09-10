package cn.iocoder.yudao.module.coal.service.safetycheckplan;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckplan.SafetyCheckPlanDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 安全检查计划 Service 接口
 *
 * @author 京京
 */
public interface SafetyCheckPlanService {

    /**
     * 创建安全检查计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSafetyCheckPlan(@Valid SafetyCheckPlanSaveReqVO createReqVO);

    /**
     * 更新安全检查计划
     *
     * @param updateReqVO 更新信息
     */
    void updateSafetyCheckPlan(@Valid SafetyCheckPlanSaveReqVO updateReqVO);

    /**
     * 删除安全检查计划
     *
     * @param id 编号
     */
    void deleteSafetyCheckPlan(Long id);

    /**
    * 批量删除安全检查计划
    *
    * @param ids 编号
    */
    void deleteSafetyCheckPlanListByIds(List<Long> ids);

    /**
     * 获得安全检查计划
     *
     * @param id 编号
     * @return 安全检查计划
     */
    SafetyCheckPlanDO getSafetyCheckPlan(Long id);

    /**
     * 获得安全检查计划分页
     *
     * @param pageReqVO 分页查询
     * @return 安全检查计划分页
     */
    PageResult<SafetyCheckPlanDO> getSafetyCheckPlanPage(SafetyCheckPlanPageReqVO pageReqVO);

}