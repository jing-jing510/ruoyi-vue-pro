package cn.iocoder.yudao.module.opcua.dal.mysql.tag;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.TagConfigPageReqVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.tag.TagConfigDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 点位配置 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TagConfigMapper extends BaseMapperX<TagConfigDO> {

    default PageResult<TagConfigDO> selectPage(TagConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagConfigDO>()
                .eqIfPresent(TagConfigDO::getConfigId, reqVO.getConfigId())
                .likeIfPresent(TagConfigDO::getDeviceName, reqVO.getDeviceName())
                .likeIfPresent(TagConfigDO::getName, reqVO.getName())
                .eqIfPresent(TagConfigDO::getIsAlarm, reqVO.getIsAlarm())
                .eqIfPresent(TagConfigDO::getEnabled, reqVO.getEnabled())
                .betweenIfPresent(TagConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagConfigDO::getId));
    }

    default TagConfigDO selectByNodeId(String nodeId) {
        return selectOne(new LambdaQueryWrapperX<TagConfigDO>()
                .eq(TagConfigDO::getNodeId, nodeId));
    }

    default List<TagConfigDO> selectListByConfigId(Long configId) {
        return selectList(TagConfigDO::getConfigId, configId);
    }

    default List<TagConfigDO> selectEnabledAlarmTags() {
        return selectList(new LambdaQueryWrapperX<TagConfigDO>()
                .eq(TagConfigDO::getEnabled, true)
                .eq(TagConfigDO::getIsAlarm, true));
    }

}
