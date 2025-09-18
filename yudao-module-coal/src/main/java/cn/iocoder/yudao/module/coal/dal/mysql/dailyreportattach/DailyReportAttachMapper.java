package cn.iocoder.yudao.module.coal.dal.mysql.dailyreportattach;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo.DailyReportAttachPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.dailyreportattach.DailyReportAttachDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生产日报附件上传 Mapper
 *
 * @author 京京
 */
@Mapper
public interface DailyReportAttachMapper extends BaseMapperX<DailyReportAttachDO> {

    default PageResult<DailyReportAttachDO> selectPage(DailyReportAttachPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DailyReportAttachDO>()
                .betweenIfPresent(DailyReportAttachDO::getReportDate, reqVO.getReportDate())
                .eqIfPresent(DailyReportAttachDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(DailyReportAttachDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DailyReportAttachDO::getId));
    }

}