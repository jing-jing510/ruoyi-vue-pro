package cn.iocoder.yudao.module.coal.service.qualityinspection;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.QualityInspectionPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.QualityInspectionSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityDataDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityInspectionDO;
import cn.iocoder.yudao.module.coal.dal.mysql.qualityinspection.QualityDataMapper;
import cn.iocoder.yudao.module.coal.dal.mysql.qualityinspection.QualityInspectionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.QUALITY_DATA_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.QUALITY_INSPECTION_NOT_EXISTS;

/**
 * 质量检测记录 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class QualityInspectionServiceImpl implements QualityInspectionService {

    @Resource
    private QualityInspectionMapper qualityInspectionMapper;
    @Resource
    private QualityDataMapper qualityDataMapper;

    @Override
    public Long createQualityInspection(QualityInspectionSaveReqVO createReqVO) {
        // 插入
        QualityInspectionDO qualityInspection = BeanUtils.toBean(createReqVO, QualityInspectionDO.class);
        qualityInspectionMapper.insert(qualityInspection);

        // 返回
        return qualityInspection.getId();
    }

    @Override
    public void updateQualityInspection(QualityInspectionSaveReqVO updateReqVO) {
        // 校验存在
        validateQualityInspectionExists(updateReqVO.getId());
        // 更新
        QualityInspectionDO updateObj = BeanUtils.toBean(updateReqVO, QualityInspectionDO.class);
        qualityInspectionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQualityInspection(Long id) {
        // 校验存在
        validateQualityInspectionExists(id);
        // 删除
        qualityInspectionMapper.deleteById(id);

        // 删除子表
        deleteQualityDataByInspectionId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteQualityInspectionListByIds(List<Long> ids) {
        // 删除
        qualityInspectionMapper.deleteByIds(ids);
    
    // 删除子表
            deleteQualityDataByInspectionIds(ids);
    }


    private void validateQualityInspectionExists(Long id) {
        if (qualityInspectionMapper.selectById(id) == null) {
            throw exception(QUALITY_INSPECTION_NOT_EXISTS);
        }
    }

    @Override
    public QualityInspectionDO getQualityInspection(Long id) {
        return qualityInspectionMapper.selectById(id);
    }

    @Override
    public PageResult<QualityInspectionDO> getQualityInspectionPage(QualityInspectionPageReqVO pageReqVO) {
        return qualityInspectionMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（质量检测数据） ====================

    @Override
    public PageResult<QualityDataDO> getQualityDataPage(PageParam pageReqVO, Long inspectionId) {
        return qualityDataMapper.selectPage(pageReqVO, inspectionId);
    }

    @Override
    public Long createQualityData(QualityDataDO qualityData) {
        qualityData.clean(); // 清理掉创建、更新时间等相关属性值
        qualityDataMapper.insert(qualityData);
        return qualityData.getId();
    }

    @Override
    public void updateQualityData(QualityDataDO qualityData) {
        // 校验存在
        validateQualityDataExists(qualityData.getId());
        // 更新
        qualityData.clean(); // 解决更新情况下：updateTime 不更新
        qualityDataMapper.updateById(qualityData);
    }

    @Override
    public void deleteQualityData(Long id) {
        // 删除
        qualityDataMapper.deleteById(id);
    }

	@Override
	public void deleteQualityDataListByIds(List<Long> ids) {
        // 删除
        qualityDataMapper.deleteByIds(ids);
	}

    @Override
    public QualityDataDO getQualityData(Long id) {
        return qualityDataMapper.selectById(id);
    }

    private void validateQualityDataExists(Long id) {
        if (qualityDataMapper.selectById(id) == null) {
            throw exception(QUALITY_DATA_NOT_EXISTS);
        }
    }

    private void deleteQualityDataByInspectionId(Long inspectionId) {
        qualityDataMapper.deleteByInspectionId(inspectionId);
    }

	private void deleteQualityDataByInspectionIds(List<Long> inspectionIds) {
        qualityDataMapper.deleteByInspectionIds(inspectionIds);
	}

}