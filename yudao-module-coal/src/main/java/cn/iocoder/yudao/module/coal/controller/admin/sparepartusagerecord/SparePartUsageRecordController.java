package cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord;

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

import cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartusagerecord.SparePartUsageRecordDO;
import cn.iocoder.yudao.module.coal.service.sparepartusagerecord.SparePartUsageRecordService;

@Tag(name = "管理后台 - 备件使用记录")
@RestController
@RequestMapping("/coal/spare-part-usage-record")
@Validated
public class SparePartUsageRecordController {

    @Resource
    private SparePartUsageRecordService sparePartUsageRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建备件使用记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-usage-record:create')")
    public CommonResult<Long> createSparePartUsageRecord(@Valid @RequestBody SparePartUsageRecordSaveReqVO createReqVO) {
        return success(sparePartUsageRecordService.createSparePartUsageRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新备件使用记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-usage-record:update')")
    public CommonResult<Boolean> updateSparePartUsageRecord(@Valid @RequestBody SparePartUsageRecordSaveReqVO updateReqVO) {
        sparePartUsageRecordService.updateSparePartUsageRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除备件使用记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-usage-record:delete')")
    public CommonResult<Boolean> deleteSparePartUsageRecord(@RequestParam("id") Long id) {
        sparePartUsageRecordService.deleteSparePartUsageRecord(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除备件使用记录")
                @PreAuthorize("@ss.hasPermission('coal:spare-part-usage-record:delete')")
    public CommonResult<Boolean> deleteSparePartUsageRecordList(@RequestParam("ids") List<Long> ids) {
        sparePartUsageRecordService.deleteSparePartUsageRecordListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得备件使用记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-usage-record:query')")
    public CommonResult<SparePartUsageRecordRespVO> getSparePartUsageRecord(@RequestParam("id") Long id) {
        SparePartUsageRecordDO sparePartUsageRecord = sparePartUsageRecordService.getSparePartUsageRecord(id);
        return success(BeanUtils.toBean(sparePartUsageRecord, SparePartUsageRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得备件使用记录分页")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-usage-record:query')")
    public CommonResult<PageResult<SparePartUsageRecordRespVO>> getSparePartUsageRecordPage(@Valid SparePartUsageRecordPageReqVO pageReqVO) {
        PageResult<SparePartUsageRecordDO> pageResult = sparePartUsageRecordService.getSparePartUsageRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SparePartUsageRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出备件使用记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-usage-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSparePartUsageRecordExcel(@Valid SparePartUsageRecordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SparePartUsageRecordDO> list = sparePartUsageRecordService.getSparePartUsageRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "备件使用记录.xls", "数据", SparePartUsageRecordRespVO.class,
                        BeanUtils.toBean(list, SparePartUsageRecordRespVO.class));
    }

}