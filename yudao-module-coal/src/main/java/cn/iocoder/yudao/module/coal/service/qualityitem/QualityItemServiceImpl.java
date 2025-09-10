package cn.iocoder.yudao.module.coal.service.qualityitem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.qualityitem.vo.QualityItemPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.qualityitem.vo.QualityItemSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityitem.QualityItemDO;
import cn.iocoder.yudao.module.coal.dal.mysql.qualityitem.QualityItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.QUALITY_ITEM_NOT_EXISTS;

/**
 * 质量检测项目 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class QualityItemServiceImpl implements QualityItemService {

    @Resource
    private QualityItemMapper qualityItemMapper;

    @Override
    public Long createQualityItem(QualityItemSaveReqVO createReqVO) {
        // 插入
        QualityItemDO qualityItem = BeanUtils.toBean(createReqVO, QualityItemDO.class);
        qualityItemMapper.insert(qualityItem);

        // 返回
        return qualityItem.getId();
    }

    @Override
    public void updateQualityItem(QualityItemSaveReqVO updateReqVO) {
        // 校验存在
        validateQualityItemExists(updateReqVO.getId());
        // 更新
        QualityItemDO updateObj = BeanUtils.toBean(updateReqVO, QualityItemDO.class);
        qualityItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteQualityItem(Long id) {
        // 校验存在
        validateQualityItemExists(id);
        // 删除
        qualityItemMapper.deleteById(id);
    }

    @Override
        public void deleteQualityItemListByIds(List<Long> ids) {
        // 删除
        qualityItemMapper.deleteByIds(ids);
        }


    private void validateQualityItemExists(Long id) {
        if (qualityItemMapper.selectById(id) == null) {
            throw exception(QUALITY_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public QualityItemDO getQualityItem(Long id) {
        return qualityItemMapper.selectById(id);
    }

    @Override
    public PageResult<QualityItemDO> getQualityItemPage(QualityItemPageReqVO pageReqVO) {
        return qualityItemMapper.selectPage(pageReqVO);
    }

}