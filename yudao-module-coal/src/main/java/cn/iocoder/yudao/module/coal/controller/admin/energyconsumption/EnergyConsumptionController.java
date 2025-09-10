package cn.iocoder.yudao.module.coal.controller.admin.energyconsumption;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo.EnergyConsumptionPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo.EnergyConsumptionRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo.EnergyConsumptionSaveReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo.EnergyConsumptionStatisticsRespVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.energyconsumption.EnergyConsumptionDO;
import cn.iocoder.yudao.module.coal.service.energyconsumption.EnergyConsumptionService;
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

@Tag(name = "管理后台 - 能源消耗记录")
@RestController
@RequestMapping("/coal/energy-consumption")
@Validated
public class EnergyConsumptionController {

    @Resource
    private EnergyConsumptionService energyConsumptionService;

    @PostMapping("/create")
    @Operation(summary = "创建能源消耗记录")
    @PreAuthorize("@ss.hasPermission('coal:energy-consumption:create')")
    public CommonResult<Long> createEnergyConsumption(@Valid @RequestBody EnergyConsumptionSaveReqVO createReqVO) {
        return success(energyConsumptionService.createEnergyConsumption(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新能源消耗记录")
    @PreAuthorize("@ss.hasPermission('coal:energy-consumption:update')")
    public CommonResult<Boolean> updateEnergyConsumption(@Valid @RequestBody EnergyConsumptionSaveReqVO updateReqVO) {
        energyConsumptionService.updateEnergyConsumption(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除能源消耗记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:energy-consumption:delete')")
    public CommonResult<Boolean> deleteEnergyConsumption(@RequestParam("id") Long id) {
        energyConsumptionService.deleteEnergyConsumption(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除能源消耗记录")
                @PreAuthorize("@ss.hasPermission('coal:energy-consumption:delete')")
    public CommonResult<Boolean> deleteEnergyConsumptionList(@RequestParam("ids") List<Long> ids) {
        energyConsumptionService.deleteEnergyConsumptionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得能源消耗记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:energy-consumption:query')")
    public CommonResult<EnergyConsumptionRespVO> getEnergyConsumption(@RequestParam("id") Long id) {
        EnergyConsumptionDO energyConsumption = energyConsumptionService.getEnergyConsumption(id);
        return success(BeanUtils.toBean(energyConsumption, EnergyConsumptionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得能源消耗记录分页")
    @PreAuthorize("@ss.hasPermission('coal:energy-consumption:query')")
    public CommonResult<PageResult<EnergyConsumptionRespVO>> getEnergyConsumptionPage(@Valid EnergyConsumptionPageReqVO pageReqVO) {
        PageResult<EnergyConsumptionDO> pageResult = energyConsumptionService.getEnergyConsumptionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EnergyConsumptionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出能源消耗记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:energy-consumption:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEnergyConsumptionExcel(@Valid EnergyConsumptionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EnergyConsumptionDO> list = energyConsumptionService.getEnergyConsumptionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "能源消耗记录.xls", "数据", EnergyConsumptionRespVO.class,
                        BeanUtils.toBean(list, EnergyConsumptionRespVO.class));
    }

    @GetMapping("/statistics")
    @Operation(summary = "获得能源消耗记录统计信息")
    @PreAuthorize("@ss.hasPermission('coal:energy-consumption:query')")
    public CommonResult<EnergyConsumptionStatisticsRespVO> getEnergyConsumptionStatistics() {
        EnergyConsumptionStatisticsRespVO statistics = energyConsumptionService.getEnergyConsumptionStatistics();
        return success(statistics);
    }

}