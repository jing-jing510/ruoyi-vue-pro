package cn.iocoder.yudao.module.coal.service.sparepartusagerecord;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord.vo.SparePartUsageRecordPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord.vo.SparePartUsageRecordSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartusagerecord.SparePartUsageRecordDO;
import cn.iocoder.yudao.module.coal.dal.mysql.sparepartusagerecord.SparePartUsageRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SPARE_PART_USAGE_RECORD_NOT_EXISTS;

/**
 * 备件使用记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SparePartUsageRecordServiceImpl implements SparePartUsageRecordService {

    @Resource
    private SparePartUsageRecordMapper sparePartUsageRecordMapper;

    @Override
    public Long createSparePartUsageRecord(SparePartUsageRecordSaveReqVO createReqVO) {
        // 插入
        SparePartUsageRecordDO sparePartUsageRecord = BeanUtils.toBean(createReqVO, SparePartUsageRecordDO.class);
        sparePartUsageRecordMapper.insert(sparePartUsageRecord);

        // 返回
        return sparePartUsageRecord.getId();
    }

    @Override
    public void updateSparePartUsageRecord(SparePartUsageRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateSparePartUsageRecordExists(updateReqVO.getId());
        // 更新
        SparePartUsageRecordDO updateObj = BeanUtils.toBean(updateReqVO, SparePartUsageRecordDO.class);
        sparePartUsageRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteSparePartUsageRecord(Long id) {
        // 校验存在
        validateSparePartUsageRecordExists(id);
        // 删除
        sparePartUsageRecordMapper.deleteById(id);
    }

    @Override
        public void deleteSparePartUsageRecordListByIds(List<Long> ids) {
        // 删除
        sparePartUsageRecordMapper.deleteByIds(ids);
        }


    private void validateSparePartUsageRecordExists(Long id) {
        if (sparePartUsageRecordMapper.selectById(id) == null) {
            throw exception(SPARE_PART_USAGE_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public SparePartUsageRecordDO getSparePartUsageRecord(Long id) {
        return sparePartUsageRecordMapper.selectById(id);
    }

    @Override
    public PageResult<SparePartUsageRecordDO> getSparePartUsageRecordPage(SparePartUsageRecordPageReqVO pageReqVO) {
        return sparePartUsageRecordMapper.selectPage(pageReqVO);
    }

}