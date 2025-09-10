package cn.iocoder.yudao.module.coal.service.safetyaccident;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.safetyaccident.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetyaccident.SafetyAccidentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 安全事故记录 Service 接口
 *
 * @author 芋道源码
 */
public interface SafetyAccidentService {

    /**
     * 创建安全事故记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSafetyAccident(@Valid SafetyAccidentSaveReqVO createReqVO);

    /**
     * 更新安全事故记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSafetyAccident(@Valid SafetyAccidentSaveReqVO updateReqVO);

    /**
     * 删除安全事故记录
     *
     * @param id 编号
     */
    void deleteSafetyAccident(Long id);

    /**
    * 批量删除安全事故记录
    *
    * @param ids 编号
    */
    void deleteSafetyAccidentListByIds(List<Long> ids);

    /**
     * 获得安全事故记录
     *
     * @param id 编号
     * @return 安全事故记录
     */
    SafetyAccidentDO getSafetyAccident(Long id);

    /**
     * 获得安全事故记录分页
     *
     * @param pageReqVO 分页查询
     * @return 安全事故记录分页
     */
    PageResult<SafetyAccidentDO> getSafetyAccidentPage(SafetyAccidentPageReqVO pageReqVO);

    /**
     * 获得安全事故记录统计数据
     *
     * @return 统计数据
     */
    Map<String, Object> getSafetyAccidentStatistics();

}