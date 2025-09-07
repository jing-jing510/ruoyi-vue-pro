package cn.iocoder.yudao.module.coal.service.shiftsystem;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.shiftsystem.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.shiftsystem.ShiftSystemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 班制与班次设置 (树表) Service 接口
 *
 * @author 芋道源码
 */
public interface ShiftSystemService {

    /**
     * 创建班制与班次设置 (树表)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createShiftSystem(@Valid ShiftSystemSaveReqVO createReqVO);

    /**
     * 更新班制与班次设置 (树表)
     *
     * @param updateReqVO 更新信息
     */
    void updateShiftSystem(@Valid ShiftSystemSaveReqVO updateReqVO);

    /**
     * 删除班制与班次设置 (树表)
     *
     * @param id 编号
     */
    void deleteShiftSystem(Long id);


    /**
     * 获得班制与班次设置 (树表)
     *
     * @param id 编号
     * @return 班制与班次设置 (树表)
     */
    ShiftSystemDO getShiftSystem(Long id);

    /**
     * 获得班制与班次设置 (树表)列表
     *
     * @param listReqVO 查询条件
     * @return 班制与班次设置 (树表)列表
     */
    List<ShiftSystemDO> getShiftSystemList(ShiftSystemListReqVO listReqVO);

}