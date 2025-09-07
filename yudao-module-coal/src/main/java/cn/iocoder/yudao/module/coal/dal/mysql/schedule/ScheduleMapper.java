package cn.iocoder.yudao.module.coal.dal.mysql.schedule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.SchedulePageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 排班管理 (主表) Mapper
 *
 * @author 京京
 */
@Mapper
public interface ScheduleMapper extends BaseMapperX<ScheduleDO> {

    default PageResult<ScheduleDO> selectPage(SchedulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ScheduleDO>()
                .betweenIfPresent(ScheduleDO::getScheduleDate, reqVO.getScheduleDate())
                .eqIfPresent(ScheduleDO::getShiftSystemId, reqVO.getShiftSystemId())
                .eqIfPresent(ScheduleDO::getScheduleStatus, reqVO.getScheduleStatus())
                .betweenIfPresent(ScheduleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ScheduleDO::getId));
    }

}