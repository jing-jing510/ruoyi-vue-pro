package cn.iocoder.yudao.module.opcua.convert.alarm;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo.AlarmEventRespVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.alarm.AlarmEventDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 报警事件 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface AlarmEventConvert {

    AlarmEventConvert INSTANCE = Mappers.getMapper(AlarmEventConvert.class);

    AlarmEventRespVO convert(AlarmEventDO bean);

    PageResult<AlarmEventRespVO> convertPage(PageResult<AlarmEventDO> page);

}
