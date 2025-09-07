package cn.iocoder.yudao.module.coal.controller.admin.sparepart;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartAlertPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartAlertRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartAlertSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepart.CoalSparePartAlertDO;
import cn.iocoder.yudao.module.coal.service.sparepart.CoalSparePartAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 备件预警记录")
@RestController
@RequestMapping("/coal/spare-part-alert")
@Validated
public class CoalSparePartAlertController {

    @Resource
    private CoalSparePartAlertService sparePartAlertService;

    @PostMapping("/create")
    @Operation(summary = "创建备件预警记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:create')")
    public CommonResult<Long> createSparePartAlert(@Valid @RequestBody CoalSparePartAlertSaveReqVO createReqVO) {
        return success(sparePartAlertService.createAlert(
                createReqVO.getSparePartId(), createReqVO.getWarehouseId(),
                createReqVO.getAlertType(), createReqVO.getCurrentStock(),
                createReqVO.getThresholdValue(), createReqVO.getAlertLevel(),
                createReqVO.getAlertMessage()));
    }

    @PutMapping("/handle")
    @Operation(summary = "处理预警")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:handle')")
    public CommonResult<Boolean> handleAlert(@RequestParam("alertId") Long alertId,
                                           @RequestParam("handlerId") Long handlerId,
                                           @RequestParam("handleRemark") String handleRemark) {
        sparePartAlertService.handleAlert(alertId, handlerId, handleRemark);
        return success(true);
    }

    @PutMapping("/ignore")
    @Operation(summary = "忽略预警")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:handle')")
    public CommonResult<Boolean> ignoreAlert(@RequestParam("alertId") Long alertId,
                                           @RequestParam("handlerId") Long handlerId,
                                           @RequestParam("handleRemark") String handleRemark) {
        sparePartAlertService.ignoreAlert(alertId, handlerId, handleRemark);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除备件预警记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:delete')")
    public CommonResult<Boolean> deleteSparePartAlert(@RequestParam("id") Long id) {
        sparePartAlertService.deleteAlert(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得备件预警记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:query')")
    public CommonResult<CoalSparePartAlertRespVO> getSparePartAlert(@RequestParam("id") Long id) {
        CoalSparePartAlertDO sparePartAlert = sparePartAlertService.getAlert(id);
        return success(BeanUtils.toBean(sparePartAlert, CoalSparePartAlertRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得备件预警记录分页")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:query')")
    public CommonResult<PageResult<CoalSparePartAlertRespVO>> getSparePartAlertPage(@Valid CoalSparePartAlertPageReqVO pageReqVO) {
        PageResult<CoalSparePartAlertDO> pageResult = sparePartAlertService.getAlertPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CoalSparePartAlertRespVO.class));
    }

    @GetMapping("/pending")
    @Operation(summary = "获得待处理预警列表")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:query')")
    public CommonResult<List<CoalSparePartAlertRespVO>> getPendingAlerts() {
        List<CoalSparePartAlertDO> list = sparePartAlertService.getPendingAlerts();
        return success(BeanUtils.toBean(list, CoalSparePartAlertRespVO.class));
    }

    @PostMapping("/manual-check-stock")
    @Operation(summary = "手动触发库存预警检查")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:create')")
    public CommonResult<Boolean> manualCheckStockAlerts() {
        sparePartAlertService.checkStockAlerts();
        return success(true);
    }

    @PostMapping("/manual-check-replacement")
    @Operation(summary = "手动触发更换预警检查")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:create')")
    public CommonResult<Boolean> manualCheckReplacementAlerts() {
        sparePartAlertService.checkReplacementAlerts();
        return success(true);
    }

}
