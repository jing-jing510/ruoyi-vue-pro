package cn.iocoder.yudao.module.coal.service.sparepartstock;

import java.math.BigDecimal;
import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartstock.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartstock.SparePartStockDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 备件库存记录 Service 接口
 *
 * @author 京京
 */
public interface SparePartStockService {

    /**
     * 创建备件库存记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSparePartStock(@Valid SparePartStockSaveReqVO createReqVO);

    /**
     * 更新备件库存记录
     *
     * @param updateReqVO 更新信息
     */
    void updateSparePartStock(@Valid SparePartStockSaveReqVO updateReqVO);

    /**
     * 删除备件库存记录
     *
     * @param id 编号
     */
    void deleteSparePartStock(Long id);

    /**
    * 批量删除备件库存记录
    *
    * @param ids 编号
    */
    void deleteSparePartStockListByIds(List<Long> ids);

    /**
     * 获得备件库存记录
     *
     * @param id 编号
     * @return 备件库存记录
     */
    SparePartStockDO getSparePartStock(Long id);

    /**
     * 获得备件库存记录分页
     *
     * @param pageReqVO 分页查询
     * @return 备件库存记录分页
     */
    PageResult<SparePartStockDO> getSparePartStockPage(SparePartStockPageReqVO pageReqVO);

    /**
     * 增加库存数量
     *
     * @param sparePartId 备件ID
     * @param quantity 增加数量
     */
    void increaseStock(Long sparePartId, BigDecimal quantity);

    /**
     * 减少库存数量
     *
     * @param sparePartId 备件ID
     * @param quantity 减少数量
     */
    void decreaseStock(Long sparePartId, BigDecimal quantity);

    /**
     * 获取当前库存数量
     *
     * @param sparePartId 备件ID
     * @return 当前库存数量
     */
    BigDecimal getCurrentStock(Long sparePartId);

}