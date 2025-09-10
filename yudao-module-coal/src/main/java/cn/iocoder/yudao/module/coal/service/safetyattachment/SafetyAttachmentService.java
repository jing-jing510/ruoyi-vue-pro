package cn.iocoder.yudao.module.coal.service.safetyattachment;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetyattachment.SafetyAttachmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 安全附件 Service 接口
 *
 * @author 芋道源码
 */
public interface SafetyAttachmentService {

    /**
     * 创建安全附件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSafetyAttachment(@Valid SafetyAttachmentSaveReqVO createReqVO);

    /**
     * 更新安全附件
     *
     * @param updateReqVO 更新信息
     */
    void updateSafetyAttachment(@Valid SafetyAttachmentSaveReqVO updateReqVO);

    /**
     * 删除安全附件
     *
     * @param id 编号
     */
    void deleteSafetyAttachment(Long id);

    /**
    * 批量删除安全附件
    *
    * @param ids 编号
    */
    void deleteSafetyAttachmentListByIds(List<Long> ids);

    /**
     * 获得安全附件
     *
     * @param id 编号
     * @return 安全附件
     */
    SafetyAttachmentDO getSafetyAttachment(Long id);

    /**
     * 获得安全附件分页
     *
     * @param pageReqVO 分页查询
     * @return 安全附件分页
     */
    PageResult<SafetyAttachmentDO> getSafetyAttachmentPage(SafetyAttachmentPageReqVO pageReqVO);

    /**
     * 获得安全附件统计数据
     *
     * @return 统计数据
     */
    Map<String, Object> getSafetyAttachmentStatistics();

}