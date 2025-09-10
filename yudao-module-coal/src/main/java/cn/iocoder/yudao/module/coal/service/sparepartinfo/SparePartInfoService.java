package cn.iocoder.yudao.module.coal.service.sparepartinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartInfoPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartInfoSaveReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartStockStatisticsRespVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinfo.SparePartInfoDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 备件基础信息 Service 接口
 *
 * @author 芋道源码
 */
public interface SparePartInfoService {

    /**
     * 创建备件基础信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSparePartInfo(@Valid SparePartInfoSaveReqVO createReqVO);

    /**
     * 更新备件基础信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSparePartInfo(@Valid SparePartInfoSaveReqVO updateReqVO);

    /**
     * 删除备件基础信息
     *
     * @param id 编号
     */
    void deleteSparePartInfo(Long id);

    /**
    * 批量删除备件基础信息
    *
    * @param ids 编号
    */
    void deleteSparePartInfoListByIds(List<Long> ids);

    /**
     * 获得备件基础信息
     *
     * @param id 编号
     * @return 备件基础信息
     */
    SparePartInfoDO getSparePartInfo(Long id);

    /**
     * 获得备件基础信息分页
     *
     * @param pageReqVO 分页查询
     * @return 备件基础信息分页
     */
    PageResult<SparePartInfoDO> getSparePartInfoPage(SparePartInfoPageReqVO pageReqVO);

    /**
     * 获得备件基础信息简单列表，用于下拉选择
     *
     * @return 备件基础信息简单列表
     */
    List<SparePartInfoDO> getSimpleSparePartList();

    /**
     * 获得备件库存统计信息
     *
     * @return 库存统计信息
     */
    SparePartStockStatisticsRespVO getStockStatistics();

    /**
     * 获得备件使用频率分析
     *
     * @return 使用频率分析信息
     */
    SparePartStockStatisticsRespVO.UsageFrequencyStatistics getUsageFrequencyAnalysis();

}