package cn.iocoder.yudao.module.coal.service.sparepartstock;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartstock.vo.SparePartStockPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartstock.vo.SparePartStockSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartstock.SparePartStockDO;
import cn.iocoder.yudao.module.coal.dal.mysql.sparepartstock.SparePartStockMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SPARE_PART_STOCK_NOT_EXISTS;

/**
 * 备件库存记录 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class SparePartStockServiceImpl implements SparePartStockService {

    @Resource
    private SparePartStockMapper sparePartStockMapper;

    @Override
    public Long createSparePartStock(SparePartStockSaveReqVO createReqVO) {
        // 插入
        SparePartStockDO sparePartStock = BeanUtils.toBean(createReqVO, SparePartStockDO.class);
        sparePartStockMapper.insert(sparePartStock);

        // 返回
        return sparePartStock.getId();
    }

    @Override
    public void updateSparePartStock(SparePartStockSaveReqVO updateReqVO) {
        // 校验存在
        validateSparePartStockExists(updateReqVO.getId());
        // 更新
        SparePartStockDO updateObj = BeanUtils.toBean(updateReqVO, SparePartStockDO.class);
        sparePartStockMapper.updateById(updateObj);
    }

    @Override
    public void deleteSparePartStock(Long id) {
        // 校验存在
        validateSparePartStockExists(id);
        // 删除
        sparePartStockMapper.deleteById(id);
    }

    @Override
        public void deleteSparePartStockListByIds(List<Long> ids) {
        // 删除
        sparePartStockMapper.deleteByIds(ids);
        }


    private void validateSparePartStockExists(Long id) {
        if (sparePartStockMapper.selectById(id) == null) {
            throw exception(SPARE_PART_STOCK_NOT_EXISTS);
        }
    }

    @Override
    public SparePartStockDO getSparePartStock(Long id) {
        return sparePartStockMapper.selectById(id);
    }

    @Override
    public PageResult<SparePartStockDO> getSparePartStockPage(SparePartStockPageReqVO pageReqVO) {
        return sparePartStockMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseStock(Long sparePartId, BigDecimal quantity) {
        // 查找或创建库存记录
        SparePartStockDO stock = getOrCreateStock(sparePartId);
        
        // 增加库存数量
        stock.setQuantity(stock.getQuantity().add(quantity));
        
        // 更新最后入库时间
        stock.setLastInDate(LocalDateTime.now());
        
        // 更新库存记录
        sparePartStockMapper.updateById(stock);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(Long sparePartId, BigDecimal quantity) {
        // 查找库存记录
        SparePartStockDO stock = getOrCreateStock(sparePartId);
        
        // 检查库存是否充足
        if (stock.getQuantity().compareTo(quantity) < 0) {
            throw exception(SPARE_PART_STOCK_NOT_EXISTS, "库存不足，当前库存：" + stock.getQuantity() + "，需要数量：" + quantity);
        }
        
        // 减少库存数量
        stock.setQuantity(stock.getQuantity().subtract(quantity));
        
        // 更新最后出库时间
        stock.setLastOutDate(LocalDateTime.now());
        
        // 更新库存记录
        sparePartStockMapper.updateById(stock);
    }

    @Override
    public BigDecimal getCurrentStock(Long sparePartId) {
        SparePartStockDO stock = getOrCreateStock(sparePartId);
        return stock.getQuantity();
    }

    /**
     * 获取或创建库存记录
     */
    private SparePartStockDO getOrCreateStock(Long sparePartId) {
        // 查找现有库存记录
        SparePartStockDO stock = sparePartStockMapper.selectBySparePartId(sparePartId);
        
        if (stock == null) {
            // 创建新的库存记录
            stock = new SparePartStockDO();
            stock.setSparePartId(sparePartId);
            stock.setQuantity(BigDecimal.ZERO);
            stock.setStockType(1); // 默认库存类型
            stock.setWarehouseLocation("默认仓库"); // 设置默认仓库位置
            stock.setUnitCost(BigDecimal.ZERO); // 设置默认单位成本
            stock.setTotalCost(BigDecimal.ZERO); // 设置默认总成本
            sparePartStockMapper.insert(stock);
        }
        
        return stock;
    }

}