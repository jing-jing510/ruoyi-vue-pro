package cn.iocoder.yudao.module.coal.dal.mysql.safetycheckrecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord.SafetyCheckItemDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 安全检查项目 Mapper
 *
 * @author 京京
 */
@Mapper
public interface SafetyCheckItemMapper extends BaseMapperX<SafetyCheckItemDO> {

    default PageResult<SafetyCheckItemDO> selectPage(PageParam reqVO, Long recordId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SafetyCheckItemDO>()
            .eq(SafetyCheckItemDO::getRecordId, recordId)
            .orderByDesc(SafetyCheckItemDO::getId));
    }

    default List<SafetyCheckItemDO> selectListByRecordId(Long recordId) {
        return selectList(new LambdaQueryWrapperX<SafetyCheckItemDO>()
            .eq(SafetyCheckItemDO::getRecordId, recordId)
            .orderByDesc(SafetyCheckItemDO::getId));
    }

    default int deleteByRecordId(Long recordId) {
        return delete(SafetyCheckItemDO::getRecordId, recordId);
    }

	default int deleteByRecordIds(List<Long> recordIds) {
	    return deleteBatch(SafetyCheckItemDO::getRecordId, recordIds);
	}

}
