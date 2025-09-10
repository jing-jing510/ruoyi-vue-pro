package cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinventorylog.SparePartInventoryLogDO;
import cn.iocoder.yudao.module.coal.service.sparepartinventorylog.SparePartInventoryLogService;

@Tag(name = "管理后台 - 备件出入库记录")
@RestController
@RequestMapping("/coal/spare-part-inventory-log")
@Validated
public class SparePartInventoryLogController {

    @Resource
    private SparePartInventoryLogService sparePartInventoryLogService;

    @PostMapping("/create")
    @Operation(summary = "创建备件出入库记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-inventory-log:create')")
    public CommonResult<Long> createSparePartInventoryLog(@Valid @RequestBody SparePartInventoryLogSaveReqVO createReqVO) {
        return success(sparePartInventoryLogService.createSparePartInventoryLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新备件出入库记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-inventory-log:update')")
    public CommonResult<Boolean> updateSparePartInventoryLog(@Valid @RequestBody SparePartInventoryLogSaveReqVO updateReqVO) {
        sparePartInventoryLogService.updateSparePartInventoryLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除备件出入库记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-inventory-log:delete')")
    public CommonResult<Boolean> deleteSparePartInventoryLog(@RequestParam("id") Long id) {
        sparePartInventoryLogService.deleteSparePartInventoryLog(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除备件出入库记录")
                @PreAuthorize("@ss.hasPermission('coal:spare-part-inventory-log:delete')")
    public CommonResult<Boolean> deleteSparePartInventoryLogList(@RequestParam("ids") List<Long> ids) {
        sparePartInventoryLogService.deleteSparePartInventoryLogListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得备件出入库记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-inventory-log:query')")
    public CommonResult<SparePartInventoryLogRespVO> getSparePartInventoryLog(@RequestParam("id") Long id) {
        SparePartInventoryLogDO sparePartInventoryLog = sparePartInventoryLogService.getSparePartInventoryLog(id);
        return success(BeanUtils.toBean(sparePartInventoryLog, SparePartInventoryLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得备件出入库记录分页")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-inventory-log:query')")
    public CommonResult<PageResult<SparePartInventoryLogRespVO>> getSparePartInventoryLogPage(@Valid SparePartInventoryLogPageReqVO pageReqVO) {
        PageResult<SparePartInventoryLogDO> pageResult = sparePartInventoryLogService.getSparePartInventoryLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SparePartInventoryLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出备件出入库记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-inventory-log:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSparePartInventoryLogExcel(@Valid SparePartInventoryLogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SparePartInventoryLogDO> list = sparePartInventoryLogService.getSparePartInventoryLogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "备件出入库记录.xls", "数据", SparePartInventoryLogRespVO.class,
                        BeanUtils.toBean(list, SparePartInventoryLogRespVO.class));
    }

}