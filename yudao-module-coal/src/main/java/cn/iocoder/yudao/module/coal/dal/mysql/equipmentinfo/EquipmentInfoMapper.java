package cn.iocoder.yudao.module.coal.dal.mysql.equipmentinfo;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo.EquipmentInfoListReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.equipmentinfo.EquipmentInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备档案 Mapper
 *
 * @author 京京
 */
@Mapper
public interface EquipmentInfoMapper extends BaseMapperX<EquipmentInfoDO> {

    default List<EquipmentInfoDO> selectList(EquipmentInfoListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquipmentInfoDO>()
                .eqIfPresent(EquipmentInfoDO::getEquipmentCode, reqVO.getEquipmentCode())
                .likeIfPresent(EquipmentInfoDO::getEquipmentName, reqVO.getEquipmentName())
                .eqIfPresent(EquipmentInfoDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(EquipmentInfoDO::getParentEquipmentId, reqVO.getParentEquipmentId())
                .eqIfPresent(EquipmentInfoDO::getModel, reqVO.getModel())
                .eqIfPresent(EquipmentInfoDO::getManufacturer, reqVO.getManufacturer())
                .betweenIfPresent(EquipmentInfoDO::getManufactureDate, reqVO.getManufactureDate())
                .betweenIfPresent(EquipmentInfoDO::getInstallDate, reqVO.getInstallDate())
                .betweenIfPresent(EquipmentInfoDO::getCommissionDate, reqVO.getCommissionDate())
                .eqIfPresent(EquipmentInfoDO::getAssetNumber, reqVO.getAssetNumber())
                .eqIfPresent(EquipmentInfoDO::getRatedPower, reqVO.getRatedPower())
                .eqIfPresent(EquipmentInfoDO::getRatedCapacity, reqVO.getRatedCapacity())
                .eqIfPresent(EquipmentInfoDO::getWeight, reqVO.getWeight())
                .eqIfPresent(EquipmentInfoDO::getDimensions, reqVO.getDimensions())
                .eqIfPresent(EquipmentInfoDO::getVoltageLevel, reqVO.getVoltageLevel())
                .eqIfPresent(EquipmentInfoDO::getProtectionLevel, reqVO.getProtectionLevel())
                .eqIfPresent(EquipmentInfoDO::getWorkshop, reqVO.getWorkshop())
                .eqIfPresent(EquipmentInfoDO::getLocation, reqVO.getLocation())
                .eqIfPresent(EquipmentInfoDO::getCoordinateX, reqVO.getCoordinateX())
                .eqIfPresent(EquipmentInfoDO::getCoordinateY, reqVO.getCoordinateY())
                .eqIfPresent(EquipmentInfoDO::getEquipmentStatus, reqVO.getEquipmentStatus())
                .eqIfPresent(EquipmentInfoDO::getHealthLevel, reqVO.getHealthLevel())
                .eqIfPresent(EquipmentInfoDO::getImportanceLevel, reqVO.getImportanceLevel())
                .eqIfPresent(EquipmentInfoDO::getResponsiblePersonId, reqVO.getResponsiblePersonId())
                .eqIfPresent(EquipmentInfoDO::getMaintenancePersonId, reqVO.getMaintenancePersonId())
                .eqIfPresent(EquipmentInfoDO::getOperatorPersonId, reqVO.getOperatorPersonId())
                .eqIfPresent(EquipmentInfoDO::getQrCode, reqVO.getQrCode())
                .eqIfPresent(EquipmentInfoDO::getQrCodeUrl, reqVO.getQrCodeUrl())
                .eqIfPresent(EquipmentInfoDO::getManualUrl, reqVO.getManualUrl())
                .eqIfPresent(EquipmentInfoDO::getDrawingUrl, reqVO.getDrawingUrl())
                .eqIfPresent(EquipmentInfoDO::getSupplier, reqVO.getSupplier())
                .betweenIfPresent(EquipmentInfoDO::getPurchaseDate, reqVO.getPurchaseDate())
                .eqIfPresent(EquipmentInfoDO::getPurchasePrice, reqVO.getPurchasePrice())
                .eqIfPresent(EquipmentInfoDO::getWarrantyPeriod, reqVO.getWarrantyPeriod())
                .betweenIfPresent(EquipmentInfoDO::getWarrantyExpireDate, reqVO.getWarrantyExpireDate())
                .eqIfPresent(EquipmentInfoDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(EquipmentInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EquipmentInfoDO::getId));
    }

	default EquipmentInfoDO selectByParentEquipmentIdAndEquipmentName(Long parentEquipmentId, String equipmentName) {
	    return selectOne(EquipmentInfoDO::getParentEquipmentId, parentEquipmentId, EquipmentInfoDO::getEquipmentName, equipmentName);
	}

    default Long selectCountByParentEquipmentId(Long parentEquipmentId) {
        return selectCount(EquipmentInfoDO::getParentEquipmentId, parentEquipmentId);
    }

}