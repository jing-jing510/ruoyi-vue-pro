package cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionDailyReportPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionDailyReportRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionDailyReportSaveReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.HLproductiondailyreport.vo.HLProductionPlanProgressRespVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.HLproductiondailyreport.HLProductionDailyReportDO;
import cn.iocoder.yudao.module.coal.service.HLproductiondailyreport.HLProductionDailyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 红林生产日报")
@RestController
@RequestMapping("/coal/HL-production-daily-report")
@Validated
public class HLProductionDailyReportController {

    @Resource
    private HLProductionDailyReportService hLProductionDailyReportService;

    @PostMapping("/create")
    @Operation(summary = "创建红林生产日报")
    @PreAuthorize("@ss.hasPermission('coal:HL-production-daily-report:create')")
    public CommonResult<Long> createHLProductionDailyReport(@Valid @RequestBody HLProductionDailyReportSaveReqVO createReqVO) {
        return success(hLProductionDailyReportService.createHLProductionDailyReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新红林生产日报")
    @PreAuthorize("@ss.hasPermission('coal:HL-production-daily-report:update')")
    public CommonResult<Boolean> updateHLProductionDailyReport(@Valid @RequestBody HLProductionDailyReportSaveReqVO updateReqVO) {
        hLProductionDailyReportService.updateHLProductionDailyReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除红林生产日报")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:HL-production-daily-report:delete')")
    public CommonResult<Boolean> deleteHLProductionDailyReport(@RequestParam("id") Long id) {
        hLProductionDailyReportService.deleteHLProductionDailyReport(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除红林生产日报")
                @PreAuthorize("@ss.hasPermission('coal:HL-production-daily-report:delete')")
    public CommonResult<Boolean> deleteHLProductionDailyReportList(@RequestParam("ids") List<Long> ids) {
        hLProductionDailyReportService.deleteHLProductionDailyReportListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得红林生产日报")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:HL-production-daily-report:query')")
    public CommonResult<HLProductionDailyReportRespVO> getHLProductionDailyReport(@RequestParam("id") Long id) {
        HLProductionDailyReportDO hLProductionDailyReport = hLProductionDailyReportService.getHLProductionDailyReport(id);
        return success(BeanUtils.toBean(hLProductionDailyReport, HLProductionDailyReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得红林生产日报分页")
    @PreAuthorize("@ss.hasPermission('coal:HL-production-daily-report:query')")
    public CommonResult<PageResult<HLProductionDailyReportRespVO>> getHLProductionDailyReportPage(@Valid HLProductionDailyReportPageReqVO pageReqVO) {
        PageResult<HLProductionDailyReportDO> pageResult = hLProductionDailyReportService.getHLProductionDailyReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HLProductionDailyReportRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出红林生产日报 Excel")
    @PreAuthorize("@ss.hasPermission('coal:HL-production-daily-report:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportHLProductionDailyReportExcel(@Valid HLProductionDailyReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HLProductionDailyReportDO> list = hLProductionDailyReportService.getHLProductionDailyReportPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "红林生产日报.xls", "数据", HLProductionDailyReportRespVO.class,
                        BeanUtils.toBean(list, HLProductionDailyReportRespVO.class));
    }

    @TenantIgnore
    @PermitAll
    @GetMapping("/plan-progress")
    @Operation(summary = "获得红林生产计划进展统计信息，包含年月计划完成百分比")
    public CommonResult<List<HLProductionPlanProgressRespVO>> getHLPlanProgress() {
        HLProductionPlanProgressRespVO progress = hLProductionDailyReportService.getHLPlanProgress();
        return success(Arrays.asList(progress));
    }

}