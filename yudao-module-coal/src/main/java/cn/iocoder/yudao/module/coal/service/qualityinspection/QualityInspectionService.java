package cn.iocoder.yudao.module.coal.service.qualityinspection;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityInspectionDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityDataDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 质量检测记录 Service 接口
 *
 * @author 京京
 */
public interface QualityInspectionService {

    /**
     * 创建质量检测记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQualityInspection(@Valid QualityInspectionSaveReqVO createReqVO);

    /**
     * 更新质量检测记录
     *
     * @param updateReqVO 更新信息
     */
    void updateQualityInspection(@Valid QualityInspectionSaveReqVO updateReqVO);

    /**
     * 删除质量检测记录
     *
     * @param id 编号
     */
    void deleteQualityInspection(Long id);

    /**
    * 批量删除质量检测记录
    *
    * @param ids 编号
    */
    void deleteQualityInspectionListByIds(List<Long> ids);

    /**
     * 获得质量检测记录
     *
     * @param id 编号
     * @return 质量检测记录
     */
    QualityInspectionDO getQualityInspection(Long id);

    /**
     * 获得质量检测记录分页
     *
     * @param pageReqVO 分页查询
     * @return 质量检测记录分页
     */
    PageResult<QualityInspectionDO> getQualityInspectionPage(QualityInspectionPageReqVO pageReqVO);

    // ==================== 子表（质量检测数据） ====================

    /**
     * 获得质量检测数据分页
     *
     * @param pageReqVO 分页查询
     * @param inspectionId 检测记录ID
     * @return 质量检测数据分页
     */
    PageResult<QualityDataDO> getQualityDataPage(PageParam pageReqVO, Long inspectionId);

    /**
     * 创建质量检测数据
     *
     * @param qualityData 创建信息
     * @return 编号
     */
    Long createQualityData(@Valid QualityDataDO qualityData);

    /**
     * 更新质量检测数据
     *
     * @param qualityData 更新信息
     */
    void updateQualityData(@Valid QualityDataDO qualityData);

    /**
     * 删除质量检测数据
     *
     * @param id 编号
     */
    void deleteQualityData(Long id);

    /**
    * 批量删除质量检测数据
    *
    * @param ids 编号
    */
    void deleteQualityDataListByIds(List<Long> ids);

	/**
	 * 获得质量检测数据
	 *
	 * @param id 编号
     * @return 质量检测数据
	 */
    QualityDataDO getQualityData(Long id);

}