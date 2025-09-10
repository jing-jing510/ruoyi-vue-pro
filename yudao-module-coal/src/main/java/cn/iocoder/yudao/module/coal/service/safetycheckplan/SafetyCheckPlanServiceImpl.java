package cn.iocoder.yudao.module.coal.service.safetycheckplan;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan.vo.SafetyCheckPlanPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan.vo.SafetyCheckPlanSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckplan.SafetyCheckPlanDO;
import cn.iocoder.yudao.module.coal.dal.mysql.safetycheckplan.SafetyCheckPlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SAFETY_CHECK_PLAN_NOT_EXISTS;

/**
 * 安全检查计划 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class SafetyCheckPlanServiceImpl implements SafetyCheckPlanService {

    @Resource
    private SafetyCheckPlanMapper safetyCheckPlanMapper;

    @Override
    public Long createSafetyCheckPlan(SafetyCheckPlanSaveReqVO createReqVO) {
        // 插入
        SafetyCheckPlanDO safetyCheckPlan = BeanUtils.toBean(createReqVO, SafetyCheckPlanDO.class);
        safetyCheckPlanMapper.insert(safetyCheckPlan);

        // 返回
        return safetyCheckPlan.getId();
    }

    @Override
    public void updateSafetyCheckPlan(SafetyCheckPlanSaveReqVO updateReqVO) {
        // 校验存在
        validateSafetyCheckPlanExists(updateReqVO.getId());
        // 更新
        SafetyCheckPlanDO updateObj = BeanUtils.toBean(updateReqVO, SafetyCheckPlanDO.class);
        safetyCheckPlanMapper.updateById(updateObj);
    }

    @Override
    public void deleteSafetyCheckPlan(Long id) {
        // 校验存在
        validateSafetyCheckPlanExists(id);
        // 删除
        safetyCheckPlanMapper.deleteById(id);
    }

    @Override
        public void deleteSafetyCheckPlanListByIds(List<Long> ids) {
        // 删除
        safetyCheckPlanMapper.deleteByIds(ids);
        }


    private void validateSafetyCheckPlanExists(Long id) {
        if (safetyCheckPlanMapper.selectById(id) == null) {
            throw exception(SAFETY_CHECK_PLAN_NOT_EXISTS);
        }
    }

    @Override
    public SafetyCheckPlanDO getSafetyCheckPlan(Long id) {
        return safetyCheckPlanMapper.selectById(id);
    }

    @Override
    public PageResult<SafetyCheckPlanDO> getSafetyCheckPlanPage(SafetyCheckPlanPageReqVO pageReqVO) {
        return safetyCheckPlanMapper.selectPage(pageReqVO);
    }

}