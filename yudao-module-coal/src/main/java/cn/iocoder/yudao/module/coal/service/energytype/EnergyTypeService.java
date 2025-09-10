package cn.iocoder.yudao.module.coal.service.energytype;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.energytype.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.energytype.EnergyTypeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 能源类型配置 Service 接口
 *
 * @author 京京
 */
public interface EnergyTypeService {

    /**
     * 创建能源类型配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEnergyType(@Valid EnergyTypeSaveReqVO createReqVO);

    /**
     * 更新能源类型配置
     *
     * @param updateReqVO 更新信息
     */
    void updateEnergyType(@Valid EnergyTypeSaveReqVO updateReqVO);

    /**
     * 删除能源类型配置
     *
     * @param id 编号
     */
    void deleteEnergyType(Long id);

    /**
    * 批量删除能源类型配置
    *
    * @param ids 编号
    */
    void deleteEnergyTypeListByIds(List<Long> ids);

    /**
     * 获得能源类型配置
     *
     * @param id 编号
     * @return 能源类型配置
     */
    EnergyTypeDO getEnergyType(Long id);

    /**
     * 获得能源类型配置分页
     *
     * @param pageReqVO 分页查询
     * @return 能源类型配置分页
     */
    PageResult<EnergyTypeDO> getEnergyTypePage(EnergyTypePageReqVO pageReqVO);

    /**
     * 获得能源类型配置简单列表，用于下拉选择
     *
     * @return 能源类型配置简单列表
     */
    List<EnergyTypeDO> getSimpleEnergyTypeList();

}