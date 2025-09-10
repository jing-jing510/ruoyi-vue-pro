package cn.iocoder.yudao.module.coal.controller.admin.qualityinspection;

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

import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityInspectionDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityDataDO;
import cn.iocoder.yudao.module.coal.service.qualityinspection.QualityInspectionService;

@Tag(name = "管理后台 - 质量检测记录")
@RestController
@RequestMapping("/coal/quality-inspection")
@Validated
public class QualityInspectionController {

    @Resource
    private QualityInspectionService qualityInspectionService;

    @PostMapping("/create")
    @Operation(summary = "创建质量检测记录")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:create')")
    public CommonResult<Long> createQualityInspection(@Valid @RequestBody QualityInspectionSaveReqVO createReqVO) {
        return success(qualityInspectionService.createQualityInspection(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新质量检测记录")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:update')")
    public CommonResult<Boolean> updateQualityInspection(@Valid @RequestBody QualityInspectionSaveReqVO updateReqVO) {
        qualityInspectionService.updateQualityInspection(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除质量检测记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:delete')")
    public CommonResult<Boolean> deleteQualityInspection(@RequestParam("id") Long id) {
        qualityInspectionService.deleteQualityInspection(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除质量检测记录")
                @PreAuthorize("@ss.hasPermission('coal:quality-inspection:delete')")
    public CommonResult<Boolean> deleteQualityInspectionList(@RequestParam("ids") List<Long> ids) {
        qualityInspectionService.deleteQualityInspectionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得质量检测记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:query')")
    public CommonResult<QualityInspectionRespVO> getQualityInspection(@RequestParam("id") Long id) {
        QualityInspectionDO qualityInspection = qualityInspectionService.getQualityInspection(id);
        return success(BeanUtils.toBean(qualityInspection, QualityInspectionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得质量检测记录分页")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:query')")
    public CommonResult<PageResult<QualityInspectionRespVO>> getQualityInspectionPage(@Valid QualityInspectionPageReqVO pageReqVO) {
        PageResult<QualityInspectionDO> pageResult = qualityInspectionService.getQualityInspectionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QualityInspectionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出质量检测记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQualityInspectionExcel(@Valid QualityInspectionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QualityInspectionDO> list = qualityInspectionService.getQualityInspectionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "质量检测记录.xls", "数据", QualityInspectionRespVO.class,
                        BeanUtils.toBean(list, QualityInspectionRespVO.class));
    }

    // ==================== 子表（质量检测数据） ====================

    @GetMapping("/quality-data/page")
    @Operation(summary = "获得质量检测数据分页")
    @Parameter(name = "inspectionId", description = "检测记录ID")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:query')")
    public CommonResult<PageResult<QualityDataDO>> getQualityDataPage(PageParam pageReqVO,
                                                                                        @RequestParam("inspectionId") Long inspectionId) {
        return success(qualityInspectionService.getQualityDataPage(pageReqVO, inspectionId));
    }

    @PostMapping("/quality-data/create")
    @Operation(summary = "创建质量检测数据")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:create')")
    public CommonResult<Long> createQualityData(@Valid @RequestBody QualityDataDO qualityData) {
        return success(qualityInspectionService.createQualityData(qualityData));
    }

    @PutMapping("/quality-data/update")
    @Operation(summary = "更新质量检测数据")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:update')")
    public CommonResult<Boolean> updateQualityData(@Valid @RequestBody QualityDataDO qualityData) {
        qualityInspectionService.updateQualityData(qualityData);
        return success(true);
    }

    @DeleteMapping("/quality-data/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除质量检测数据")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:delete')")
    public CommonResult<Boolean> deleteQualityData(@RequestParam("id") Long id) {
        qualityInspectionService.deleteQualityData(id);
        return success(true);
    }

    @DeleteMapping("/quality-data/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除质量检测数据")
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:delete')")
    public CommonResult<Boolean> deleteQualityDataList(@RequestParam("ids") List<Long> ids) {
        qualityInspectionService.deleteQualityDataListByIds(ids);
        return success(true);
    }

	@GetMapping("/quality-data/get")
	@Operation(summary = "获得质量检测数据")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:quality-inspection:query')")
	public CommonResult<QualityDataDO> getQualityData(@RequestParam("id") Long id) {
	    return success(qualityInspectionService.getQualityData(id));
	}

}