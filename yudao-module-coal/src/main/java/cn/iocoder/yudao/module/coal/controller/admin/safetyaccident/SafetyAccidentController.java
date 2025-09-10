package cn.iocoder.yudao.module.coal.controller.admin.safetyaccident;

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

import cn.iocoder.yudao.module.coal.controller.admin.safetyaccident.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetyaccident.SafetyAccidentDO;
import cn.iocoder.yudao.module.coal.service.safetyaccident.SafetyAccidentService;

@Tag(name = "管理后台 - 安全事故记录")
@RestController
@RequestMapping("/coal/safety-accident")
@Validated
public class SafetyAccidentController {

    @Resource
    private SafetyAccidentService safetyAccidentService;

    @PostMapping("/create")
    @Operation(summary = "创建安全事故记录")
    @PreAuthorize("@ss.hasPermission('coal:safety-accident:create')")
    public CommonResult<Long> createSafetyAccident(@Valid @RequestBody SafetyAccidentSaveReqVO createReqVO) {
        return success(safetyAccidentService.createSafetyAccident(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新安全事故记录")
    @PreAuthorize("@ss.hasPermission('coal:safety-accident:update')")
    public CommonResult<Boolean> updateSafetyAccident(@Valid @RequestBody SafetyAccidentSaveReqVO updateReqVO) {
        safetyAccidentService.updateSafetyAccident(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除安全事故记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:safety-accident:delete')")
    public CommonResult<Boolean> deleteSafetyAccident(@RequestParam("id") Long id) {
        safetyAccidentService.deleteSafetyAccident(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除安全事故记录")
                @PreAuthorize("@ss.hasPermission('coal:safety-accident:delete')")
    public CommonResult<Boolean> deleteSafetyAccidentList(@RequestParam("ids") List<Long> ids) {
        safetyAccidentService.deleteSafetyAccidentListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得安全事故记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:safety-accident:query')")
    public CommonResult<SafetyAccidentRespVO> getSafetyAccident(@RequestParam("id") Long id) {
        SafetyAccidentDO safetyAccident = safetyAccidentService.getSafetyAccident(id);
        return success(BeanUtils.toBean(safetyAccident, SafetyAccidentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得安全事故记录分页")
    @PreAuthorize("@ss.hasPermission('coal:safety-accident:query')")
    public CommonResult<PageResult<SafetyAccidentRespVO>> getSafetyAccidentPage(@Valid SafetyAccidentPageReqVO pageReqVO) {
        PageResult<SafetyAccidentDO> pageResult = safetyAccidentService.getSafetyAccidentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SafetyAccidentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出安全事故记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:safety-accident:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSafetyAccidentExcel(@Valid SafetyAccidentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SafetyAccidentDO> list = safetyAccidentService.getSafetyAccidentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "安全事故记录.xls", "数据", SafetyAccidentRespVO.class,
                        BeanUtils.toBean(list, SafetyAccidentRespVO.class));
    }

    @GetMapping("/statistics")
    @Operation(summary = "获得安全事故记录统计数据")
    @PreAuthorize("@ss.hasPermission('coal:safety-accident:query')")
    public CommonResult<Map<String, Object>> getSafetyAccidentStatistics() {
        return success(safetyAccidentService.getSafetyAccidentStatistics());
    }

}