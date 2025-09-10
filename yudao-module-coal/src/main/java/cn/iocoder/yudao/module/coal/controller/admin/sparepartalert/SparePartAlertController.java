package cn.iocoder.yudao.module.coal.controller.admin.sparepartalert;

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

import cn.iocoder.yudao.module.coal.controller.admin.sparepartalert.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartalert.SparePartAlertDO;
import cn.iocoder.yudao.module.coal.service.sparepartalert.SparePartAlertService;

@Tag(name = "管理后台 - 备件预警记录")
@RestController
@RequestMapping("/coal/spare-part-alert")
@Validated
public class SparePartAlertController {

    @Resource
    private SparePartAlertService sparePartAlertService;

    @PostMapping("/create")
    @Operation(summary = "创建备件预警记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:create')")
    public CommonResult<Long> createSparePartAlert(@Valid @RequestBody SparePartAlertSaveReqVO createReqVO) {
        return success(sparePartAlertService.createSparePartAlert(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新备件预警记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:update')")
    public CommonResult<Boolean> updateSparePartAlert(@Valid @RequestBody SparePartAlertSaveReqVO updateReqVO) {
        sparePartAlertService.updateSparePartAlert(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除备件预警记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:delete')")
    public CommonResult<Boolean> deleteSparePartAlert(@RequestParam("id") Long id) {
        sparePartAlertService.deleteSparePartAlert(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除备件预警记录")
                @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:delete')")
    public CommonResult<Boolean> deleteSparePartAlertList(@RequestParam("ids") List<Long> ids) {
        sparePartAlertService.deleteSparePartAlertListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得备件预警记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:query')")
    public CommonResult<SparePartAlertRespVO> getSparePartAlert(@RequestParam("id") Long id) {
        SparePartAlertDO sparePartAlert = sparePartAlertService.getSparePartAlert(id);
        return success(BeanUtils.toBean(sparePartAlert, SparePartAlertRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得备件预警记录分页")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:query')")
    public CommonResult<PageResult<SparePartAlertRespVO>> getSparePartAlertPage(@Valid SparePartAlertPageReqVO pageReqVO) {
        PageResult<SparePartAlertDO> pageResult = sparePartAlertService.getSparePartAlertPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SparePartAlertRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出备件预警记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSparePartAlertExcel(@Valid SparePartAlertPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SparePartAlertDO> list = sparePartAlertService.getSparePartAlertPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "备件预警记录.xls", "数据", SparePartAlertRespVO.class,
                        BeanUtils.toBean(list, SparePartAlertRespVO.class));
    }

    @PostMapping("/send-notification/{id}")
    @Operation(summary = "发送预警通知")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-alert:send')")
    public CommonResult<Boolean> sendNotification(@PathVariable("id") Long id) {
        sparePartAlertService.sendNotification(id);
        return success(true);
    }

}