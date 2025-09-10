package cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan;

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

import cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceplan.MaintenancePlanDO;
import cn.iocoder.yudao.module.coal.service.maintenanceplan.MaintenancePlanService;

@Tag(name = "管理后台 - 检修计划")
@RestController
@RequestMapping("/coal/maintenance-plan")
@Validated
public class MaintenancePlanController {

    @Resource
    private MaintenancePlanService maintenancePlanService;

    @PostMapping("/create")
    @Operation(summary = "创建检修计划")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-plan:create')")
    public CommonResult<Long> createMaintenancePlan(@Valid @RequestBody MaintenancePlanSaveReqVO createReqVO) {
        return success(maintenancePlanService.createMaintenancePlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检修计划")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-plan:update')")
    public CommonResult<Boolean> updateMaintenancePlan(@Valid @RequestBody MaintenancePlanSaveReqVO updateReqVO) {
        maintenancePlanService.updateMaintenancePlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检修计划")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:maintenance-plan:delete')")
    public CommonResult<Boolean> deleteMaintenancePlan(@RequestParam("id") Long id) {
        maintenancePlanService.deleteMaintenancePlan(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除检修计划")
                @PreAuthorize("@ss.hasPermission('coal:maintenance-plan:delete')")
    public CommonResult<Boolean> deleteMaintenancePlanList(@RequestParam("ids") List<Long> ids) {
        maintenancePlanService.deleteMaintenancePlanListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检修计划")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-plan:query')")
    public CommonResult<MaintenancePlanRespVO> getMaintenancePlan(@RequestParam("id") Long id) {
        MaintenancePlanDO maintenancePlan = maintenancePlanService.getMaintenancePlan(id);
        return success(BeanUtils.toBean(maintenancePlan, MaintenancePlanRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检修计划分页")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-plan:query')")
    public CommonResult<PageResult<MaintenancePlanRespVO>> getMaintenancePlanPage(@Valid MaintenancePlanPageReqVO pageReqVO) {
        PageResult<MaintenancePlanDO> pageResult = maintenancePlanService.getMaintenancePlanPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MaintenancePlanRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检修计划 Excel")
    @PreAuthorize("@ss.hasPermission('coal:maintenance-plan:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaintenancePlanExcel(@Valid MaintenancePlanPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MaintenancePlanDO> list = maintenancePlanService.getMaintenancePlanPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "检修计划.xls", "数据", MaintenancePlanRespVO.class,
                        BeanUtils.toBean(list, MaintenancePlanRespVO.class));
    }

}