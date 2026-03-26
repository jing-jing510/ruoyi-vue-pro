package cn.iocoder.yudao.module.opcua.dal.mysql.config;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigPageReqVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.config.OpcuaConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * OPC UA 配置 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OpcuaConfigMapper extends BaseMapperX<OpcuaConfigDO> {

    default PageResult<OpcuaConfigDO> selectPage(OpcuaConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpcuaConfigDO>()
                .likeIfPresent(OpcuaConfigDO::getName, reqVO.getName())
                .eqIfPresent(OpcuaConfigDO::getEnabled, reqVO.getEnabled())
                .betweenIfPresent(OpcuaConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OpcuaConfigDO::getId));
    }

    default OpcuaConfigDO selectByName(String name) {
        return selectOne(OpcuaConfigDO::getName, name);
    }

}
