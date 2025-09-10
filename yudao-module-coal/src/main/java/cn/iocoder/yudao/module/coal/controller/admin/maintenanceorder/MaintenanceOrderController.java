package cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderItemDO;
import cn.iocoder.yudao.module.coal.service.maintenanceorder.MaintenanceOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 检修单")
@RestController
@RequestMapping("/coal/maintenance-order")
@Validated
public class MaintenanceOrderController {

    @Resource
    private MaintenanceOrderService maintenanceOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建检修单")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:create')")
    public CommonResult<Long> createMaintenanceOrder(@Valid @RequestBody MaintenanceOrderSaveReqVO createReqVO) {
        return success(maintenanceOrderService.createMaintenanceOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检修单")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:update')")
    public CommonResult<Boolean> updateMaintenanceOrder(@Valid @RequestBody MaintenanceOrderSaveReqVO updateReqVO) {
        maintenanceOrderService.updateMaintenanceOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检修单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:delete')")
    public CommonResult<Boolean> deleteMaintenanceOrder(@RequestParam("id") Long id) {
        maintenanceOrderService.deleteMaintenanceOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除检修单")
                @PreAuthorize("@ss.hasPermission('coal:maintenance-order:delete')")
    public CommonResult<Boolean> deleteMaintenanceOrderList(@RequestParam("ids") List<Long> ids) {
        maintenanceOrderService.deleteMaintenanceOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检修单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:query')")
    public CommonResult<MaintenanceOrderRespVO> getMaintenanceOrder(@RequestParam("id") Long id) {
        MaintenanceOrderDO maintenanceOrder = maintenanceOrderService.getMaintenanceOrder(id);
        return success(BeanUtils.toBean(maintenanceOrder, MaintenanceOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检修单分页")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:query')")
    public CommonResult<PageResult<MaintenanceOrderRespVO>> getMaintenanceOrderPage(@Valid MaintenanceOrderPageReqVO pageReqVO) {
        PageResult<MaintenanceOrderDO> pageResult = maintenanceOrderService.getMaintenanceOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MaintenanceOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检修单 Excel")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintenanceOrderExcel(@Valid MaintenanceOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintenanceOrderDO> list = maintenanceOrderService.getMaintenanceOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "检修单.xls", "数据", MaintenanceOrderRespVO.class,
                        BeanUtils.toBean(list, MaintenanceOrderRespVO.class));
    }

    // ==================== 子表（检修项目明细） ====================

    @GetMapping("/maintenance-order-item/page")
    @Operation(summary = "获得检修项目明细分页")
    @Parameter(name = "orderId", description = "检修单ID")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:query')")
    public CommonResult<PageResult<MaintenanceOrderItemDO>> getMaintenanceOrderItemPage(PageParam pageReqVO,
                                                                                        @RequestParam("orderId") Long orderId) {
        return success(maintenanceOrderService.getMaintenanceOrderItemPage(pageReqVO, orderId));
    }

    @PostMapping("/maintenance-order-item/create")
    @Operation(summary = "创建检修项目明细")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:create')")
    public CommonResult<Long> createMaintenanceOrderItem(@Valid @RequestBody MaintenanceOrderItemDO maintenanceOrderItem) {
        return success(maintenanceOrderService.createMaintenanceOrderItem(maintenanceOrderItem));
    }

    @PutMapping("/maintenance-order-item/update")
    @Operation(summary = "更新检修项目明细")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:update')")
    public CommonResult<Boolean> updateMaintenanceOrderItem(@Valid @RequestBody MaintenanceOrderItemDO maintenanceOrderItem) {
        maintenanceOrderService.updateMaintenanceOrderItem(maintenanceOrderItem);
        return success(true);
    }

    @DeleteMapping("/maintenance-order-item/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除检修项目明细")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:delete')")
    public CommonResult<Boolean> deleteMaintenanceOrderItem(@RequestParam("id") Long id) {
        maintenanceOrderService.deleteMaintenanceOrderItem(id);
        return success(true);
    }

    @DeleteMapping("/maintenance-order-item/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除检修项目明细")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:delete')")
    public CommonResult<Boolean> deleteMaintenanceOrderItemList(@RequestParam("ids") List<Long> ids) {
        maintenanceOrderService.deleteMaintenanceOrderItemListByIds(ids);
        return success(true);
    }

	@GetMapping("/maintenance-order-item/get")
	@Operation(summary = "获得检修项目明细")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order:query')")
	public CommonResult<MaintenanceOrderItemDO> getMaintenanceOrderItem(@RequestParam("id") Long id) {
	    return success(maintenanceOrderService.getMaintenanceOrderItem(id));
	}

}