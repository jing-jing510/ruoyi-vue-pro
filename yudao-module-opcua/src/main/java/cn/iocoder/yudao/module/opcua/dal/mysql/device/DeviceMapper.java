package cn.iocoder.yudao.module.opcua.dal.mysql.device;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.opcua.controller.admin.device.vo.DevicePageReqVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.device.DeviceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DeviceMapper extends BaseMapperX<DeviceDO> {

    default PageResult<DeviceDO> selectPage(DevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceDO>()
                .likeIfPresent(DeviceDO::getCode, reqVO.getCode())
                .likeIfPresent(DeviceDO::getName, reqVO.getName())
                .eqIfPresent(DeviceDO::getType, reqVO.getType())
                .eqIfPresent(DeviceDO::getConfigId, reqVO.getConfigId())
                .eqIfPresent(DeviceDO::getEnabled, reqVO.getEnabled())
                .betweenIfPresent(DeviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeviceDO::getId));
    }

    default DeviceDO selectByCode(String code) {
        return selectOne(DeviceDO::getCode, code);
    }

}
