package cn.iocoder.yudao.module.opcua.service.tag;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.TagConfigPageReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.TagConfigSaveReqVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.tag.TagConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 点位配置 Service 接口
 *
 * @author 芋道源码
 */
public interface TagConfigService {

    /**
     * 创建点位配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTag(@Valid TagConfigSaveReqVO createReqVO);

    /**
     * 更新点位配置
     *
     * @param updateReqVO 更新信息
     */
    void updateTag(@Valid TagConfigSaveReqVO updateReqVO);

    /**
     * 删除点位配置
     *
     * @param id 编号
     */
    void deleteTag(Long id);

    /**
     * 获得点位配置
     *
     * @param id 编号
     * @return 点位配置
     */
    TagConfigDO getTag(Long id);

    /**
     * 获得点位配置分页
     *
     * @param pageReqVO 分页查询
     * @return 点位配置分页
     */
    PageResult<TagConfigDO> getTagPage(TagConfigPageReqVO pageReqVO);

    /**
     * 批量导入点位配置
     *
     * @param list 点位配置列表
     */
    void batchImportTags(List<TagConfigSaveReqVO> list);

    /**
     * 获取启用的报警点位列表
     *
     * @return 报警点位列表
     */
    List<TagConfigDO> getEnabledAlarmTags();

    /**
     * 更新点位最后采集值
     *
     * @param id 点位ID
     * @param lastValue 最后采集值
     */
    void updateLastValue(Long id, String lastValue);

}
