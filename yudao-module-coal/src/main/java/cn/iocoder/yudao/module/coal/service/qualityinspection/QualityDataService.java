package cn.iocoder.yudao.module.coal.service.qualityinspection;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityDataDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 质量检测数据 Service 接口
 *
 * @author 京京
 */
public interface QualityDataService {

    /**
     * 创建质量检测数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQualityData(@Valid QualityDataSaveReqVO createReqVO);

    /**
     * 更新质量检测数据
     *
     * @param updateReqVO 更新信息
     */
    void updateQualityData(@Valid QualityDataSaveReqVO updateReqVO);

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

    /**
     * 获得质量检测数据分页
     *
     * @param pageReqVO 分页查询
     * @return 质量检测数据分页
     */
    PageResult<QualityDataDO> getQualityDataPage(QualityDataPageReqVO pageReqVO);

    /**
     * 获得质量检测数据统计
     *
     * @return 质量检测数据统计
     */
    QualityDataStatisticsRespVO getQualityDataStatistics();

}