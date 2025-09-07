package cn.iocoder.yudao.module.coal.service.equipmentinfo;

import cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo.EquipmentInfoListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo.EquipmentInfoSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.equipmentinfo.EquipmentInfoDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 设备档案 Service 接口
 *
 * @author 京京
 */
public interface EquipmentInfoService {

    /**
     * 创建设备档案
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEquipmentInfo(@Valid EquipmentInfoSaveReqVO createReqVO);

    /**
     * 更新设备档案
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipmentInfo(@Valid EquipmentInfoSaveReqVO updateReqVO);

    /**
     * 删除设备档案
     *
     * @param id 编号
     */
    void deleteEquipmentInfo(Long id);


    /**
     * 获得设备档案
     *
     * @param id 编号
     * @return 设备档案
     */
    EquipmentInfoDO getEquipmentInfo(Long id);

    /**
     * 获得设备档案列表
     *
     * @param listReqVO 查询条件
     * @return 设备档案列表
     */
    List<EquipmentInfoDO> getEquipmentInfoList(EquipmentInfoListReqVO listReqVO);

}