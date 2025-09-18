package cn.iocoder.yudao.module.coal.service.dailyreportattach;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo.DailyReportAttachPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo.DailyReportAttachSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.dailyreportattach.DailyReportAttachDO;
import cn.iocoder.yudao.module.coal.dal.mysql.dailyreportattach.DailyReportAttachMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DAILY_REPORT_ATTACH_NOT_EXISTS;

/**
 * 生产日报附件上传 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class DailyReportAttachServiceImpl implements DailyReportAttachService {

    @Resource
    private DailyReportAttachMapper dailyReportAttachMapper;

    @Override
    public Long createDailyReportAttach(DailyReportAttachSaveReqVO createReqVO) {
        // 插入
        DailyReportAttachDO dailyReportAttach = BeanUtils.toBean(createReqVO, DailyReportAttachDO.class);
        dailyReportAttachMapper.insert(dailyReportAttach);

        // 返回
        return dailyReportAttach.getId();
    }

    @Override
    public void updateDailyReportAttach(DailyReportAttachSaveReqVO updateReqVO) {
        // 校验存在
        validateDailyReportAttachExists(updateReqVO.getId());
        // 更新
        DailyReportAttachDO updateObj = BeanUtils.toBean(updateReqVO, DailyReportAttachDO.class);
        dailyReportAttachMapper.updateById(updateObj);
    }

    @Override
    public void deleteDailyReportAttach(Long id) {
        // 校验存在
        validateDailyReportAttachExists(id);
        // 删除
        dailyReportAttachMapper.deleteById(id);
    }

    @Override
        public void deleteDailyReportAttachListByIds(List<Long> ids) {
        // 删除
        dailyReportAttachMapper.deleteByIds(ids);
        }


    private void validateDailyReportAttachExists(Long id) {
        if (dailyReportAttachMapper.selectById(id) == null) {
            throw exception(DAILY_REPORT_ATTACH_NOT_EXISTS);
        }
    }

    @Override
    public DailyReportAttachDO getDailyReportAttach(Long id) {
        return dailyReportAttachMapper.selectById(id);
    }

    @Override
    public PageResult<DailyReportAttachDO> getDailyReportAttachPage(DailyReportAttachPageReqVO pageReqVO) {
        return dailyReportAttachMapper.selectPage(pageReqVO);
    }

}