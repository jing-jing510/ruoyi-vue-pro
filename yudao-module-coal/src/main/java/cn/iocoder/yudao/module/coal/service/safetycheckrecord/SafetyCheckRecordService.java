package cn.iocoder.yudao.module.coal.service.safetycheckrecord;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord.SafetyCheckRecordDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord.SafetyCheckItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 安全检查记录 Service 接口
 *
 * @author 京京
 */
public interface SafetyCheckRecordService {

    /**
     * 创建安全检查记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSafetyCheckRecord(@Valid SafetyCheckRecordSaveReqVO createReqVO);

    /**
     * 更新安全检查记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSafetyCheckRecord(@Valid SafetyCheckRecordSaveReqVO updateReqVO);

    /**
     * 删除安全检查记录
     *
     * @param id 编号
     */
    void deleteSafetyCheckRecord(Long id);

    /**
    * 批量删除安全检查记录
    *
    * @param ids 编号
    */
    void deleteSafetyCheckRecordListByIds(List<Long> ids);

    /**
     * 获得安全检查记录
     *
     * @param id 编号
     * @return 安全检查记录
     */
    SafetyCheckRecordDO getSafetyCheckRecord(Long id);

    /**
     * 获得安全检查记录分页
     *
     * @param pageReqVO 分页查询
     * @return 安全检查记录分页
     */
    PageResult<SafetyCheckRecordDO> getSafetyCheckRecordPage(SafetyCheckRecordPageReqVO pageReqVO);

    // ==================== 子表（安全检查项目） ====================

    /**
     * 获得安全检查项目分页
     *
     * @param pageReqVO 分页查询
     * @param recordId 检查记录ID
     * @return 安全检查项目分页
     */
    PageResult<SafetyCheckItemDO> getSafetyCheckItemPage(PageParam pageReqVO, Long recordId);

    /**
     * 创建安全检查项目
     *
     * @param safetyCheckItem 创建信息
     * @return 编号
     */
    Long createSafetyCheckItem(@Valid SafetyCheckItemDO safetyCheckItem);

    /**
     * 更新安全检查项目
     *
     * @param safetyCheckItem 更新信息
     */
    void updateSafetyCheckItem(@Valid SafetyCheckItemDO safetyCheckItem);

    /**
     * 删除安全检查项目
     *
     * @param id 编号
     */
    void deleteSafetyCheckItem(Long id);

    /**
    * 批量删除安全检查项目
    *
    * @param ids 编号
    */
    void deleteSafetyCheckItemListByIds(List<Long> ids);

	/**
	 * 获得安全检查项目
	 *
	 * @param id 编号
     * @return 安全检查项目
	 */
    SafetyCheckItemDO getSafetyCheckItem(Long id);

    /**
     * 根据记录ID获得安全检查项目列表
     *
     * @param recordId 检查记录ID
     * @return 安全检查项目列表
     */
    List<SafetyCheckItemDO> getSafetyCheckItemListByRecordId(Long recordId);

    /**
     * 获得安全检查记录统计数据
     *
     * @return 统计数据
     */
    Map<String, Object> getSafetyCheckRecordStatistics();

    /**
     * 获得安全检查项目统计数据
     *
     * @return 统计数据
     */
    Map<String, Object> getSafetyCheckItemStatistics();

}