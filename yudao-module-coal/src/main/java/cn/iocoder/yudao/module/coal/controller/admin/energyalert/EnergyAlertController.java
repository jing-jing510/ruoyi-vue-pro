package cn.iocoder.yudao.module.coal.controller.admin.energyalert;

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

import cn.iocoder.yudao.module.coal.controller.admin.energyalert.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.energyalert.EnergyAlertDO;
import cn.iocoder.yudao.module.coal.service.energyalert.EnergyAlertService;

@Tag(name = "管理后台 - 能源预警记录")
@RestController
@RequestMapping("/coal/energy-alert")
@Validated
public class EnergyAlertController {

    @Resource
    private EnergyAlertService energyAlertService;

    @PostMapping("/create")
    @Operation(summary = "创建能源预警记录")
    @PreAuthorize("@ss.hasPermission('coal:energy-alert:create')")
    public CommonResult<Long> createEnergyAlert(@Valid @RequestBody EnergyAlertSaveReqVO createReqVO) {
        return success(energyAlertService.createEnergyAlert(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新能源预警记录")
    @PreAuthorize("@ss.hasPermission('coal:energy-alert:update')")
    public CommonResult<Boolean> updateEnergyAlert(@Valid @RequestBody EnergyAlertSaveReqVO updateReqVO) {
        energyAlertService.updateEnergyAlert(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除能源预警记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:energy-alert:delete')")
    public CommonResult<Boolean> deleteEnergyAlert(@RequestParam("id") Long id) {
        energyAlertService.deleteEnergyAlert(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除能源预警记录")
                @PreAuthorize("@ss.hasPermission('coal:energy-alert:delete')")
    public CommonResult<Boolean> deleteEnergyAlertList(@RequestParam("ids") List<Long> ids) {
        energyAlertService.deleteEnergyAlertListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得能源预警记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:energy-alert:query')")
    public CommonResult<EnergyAlertRespVO> getEnergyAlert(@RequestParam("id") Long id) {
        EnergyAlertDO energyAlert = energyAlertService.getEnergyAlert(id);
        return success(BeanUtils.toBean(energyAlert, EnergyAlertRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得能源预警记录分页")
    @PreAuthorize("@ss.hasPermission('coal:energy-alert:query')")
    public CommonResult<PageResult<EnergyAlertRespVO>> getEnergyAlertPage(@Valid EnergyAlertPageReqVO pageReqVO) {
        PageResult<EnergyAlertDO> pageResult = energyAlertService.getEnergyAlertPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EnergyAlertRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出能源预警记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:energy-alert:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEnergyAlertExcel(@Valid EnergyAlertPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EnergyAlertDO> list = energyAlertService.getEnergyAlertPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "能源预警记录.xls", "数据", EnergyAlertRespVO.class,
                        BeanUtils.toBean(list, EnergyAlertRespVO.class));
    }

}