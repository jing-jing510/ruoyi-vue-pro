package cn.iocoder.yudao.module.coal.service.safetyaccident;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.safetyaccident.vo.SafetyAccidentPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.safetyaccident.vo.SafetyAccidentSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetyaccident.SafetyAccidentDO;
import cn.iocoder.yudao.module.coal.dal.mysql.safetyaccident.SafetyAccidentMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SAFETY_ACCIDENT_NOT_EXISTS;

/**
 * 安全事故记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SafetyAccidentServiceImpl implements SafetyAccidentService {

    @Resource
    private SafetyAccidentMapper safetyAccidentMapper;

    @Override
    public Long createSafetyAccident(SafetyAccidentSaveReqVO createReqVO) {
        // 插入
        SafetyAccidentDO safetyAccident = BeanUtils.toBean(createReqVO, SafetyAccidentDO.class);
        safetyAccidentMapper.insert(safetyAccident);

        // 返回
        return safetyAccident.getId();
    }

    @Override
    public void updateSafetyAccident(SafetyAccidentSaveReqVO updateReqVO) {
        // 校验存在
        validateSafetyAccidentExists(updateReqVO.getId());
        // 更新
        SafetyAccidentDO updateObj = BeanUtils.toBean(updateReqVO, SafetyAccidentDO.class);
        safetyAccidentMapper.updateById(updateObj);
    }

    @Override
    public void deleteSafetyAccident(Long id) {
        // 校验存在
        validateSafetyAccidentExists(id);
        // 删除
        safetyAccidentMapper.deleteById(id);
    }

    @Override
        public void deleteSafetyAccidentListByIds(List<Long> ids) {
        // 删除
        safetyAccidentMapper.deleteByIds(ids);
        }


    private void validateSafetyAccidentExists(Long id) {
        if (safetyAccidentMapper.selectById(id) == null) {
            throw exception(SAFETY_ACCIDENT_NOT_EXISTS);
        }
    }

    @Override
    public SafetyAccidentDO getSafetyAccident(Long id) {
        return safetyAccidentMapper.selectById(id);
    }

    @Override
    public PageResult<SafetyAccidentDO> getSafetyAccidentPage(SafetyAccidentPageReqVO pageReqVO) {
        return safetyAccidentMapper.selectPage(pageReqVO);
    }

    @Override
    public Map<String, Object> getSafetyAccidentStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 获取总事故数
        Long totalCount = safetyAccidentMapper.selectCount();
        statistics.put("totalCount", totalCount);
        
        // 获取待处理事故数（根据事故状态）
        Long pendingCount = safetyAccidentMapper.selectCount(
            new LambdaQueryWrapperX<SafetyAccidentDO>()
                .eq(SafetyAccidentDO::getAccidentStatus, 1) // 待处理状态
        );
        statistics.put("pendingCount", pendingCount);
        
        // 获取重大事故数（根据事故等级）
        Long severeCount = safetyAccidentMapper.selectCount(
            new LambdaQueryWrapperX<SafetyAccidentDO>()
                .eq(SafetyAccidentDO::getAccidentLevel, 1) // 重大事故等级
        );
        statistics.put("severeCount", severeCount);
        
        // 获取本月事故数
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
        
        Long monthlyCount = safetyAccidentMapper.selectCount(
            new LambdaQueryWrapperX<SafetyAccidentDO>()
                .between(SafetyAccidentDO::getAccidentTime, startOfMonth, endOfMonth)
        );
        statistics.put("monthlyCount", monthlyCount);
        
        return statistics;
    }

}