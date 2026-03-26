package cn.iocoder.yudao.module.opcua.service.tag;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.TagConfigPageReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.TagConfigSaveReqVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.tag.TagConfigDO;
import cn.iocoder.yudao.module.opcua.dal.mysql.tag.TagConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.opcua.enums.ErrorCodeConstants.*;

/**
 * 点位配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TagConfigServiceImpl implements TagConfigService {

    @Resource
    private TagConfigMapper tagConfigMapper;

    @Override
    public Long createTag(TagConfigSaveReqVO createReqVO) {
        // 校验 NodeId 唯一
        validateNodeIdUnique(null, createReqVO.getNodeId());

        // 插入
        TagConfigDO tag = BeanUtils.toBean(createReqVO, TagConfigDO.class);
        tagConfigMapper.insert(tag);
        return tag.getId();
    }

    @Override
    public void updateTag(TagConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateTagExists(updateReqVO.getId());
        // 校验 NodeId 唯一
        validateNodeIdUnique(updateReqVO.getId(), updateReqVO.getNodeId());

        // 更新
        TagConfigDO updateObj = BeanUtils.toBean(updateReqVO, TagConfigDO.class);
        tagConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteTag(Long id) {
        // 校验存在
        validateTagExists(id);
        // 删除
        tagConfigMapper.deleteById(id);
    }

    @Override
    public TagConfigDO getTag(Long id) {
        return tagConfigMapper.selectById(id);
    }

    @Override
    public PageResult<TagConfigDO> getTagPage(TagConfigPageReqVO pageReqVO) {
        return tagConfigMapper.selectPage(pageReqVO);
    }

    @Override
    public void batchImportTags(List<TagConfigSaveReqVO> list) {
        for (TagConfigSaveReqVO reqVO : list) {
            // 检查是否已存在
            TagConfigDO existingTag = tagConfigMapper.selectByNodeId(reqVO.getNodeId());
            if (existingTag == null) {
                createTag(reqVO);
            }
        }
    }

    @Override
    public List<TagConfigDO> getEnabledAlarmTags() {
        return tagConfigMapper.selectEnabledAlarmTags();
    }

    @Override
    public void updateLastValue(Long id, String lastValue) {
        TagConfigDO updateObj = new TagConfigDO();
        updateObj.setId(id);
        updateObj.setLastValue(lastValue);
        tagConfigMapper.updateById(updateObj);
    }

    private void validateTagExists(Long id) {
        if (tagConfigMapper.selectById(id) == null) {
            throw exception(OPCUA_TAG_NOT_EXISTS);
        }
    }

    private void validateNodeIdUnique(Long id, String nodeId) {
        TagConfigDO tag = tagConfigMapper.selectByNodeId(nodeId);
        if (tag == null) {
            return;
        }
        if (id == null || !id.equals(tag.getId())) {
            throw exception(OPCUA_TAG_NODE_ID_DUPLICATE);
        }
    }

}
