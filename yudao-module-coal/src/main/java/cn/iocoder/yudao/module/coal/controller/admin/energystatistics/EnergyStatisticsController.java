package cn.iocoder.yudao.module.coal.controller.admin.energystatistics;

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

import cn.iocoder.yudao.module.coal.controller.admin.energystatistics.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.energystatistics.EnergyStatisticsDO;
import cn.iocoder.yudao.module.coal.service.energystatistics.EnergyStatisticsService;

@Tag(name = "管理后台 - 能源消耗统计")
@RestController
@RequestMapping("/coal/energy-statistics")
@Validated
public class EnergyStatisticsController {

    @Resource
    private EnergyStatisticsService energyStatisticsService;

    @PostMapping("/create")
    @Operation(summary = "创建能源消耗统计")
    @PreAuthorize("@ss.hasPermission('coal:energy-statistics:create')")
    public CommonResult<Long> createEnergyStatistics(@Valid @RequestBody EnergyStatisticsSaveReqVO createReqVO) {
        return success(energyStatisticsService.createEnergyStatistics(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新能源消耗统计")
    @PreAuthorize("@ss.hasPermission('coal:energy-statistics:update')")
    public CommonResult<Boolean> updateEnergyStatistics(@Valid @RequestBody EnergyStatisticsSaveReqVO updateReqVO) {
        energyStatisticsService.updateEnergyStatistics(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除能源消耗统计")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:energy-statistics:delete')")
    public CommonResult<Boolean> deleteEnergyStatistics(@RequestParam("id") Long id) {
        energyStatisticsService.deleteEnergyStatistics(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除能源消耗统计")
                @PreAuthorize("@ss.hasPermission('coal:energy-statistics:delete')")
    public CommonResult<Boolean> deleteEnergyStatisticsList(@RequestParam("ids") List<Long> ids) {
        energyStatisticsService.deleteEnergyStatisticsListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得能源消耗统计")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:energy-statistics:query')")
    public CommonResult<EnergyStatisticsRespVO> getEnergyStatistics(@RequestParam("id") Long id) {
        EnergyStatisticsDO energyStatistics = energyStatisticsService.getEnergyStatistics(id);
        return success(BeanUtils.toBean(energyStatistics, EnergyStatisticsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得能源消耗统计分页")
    @PreAuthorize("@ss.hasPermission('coal:energy-statistics:query')")
    public CommonResult<PageResult<EnergyStatisticsRespVO>> getEnergyStatisticsPage(@Valid EnergyStatisticsPageReqVO pageReqVO) {
        PageResult<EnergyStatisticsDO> pageResult = energyStatisticsService.getEnergyStatisticsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EnergyStatisticsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出能源消耗统计 Excel")
    @PreAuthorize("@ss.hasPermission('coal:energy-statistics:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEnergyStatisticsExcel(@Valid EnergyStatisticsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EnergyStatisticsDO> list = energyStatisticsService.getEnergyStatisticsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "能源消耗统计.xls", "数据", EnergyStatisticsRespVO.class,
                        BeanUtils.toBean(list, EnergyStatisticsRespVO.class));
    }

    @GetMapping("/calculate-comparison-with-plan")
    @Operation(summary = "计算与计划对比百分比")
    @PreAuthorize("@ss.hasPermission('coal:energy-statistics:query')")
    public CommonResult<Double> calculateComparisonWithPlan(
            @RequestParam("energyTypeId") Long energyTypeId,
            @RequestParam("statisticsDate") String statisticsDate,
            @RequestParam("statisticsType") Integer statisticsType,
            @RequestParam("actualValue") Double actualValue) {
        Double result = energyStatisticsService.calculateComparisonWithPlan(energyTypeId, statisticsDate, statisticsType, actualValue);
        return success(result);
    }

    @GetMapping("/calculate-comparison-with-last-period")
    @Operation(summary = "计算与上期对比百分比")
    @PreAuthorize("@ss.hasPermission('coal:energy-statistics:query')")
    public CommonResult<Double> calculateComparisonWithLastPeriod(
            @RequestParam("energyTypeId") Long energyTypeId,
            @RequestParam("statisticsDate") String statisticsDate,
            @RequestParam("statisticsType") Integer statisticsType,
            @RequestParam("currentValue") Double currentValue) {
        Double result = energyStatisticsService.calculateComparisonWithLastPeriod(energyTypeId, statisticsDate, statisticsType, currentValue);
        return success(result);
    }

}