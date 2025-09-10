package cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderItemPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderItemRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderItemSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderItemDO;
import cn.iocoder.yudao.module.coal.service.maintenanceorder.MaintenanceOrderItemService;
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

@Tag(name = "管理后台 - 检修项目明细")
@RestController
@RequestMapping("/coal/maintenance-order-item")
@Validated
public class MaintenanceOrderItemController {

    @Resource
    private MaintenanceOrderItemService maintenanceOrderItemService;

    @PostMapping("/create")
    @Operation(summary = "创建检修项目明细")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order-item:create')")
    public CommonResult<Long> createMaintenanceOrderItem(@Valid @RequestBody MaintenanceOrderItemSaveReqVO createReqVO) {
        return success(maintenanceOrderItemService.createMaintenanceOrderItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检修项目明细")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order-item:update')")
    public CommonResult<Boolean> updateMaintenanceOrderItem(@Valid @RequestBody MaintenanceOrderItemSaveReqVO updateReqVO) {
        maintenanceOrderItemService.updateMaintenanceOrderItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检修项目明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order-item:delete')")
    public CommonResult<Boolean> deleteMaintenanceOrderItem(@RequestParam("id") Long id) {
        maintenanceOrderItemService.deleteMaintenanceOrderItem(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除检修项目明细")
                @PreAuthorize("@ss.hasPermission('coal:maintenance-order-item:delete')")
    public CommonResult<Boolean> deleteMaintenanceOrderItemList(@RequestParam("ids") List<Long> ids) {
        maintenanceOrderItemService.deleteMaintenanceOrderItemListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检修项目明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order-item:query')")
    public CommonResult<MaintenanceOrderItemRespVO> getMaintenanceOrderItem(@RequestParam("id") Long id) {
        MaintenanceOrderItemDO maintenanceOrderItem = maintenanceOrderItemService.getMaintenanceOrderItem(id);
        return success(BeanUtils.toBean(maintenanceOrderItem, MaintenanceOrderItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检修项目明细分页")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order-item:query')")
    public CommonResult<PageResult<MaintenanceOrderItemRespVO>> getMaintenanceOrderItemPage(@Valid MaintenanceOrderItemPageReqVO pageReqVO) {
        PageResult<MaintenanceOrderItemDO> pageResult = maintenanceOrderItemService.getMaintenanceOrderItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MaintenanceOrderItemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检修项目明细 Excel")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-order-item:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintenanceOrderItemExcel(@Valid MaintenanceOrderItemPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintenanceOrderItemDO> list = maintenanceOrderItemService.getMaintenanceOrderItemPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "检修项目明细.xls", "数据", MaintenanceOrderItemRespVO.class,
                        BeanUtils.toBean(list, MaintenanceOrderItemRespVO.class));
    }

}