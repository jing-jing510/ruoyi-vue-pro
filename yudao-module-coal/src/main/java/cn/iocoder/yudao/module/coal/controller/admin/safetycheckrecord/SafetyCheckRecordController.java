package cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord;

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

import cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord.SafetyCheckRecordDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord.SafetyCheckItemDO;
import cn.iocoder.yudao.module.coal.service.safetycheckrecord.SafetyCheckRecordService;

@Tag(name = "管理后台 - 安全检查记录")
@RestController
@RequestMapping("/coal/safety-check-record")
@Validated
public class SafetyCheckRecordController {

    @Resource
    private SafetyCheckRecordService safetyCheckRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建安全检查记录")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:create')")
    public CommonResult<Long> createSafetyCheckRecord(@Valid @RequestBody SafetyCheckRecordSaveReqVO createReqVO) {
        return success(safetyCheckRecordService.createSafetyCheckRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新安全检查记录")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:update')")
    public CommonResult<Boolean> updateSafetyCheckRecord(@Valid @RequestBody SafetyCheckRecordSaveReqVO updateReqVO) {
        safetyCheckRecordService.updateSafetyCheckRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除安全检查记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:delete')")
    public CommonResult<Boolean> deleteSafetyCheckRecord(@RequestParam("id") Long id) {
        safetyCheckRecordService.deleteSafetyCheckRecord(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除安全检查记录")
                @PreAuthorize("@ss.hasPermission('coal:safety-check-record:delete')")
    public CommonResult<Boolean> deleteSafetyCheckRecordList(@RequestParam("ids") List<Long> ids) {
        safetyCheckRecordService.deleteSafetyCheckRecordListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得安全检查记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:query')")
    public CommonResult<SafetyCheckRecordRespVO> getSafetyCheckRecord(@RequestParam("id") Long id) {
        SafetyCheckRecordDO safetyCheckRecord = safetyCheckRecordService.getSafetyCheckRecord(id);
        return success(BeanUtils.toBean(safetyCheckRecord, SafetyCheckRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得安全检查记录分页")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:query')")
    public CommonResult<PageResult<SafetyCheckRecordRespVO>> getSafetyCheckRecordPage(@Valid SafetyCheckRecordPageReqVO pageReqVO) {
        PageResult<SafetyCheckRecordDO> pageResult = safetyCheckRecordService.getSafetyCheckRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SafetyCheckRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出安全检查记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSafetyCheckRecordExcel(@Valid SafetyCheckRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SafetyCheckRecordDO> list = safetyCheckRecordService.getSafetyCheckRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "安全检查记录.xls", "数据", SafetyCheckRecordRespVO.class,
                        BeanUtils.toBean(list, SafetyCheckRecordRespVO.class));
    }

    // ==================== 子表（安全检查项目） ====================

    @GetMapping("/safety-check-item/page")
    @Operation(summary = "获得安全检查项目分页")
    @Parameter(name = "recordId", description = "检查记录ID")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:query')")
    public CommonResult<PageResult<SafetyCheckItemDO>> getSafetyCheckItemPage(PageParam pageReqVO,
                                                                                        @RequestParam("recordId") Long recordId) {
        return success(safetyCheckRecordService.getSafetyCheckItemPage(pageReqVO, recordId));
    }

    @PostMapping("/safety-check-item/create")
    @Operation(summary = "创建安全检查项目")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:create')")
    public CommonResult<Long> createSafetyCheckItem(@Valid @RequestBody SafetyCheckItemDO safetyCheckItem) {
        return success(safetyCheckRecordService.createSafetyCheckItem(safetyCheckItem));
    }

    @PutMapping("/safety-check-item/update")
    @Operation(summary = "更新安全检查项目")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:update')")
    public CommonResult<Boolean> updateSafetyCheckItem(@Valid @RequestBody SafetyCheckItemDO safetyCheckItem) {
        safetyCheckRecordService.updateSafetyCheckItem(safetyCheckItem);
        return success(true);
    }

    @DeleteMapping("/safety-check-item/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除安全检查项目")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:delete')")
    public CommonResult<Boolean> deleteSafetyCheckItem(@RequestParam("id") Long id) {
        safetyCheckRecordService.deleteSafetyCheckItem(id);
        return success(true);
    }

    @DeleteMapping("/safety-check-item/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除安全检查项目")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:delete')")
    public CommonResult<Boolean> deleteSafetyCheckItemList(@RequestParam("ids") List<Long> ids) {
        safetyCheckRecordService.deleteSafetyCheckItemListByIds(ids);
        return success(true);
    }

	@GetMapping("/safety-check-item/get")
	@Operation(summary = "获得安全检查项目")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:query')")
	public CommonResult<SafetyCheckItemDO> getSafetyCheckItem(@RequestParam("id") Long id) {
	    return success(safetyCheckRecordService.getSafetyCheckItem(id));
	}

    @GetMapping("/safety-check-item/list-by-record-id")
    @Operation(summary = "根据记录ID获得安全检查项目列表")
    @Parameter(name = "recordId", description = "检查记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:query')")
    public CommonResult<List<SafetyCheckItemDO>> getSafetyCheckItemListByRecordId(@RequestParam("recordId") Long recordId) {
        return success(safetyCheckRecordService.getSafetyCheckItemListByRecordId(recordId));
    }

    @GetMapping("/statistics")
    @Operation(summary = "获得安全检查记录统计数据")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:query')")
    public CommonResult<Map<String, Object>> getSafetyCheckRecordStatistics() {
        return success(safetyCheckRecordService.getSafetyCheckRecordStatistics());
    }

    @GetMapping("/safety-check-item/statistics")
    @Operation(summary = "获得安全检查项目统计数据")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-record:query')")
    public CommonResult<Map<String, Object>> getSafetyCheckItemStatistics() {
        return success(safetyCheckRecordService.getSafetyCheckItemStatistics());
    }

}