package cn.iocoder.yudao.module.coal.controller.admin.qualityalert;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo.QualityAlertPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo.QualityAlertRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo.QualityAlertSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityalert.QualityAlertDO;
import cn.iocoder.yudao.module.coal.service.qualityalert.QualityAlertService;
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
import java.util.Map;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 质量预警记录")
@RestController
@RequestMapping("/coal/quality-alert")
@Validated
public class QualityAlertController {

    @Resource
    private QualityAlertService qualityAlertService;

    @PostMapping("/create")
    @Operation(summary = "创建质量预警记录")
    @PreAuthorize("@ss.hasPermission('coal:quality-alert:create')")
    public CommonResult<Long> createQualityAlert(@Valid @RequestBody QualityAlertSaveReqVO createReqVO) {
        return success(qualityAlertService.createQualityAlert(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新质量预警记录")
    @PreAuthorize("@ss.hasPermission('coal:quality-alert:update')")
    public CommonResult<Boolean> updateQualityAlert(@Valid @RequestBody QualityAlertSaveReqVO updateReqVO) {
        qualityAlertService.updateQualityAlert(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除质量预警记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:quality-alert:delete')")
    public CommonResult<Boolean> deleteQualityAlert(@RequestParam("id") Long id) {
        qualityAlertService.deleteQualityAlert(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除质量预警记录")
                @PreAuthorize("@ss.hasPermission('coal:quality-alert:delete')")
    public CommonResult<Boolean> deleteQualityAlertList(@RequestParam("ids") List<Long> ids) {
        qualityAlertService.deleteQualityAlertListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得质量预警记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:quality-alert:query')")
    public CommonResult<QualityAlertRespVO> getQualityAlert(@RequestParam("id") Long id) {
        QualityAlertDO qualityAlert = qualityAlertService.getQualityAlert(id);
        return success(BeanUtils.toBean(qualityAlert, QualityAlertRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得质量预警记录分页")
    @PreAuthorize("@ss.hasPermission('coal:quality-alert:query')")
    public CommonResult<PageResult<QualityAlertRespVO>> getQualityAlertPage(@Valid QualityAlertPageReqVO pageReqVO) {
        PageResult<QualityAlertDO> pageResult = qualityAlertService.getQualityAlertPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QualityAlertRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出质量预警记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:quality-alert:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQualityAlertExcel(@Valid QualityAlertPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QualityAlertDO> list = qualityAlertService.getQualityAlertPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "质量预警记录.xls", "数据", QualityAlertRespVO.class,
                        BeanUtils.toBean(list, QualityAlertRespVO.class));
    }

    @PostMapping("/send-notification/{id}")
    @Operation(summary = "发送质量预警站内信通知")
    @PreAuthorize("@ss.hasPermission('coal:quality-alert:update')")
    public CommonResult<Boolean> sendQualityAlertNotification(
            @Parameter(description = "预警记录ID", required = true) @PathVariable("id") Long alertId) {
        qualityAlertService.sendQualityAlertNotification(alertId, null);
        return success(true);
    }

    @GetMapping("/statistics")
    @Operation(summary = "获取质量预警统计数据")
    @PreAuthorize("@ss.hasPermission('coal:quality-alert:query')")
    public CommonResult<Map<String, Object>> getQualityAlertStatistics() {
        return success((Map<String, Object>) qualityAlertService.getQualityAlertStatistics());
    }

}