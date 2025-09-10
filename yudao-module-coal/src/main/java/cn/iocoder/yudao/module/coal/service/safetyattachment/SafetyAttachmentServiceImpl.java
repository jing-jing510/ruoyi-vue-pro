package cn.iocoder.yudao.module.coal.service.safetyattachment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo.SafetyAttachmentPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo.SafetyAttachmentSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetyattachment.SafetyAttachmentDO;
import cn.iocoder.yudao.module.coal.dal.mysql.safetyattachment.SafetyAttachmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SAFETY_ATTACHMENT_NOT_EXISTS;

/**
 * 安全附件 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SafetyAttachmentServiceImpl implements SafetyAttachmentService {

    @Resource
    private SafetyAttachmentMapper safetyAttachmentMapper;

    @Override
    public Long createSafetyAttachment(SafetyAttachmentSaveReqVO createReqVO) {
        // 插入
        SafetyAttachmentDO safetyAttachment = BeanUtils.toBean(createReqVO, SafetyAttachmentDO.class);
        safetyAttachmentMapper.insert(safetyAttachment);

        // 返回
        return safetyAttachment.getId();
    }

    @Override
    public void updateSafetyAttachment(SafetyAttachmentSaveReqVO updateReqVO) {
        // 校验存在
        validateSafetyAttachmentExists(updateReqVO.getId());
        // 更新
        SafetyAttachmentDO updateObj = BeanUtils.toBean(updateReqVO, SafetyAttachmentDO.class);
        safetyAttachmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteSafetyAttachment(Long id) {
        // 校验存在
        validateSafetyAttachmentExists(id);
        // 删除
        safetyAttachmentMapper.deleteById(id);
    }

    @Override
        public void deleteSafetyAttachmentListByIds(List<Long> ids) {
        // 删除
        safetyAttachmentMapper.deleteByIds(ids);
        }


    private void validateSafetyAttachmentExists(Long id) {
        if (safetyAttachmentMapper.selectById(id) == null) {
            throw exception(SAFETY_ATTACHMENT_NOT_EXISTS);
        }
    }

    @Override
    public SafetyAttachmentDO getSafetyAttachment(Long id) {
        return safetyAttachmentMapper.selectById(id);
    }

    @Override
    public PageResult<SafetyAttachmentDO> getSafetyAttachmentPage(SafetyAttachmentPageReqVO pageReqVO) {
        return safetyAttachmentMapper.selectPage(pageReqVO);
    }

    @Override
    public Map<String, Object> getSafetyAttachmentStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 获取总附件数
        Long totalCount = safetyAttachmentMapper.selectCount();
        statistics.put("totalCount", totalCount);
        
        // 获取安全检查记录附件数
        Long recordAttachmentCount = safetyAttachmentMapper.selectCount(
            new LambdaQueryWrapperX<SafetyAttachmentDO>()
                .eq(SafetyAttachmentDO::getBusinessType, 1) // 安全检查记录
        );
        statistics.put("recordAttachmentCount", recordAttachmentCount);
        
        // 获取安全检查项目附件数
        Long itemAttachmentCount = safetyAttachmentMapper.selectCount(
            new LambdaQueryWrapperX<SafetyAttachmentDO>()
                .eq(SafetyAttachmentDO::getBusinessType, 2) // 安全检查项目
        );
        statistics.put("itemAttachmentCount", itemAttachmentCount);
        
        // 获取安全事故记录附件数
        Long accidentAttachmentCount = safetyAttachmentMapper.selectCount(
            new LambdaQueryWrapperX<SafetyAttachmentDO>()
                .eq(SafetyAttachmentDO::getBusinessType, 3) // 安全事故记录
        );
        statistics.put("accidentAttachmentCount", accidentAttachmentCount);
        
        // 获取今日上传附件数
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        
        Long todayAttachmentCount = safetyAttachmentMapper.selectCount(
            new LambdaQueryWrapperX<SafetyAttachmentDO>()
                .between(SafetyAttachmentDO::getUploadTime, startOfDay, endOfDay)
        );
        statistics.put("todayAttachmentCount", todayAttachmentCount);
        
        return statistics;
    }

}