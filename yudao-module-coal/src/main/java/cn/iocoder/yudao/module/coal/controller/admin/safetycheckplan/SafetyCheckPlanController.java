package cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan;

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

import cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckplan.SafetyCheckPlanDO;
import cn.iocoder.yudao.module.coal.service.safetycheckplan.SafetyCheckPlanService;

@Tag(name = "管理后台 - 安全检查计划")
@RestController
@RequestMapping("/coal/safety-check-plan")
@Validated
public class SafetyCheckPlanController {

    @Resource
    private SafetyCheckPlanService safetyCheckPlanService;

    @PostMapping("/create")
    @Operation(summary = "创建安全检查计划")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-plan:create')")
    public CommonResult<Long> createSafetyCheckPlan(@Valid @RequestBody SafetyCheckPlanSaveReqVO createReqVO) {
        return success(safetyCheckPlanService.createSafetyCheckPlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新安全检查计划")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-plan:update')")
    public CommonResult<Boolean> updateSafetyCheckPlan(@Valid @RequestBody SafetyCheckPlanSaveReqVO updateReqVO) {
        safetyCheckPlanService.updateSafetyCheckPlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除安全检查计划")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:safety-check-plan:delete')")
    public CommonResult<Boolean> deleteSafetyCheckPlan(@RequestParam("id") Long id) {
        safetyCheckPlanService.deleteSafetyCheckPlan(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除安全检查计划")
                @PreAuthorize("@ss.hasPermission('coal:safety-check-plan:delete')")
    public CommonResult<Boolean> deleteSafetyCheckPlanList(@RequestParam("ids") List<Long> ids) {
        safetyCheckPlanService.deleteSafetyCheckPlanListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得安全检查计划")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-plan:query')")
    public CommonResult<SafetyCheckPlanRespVO> getSafetyCheckPlan(@RequestParam("id") Long id) {
        SafetyCheckPlanDO safetyCheckPlan = safetyCheckPlanService.getSafetyCheckPlan(id);
        return success(BeanUtils.toBean(safetyCheckPlan, SafetyCheckPlanRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得安全检查计划分页")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-plan:query')")
    public CommonResult<PageResult<SafetyCheckPlanRespVO>> getSafetyCheckPlanPage(@Valid SafetyCheckPlanPageReqVO pageReqVO) {
        PageResult<SafetyCheckPlanDO> pageResult = safetyCheckPlanService.getSafetyCheckPlanPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SafetyCheckPlanRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出安全检查计划 Excel")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-plan:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSafetyCheckPlanExcel(@Valid SafetyCheckPlanPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SafetyCheckPlanDO> list = safetyCheckPlanService.getSafetyCheckPlanPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "安全检查计划.xls", "数据", SafetyCheckPlanRespVO.class,
                        BeanUtils.toBean(list, SafetyCheckPlanRespVO.class));
    }

}