package cn.iocoder.yudao.module.coal.dal.mysql.sparepart;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartAlertPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepart.CoalSparePartAlertDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 备件预警记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CoalSparePartAlertMapper extends BaseMapperX<CoalSparePartAlertDO> {

    /**
     * 根据备件ID查询预警记录列表
     *
     * @param sparePartId 备件ID
     * @return 预警记录列表
     */
    default List<CoalSparePartAlertDO> selectListBySparePartId(Long sparePartId) {
        return selectList(CoalSparePartAlertDO::getSparePartId, sparePartId);
    }

    /**
     * 根据仓库ID查询预警记录列表
     *
     * @param warehouseId 仓库ID
     * @return 预警记录列表
     */
    default List<CoalSparePartAlertDO> selectListByWarehouseId(Long warehouseId) {
        return selectList(CoalSparePartAlertDO::getWarehouseId, warehouseId);
    }

    /**
     * 根据预警类型查询预警记录列表
     *
     * @param alertType 预警类型
     * @return 预警记录列表
     */
    default List<CoalSparePartAlertDO> selectListByAlertType(Integer alertType) {
        return selectList(CoalSparePartAlertDO::getAlertType, alertType);
    }

    /**
     * 根据预警状态查询预警记录列表
     *
     * @param alertStatus 预警状态
     * @return 预警记录列表
     */
    default List<CoalSparePartAlertDO> selectListByAlertStatus(Integer alertStatus) {
        return selectList(CoalSparePartAlertDO::getAlertStatus, alertStatus);
    }

    /**
     * 查询待处理的预警记录列表
     *
     * @return 待处理预警记录列表
     */
    default List<CoalSparePartAlertDO> selectPendingAlerts() {
        return selectList(CoalSparePartAlertDO::getAlertStatus, 1); // 1=待处理
    }

    /**
     * 根据备件ID删除预警记录
     *
     * @param sparePartId 备件ID
     * @return 删除数量
     */
    default int deleteBySparePartId(Long sparePartId) {
        return delete(CoalSparePartAlertDO::getSparePartId, sparePartId);
    }

    /**
     * 根据仓库ID删除预警记录
     *
     * @param warehouseId 仓库ID
     * @return 删除数量
     */
    default int deleteByWarehouseId(Long warehouseId) {
        return delete(CoalSparePartAlertDO::getWarehouseId, warehouseId);
    }

    /**
     * 分页查询备件预警记录
     *
     * @param reqVO 查询条件
     * @return 分页结果
     */
    default PageResult<CoalSparePartAlertDO> selectPage(CoalSparePartAlertPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CoalSparePartAlertDO>()
                .eqIfPresent(CoalSparePartAlertDO::getSparePartId, reqVO.getSparePartId())
                .eqIfPresent(CoalSparePartAlertDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(CoalSparePartAlertDO::getAlertType, reqVO.getAlertType())
                .eqIfPresent(CoalSparePartAlertDO::getAlertLevel, reqVO.getAlertLevel())
                .eqIfPresent(CoalSparePartAlertDO::getAlertStatus, reqVO.getAlertStatus())
                .betweenIfPresent(CoalSparePartAlertDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CoalSparePartAlertDO::getId));
    }

    /**
     * 根据备件ID、仓库ID和状态查询预警记录
     */
    default List<CoalSparePartAlertDO> selectListBySparePartIdAndWarehouseIdAndStatus(Long sparePartId, Long warehouseId, Integer alertStatus) {
        return selectList(new LambdaQueryWrapperX<CoalSparePartAlertDO>()
                .eq(CoalSparePartAlertDO::getSparePartId, sparePartId)
                .eq(CoalSparePartAlertDO::getWarehouseId, warehouseId)
                .eq(CoalSparePartAlertDO::getAlertStatus, alertStatus));
    }

    /**
     * 根据备件ID、预警类型和状态查询预警记录
     */
    default List<CoalSparePartAlertDO> selectListBySparePartIdAndAlertTypeAndStatus(Long sparePartId, Integer alertType, Integer alertStatus) {
        return selectList(new LambdaQueryWrapperX<CoalSparePartAlertDO>()
                .eq(CoalSparePartAlertDO::getSparePartId, sparePartId)
                .eq(CoalSparePartAlertDO::getAlertType, alertType)
                .eq(CoalSparePartAlertDO::getAlertStatus, alertStatus));
    }

}
