package cn.iocoder.yudao.module.coal.dal.mysql.shiftsystem;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.shiftsystem.ShiftSystemDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.shiftsystem.vo.*;

/**
 * 班制与班次设置 (树表) Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ShiftSystemMapper extends BaseMapperX<ShiftSystemDO> {

    default List<ShiftSystemDO> selectList(ShiftSystemListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ShiftSystemDO>()
                .eqIfPresent(ShiftSystemDO::getParentId, reqVO.getParentId())
                .likeIfPresent(ShiftSystemDO::getName, reqVO.getName())
                .eqIfPresent(ShiftSystemDO::getCode, reqVO.getCode())
                .eqIfPresent(ShiftSystemDO::getShiftType, reqVO.getShiftType())
                .betweenIfPresent(ShiftSystemDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ShiftSystemDO::getId));
    }

	default ShiftSystemDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(ShiftSystemDO::getParentId, parentId, ShiftSystemDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(ShiftSystemDO::getParentId, parentId);
    }

}