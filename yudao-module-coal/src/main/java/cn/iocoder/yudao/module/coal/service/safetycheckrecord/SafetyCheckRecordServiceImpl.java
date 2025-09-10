package cn.iocoder.yudao.module.coal.service.safetycheckrecord;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord.vo.SafetyCheckRecordPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord.vo.SafetyCheckRecordSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord.SafetyCheckItemDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord.SafetyCheckRecordDO;
import cn.iocoder.yudao.module.coal.dal.mysql.safetycheckrecord.SafetyCheckItemMapper;
import cn.iocoder.yudao.module.coal.dal.mysql.safetycheckrecord.SafetyCheckRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SAFETY_CHECK_ITEM_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SAFETY_CHECK_RECORD_NOT_EXISTS;

/**
 * 安全检查记录 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class SafetyCheckRecordServiceImpl implements SafetyCheckRecordService {

    @Resource
    private SafetyCheckRecordMapper safetyCheckRecordMapper;
    @Resource
    private SafetyCheckItemMapper safetyCheckItemMapper;

    @Override
    public Long createSafetyCheckRecord(SafetyCheckRecordSaveReqVO createReqVO) {
        // 插入
        SafetyCheckRecordDO safetyCheckRecord = BeanUtils.toBean(createReqVO, SafetyCheckRecordDO.class);
        safetyCheckRecordMapper.insert(safetyCheckRecord);

        // 返回
        return safetyCheckRecord.getId();
    }

    @Override
    public void updateSafetyCheckRecord(SafetyCheckRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateSafetyCheckRecordExists(updateReqVO.getId());
        // 更新
        SafetyCheckRecordDO updateObj = BeanUtils.toBean(updateReqVO, SafetyCheckRecordDO.class);
        safetyCheckRecordMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSafetyCheckRecord(Long id) {
        // 校验存在
        validateSafetyCheckRecordExists(id);
        // 删除
        safetyCheckRecordMapper.deleteById(id);

        // 删除子表
        deleteSafetyCheckItemByRecordId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteSafetyCheckRecordListByIds(List<Long> ids) {
        // 删除
        safetyCheckRecordMapper.deleteByIds(ids);
    
    // 删除子表
            deleteSafetyCheckItemByRecordIds(ids);
    }


    private void validateSafetyCheckRecordExists(Long id) {
        if (safetyCheckRecordMapper.selectById(id) == null) {
            throw exception(SAFETY_CHECK_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public SafetyCheckRecordDO getSafetyCheckRecord(Long id) {
        return safetyCheckRecordMapper.selectById(id);
    }

    @Override
    public PageResult<SafetyCheckRecordDO> getSafetyCheckRecordPage(SafetyCheckRecordPageReqVO pageReqVO) {
        return safetyCheckRecordMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（安全检查项目） ====================

    @Override
    public PageResult<SafetyCheckItemDO> getSafetyCheckItemPage(PageParam pageReqVO, Long recordId) {
        return safetyCheckItemMapper.selectPage(pageReqVO, recordId);
    }

    @Override
    public Long createSafetyCheckItem(SafetyCheckItemDO safetyCheckItem) {
        safetyCheckItem.clean(); // 清理掉创建、更新时间等相关属性值
        safetyCheckItemMapper.insert(safetyCheckItem);
        return safetyCheckItem.getId();
    }

    @Override
    public void updateSafetyCheckItem(SafetyCheckItemDO safetyCheckItem) {
        // 校验存在
        validateSafetyCheckItemExists(safetyCheckItem.getId());
        // 更新
        safetyCheckItem.clean(); // 解决更新情况下：updateTime 不更新
        safetyCheckItemMapper.updateById(safetyCheckItem);
    }

    @Override
    public void deleteSafetyCheckItem(Long id) {
        // 删除
        safetyCheckItemMapper.deleteById(id);
    }

	@Override
	public void deleteSafetyCheckItemListByIds(List<Long> ids) {
        // 删除
        safetyCheckItemMapper.deleteByIds(ids);
	}

    @Override
    public SafetyCheckItemDO getSafetyCheckItem(Long id) {
        return safetyCheckItemMapper.selectById(id);
    }

    @Override
    public List<SafetyCheckItemDO> getSafetyCheckItemListByRecordId(Long recordId) {
        return safetyCheckItemMapper.selectListByRecordId(recordId);
    }

    @Override
    public Map<String, Object> getSafetyCheckRecordStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 获取总记录数
        Long totalCount = safetyCheckRecordMapper.selectCount();
        statistics.put("totalCount", totalCount);
        
        // 获取已提交的记录数（已完成检查的记录）
        Long submittedCount = safetyCheckRecordMapper.selectCount(
            new LambdaQueryWrapperX<SafetyCheckRecordDO>()
                .eq(SafetyCheckRecordDO::getRecordStatus, 2) // 已提交状态
        );
        statistics.put("submittedCount", submittedCount);
        
        // 获取待审核的记录数
        Long pendingAuditCount = safetyCheckRecordMapper.selectCount(
            new LambdaQueryWrapperX<SafetyCheckRecordDO>()
                .eq(SafetyCheckRecordDO::getRecordStatus, 1) // 待审核状态
        );
        statistics.put("pendingAuditCount", pendingAuditCount);
        
        // 计算平均合格率（从已提交记录中计算）
        List<SafetyCheckRecordDO> submittedRecords = safetyCheckRecordMapper.selectList(
            new LambdaQueryWrapperX<SafetyCheckRecordDO>()
                .eq(SafetyCheckRecordDO::getRecordStatus, 2)
                .isNotNull(SafetyCheckRecordDO::getQualifiedRate)
        );
        
        double avgQualifiedRate = 0;
        if (!submittedRecords.isEmpty()) {
            double totalRate = submittedRecords.stream()
                .mapToDouble(record -> record.getQualifiedRate() != null ? record.getQualifiedRate().doubleValue() : 0)
                .sum();
            avgQualifiedRate = totalRate / submittedRecords.size();
        }
        statistics.put("qualifiedRate", Math.round(avgQualifiedRate * 100.0) / 100.0);
        
        return statistics;
    }

    @Override
    public Map<String, Object> getSafetyCheckItemStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 获取总项目数
        Long totalCount = safetyCheckItemMapper.selectCount();
        statistics.put("totalCount", totalCount);
        
        // 获取待整改项目数
        Long pendingCount = safetyCheckItemMapper.selectCount(
            new LambdaQueryWrapperX<SafetyCheckItemDO>()
                .eq(SafetyCheckItemDO::getRectificationStatus, 1) // 待整改状态
        );
        statistics.put("pendingCount", pendingCount);
        
        // 获取已完成项目数
        Long completedCount = safetyCheckItemMapper.selectCount(
            new LambdaQueryWrapperX<SafetyCheckItemDO>()
                .eq(SafetyCheckItemDO::getRectificationStatus, 3) // 已完成状态
        );
        statistics.put("completedCount", completedCount);
        
        // 获取合格项目数
        Long qualifiedCount = safetyCheckItemMapper.selectCount(
            new LambdaQueryWrapperX<SafetyCheckItemDO>()
                .eq(SafetyCheckItemDO::getCheckResult, 1) // 合格
        );
        statistics.put("qualifiedCount", qualifiedCount);
        
        // 获取不合格项目数
        Long unqualifiedCount = safetyCheckItemMapper.selectCount(
            new LambdaQueryWrapperX<SafetyCheckItemDO>()
                .eq(SafetyCheckItemDO::getCheckResult, 2) // 不合格
        );
        statistics.put("unqualifiedCount", unqualifiedCount);
        
        return statistics;
    }

    private void validateSafetyCheckItemExists(Long id) {
        if (safetyCheckItemMapper.selectById(id) == null) {
            throw exception(SAFETY_CHECK_ITEM_NOT_EXISTS);
        }
    }

    private void deleteSafetyCheckItemByRecordId(Long recordId) {
        safetyCheckItemMapper.deleteByRecordId(recordId);
    }

	private void deleteSafetyCheckItemByRecordIds(List<Long> recordIds) {
        safetyCheckItemMapper.deleteByRecordIds(recordIds);
	}

}