package cn.iocoder.yudao.module.opcua.convert.config;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigRespVO;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigSaveReqVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.config.OpcuaConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * OPC UA 配置 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface OpcuaConfigConvert {

    OpcuaConfigConvert INSTANCE = Mappers.getMapper(OpcuaConfigConvert.class);

    OpcuaConfigDO convert(OpcuaConfigSaveReqVO bean);

    OpcuaConfigRespVO convert(OpcuaConfigDO bean);

    PageResult<OpcuaConfigRespVO> convertPage(PageResult<OpcuaConfigDO> page);

}
