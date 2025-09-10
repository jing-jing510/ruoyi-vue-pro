package cn.iocoder.yudao.module.coal.dal.mysql.qualityinspection;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityDataDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 质量检测数据 Mapper
 *
 * @author 京京
 */
@Mapper
public interface QualityDataMapper extends BaseMapperX<QualityDataDO> {

    default PageResult<QualityDataDO> selectPage(PageParam reqVO, Long inspectionId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QualityDataDO>()
            .eq(QualityDataDO::getInspectionId, inspectionId)
            .orderByDesc(QualityDataDO::getId));
    }

    default int deleteByInspectionId(Long inspectionId) {
        return delete(QualityDataDO::getInspectionId, inspectionId);
    }

	default int deleteByInspectionIds(List<Long> inspectionIds) {
	    return deleteBatch(QualityDataDO::getInspectionId, inspectionIds);
	}

}
