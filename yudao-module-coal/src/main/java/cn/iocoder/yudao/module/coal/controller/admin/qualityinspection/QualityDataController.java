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
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityDataDO;
import cn.iocoder.yudao.module.coal.service.qualityinspection.QualityDataService;

@Tag(name = "管理后台 - 质量检测数据")
@RestController
@RequestMapping("/coal/quality-data")
@Validated
public class QualityDataController {

    @Resource
    private QualityDataService qualityDataService;

    @PostMapping("/create")
    @Operation(summary = "创建质量检测数据")
    @PreAuthorize("@ss.hasPermission('coal:quality-data:create')")
    public CommonResult<Long> createQualityData(@Valid @RequestBody QualityDataSaveReqVO createReqVO) {
        return success(qualityDataService.createQualityData(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新质量检测数据")
    @PreAuthorize("@ss.hasPermission('coal:quality-data:update')")
    public CommonResult<Boolean> updateQualityData(@Valid @RequestBody QualityDataSaveReqVO updateReqVO) {
        qualityDataService.updateQualityData(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除质量检测数据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:quality-data:delete')")
    public CommonResult<Boolean> deleteQualityData(@RequestParam("id") Long id) {
        qualityDataService.deleteQualityData(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除质量检测数据")
                @PreAuthorize("@ss.hasPermission('coal:quality-data:delete')")
    public CommonResult<Boolean> deleteQualityDataList(@RequestParam("ids") List<Long> ids) {
        qualityDataService.deleteQualityDataListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得质量检测数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:quality-data:query')")
    public CommonResult<QualityDataRespVO> getQualityData(@RequestParam("id") Long id) {
        QualityDataDO qualityData = qualityDataService.getQualityData(id);
        return success(BeanUtils.toBean(qualityData, QualityDataRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得质量检测数据分页")
    @PreAuthorize("@ss.hasPermission('coal:quality-data:query')")
    public CommonResult<PageResult<QualityDataRespVO>> getQualityDataPage(@Valid QualityDataPageReqVO pageReqVO) {
        PageResult<QualityDataDO> pageResult = qualityDataService.getQualityDataPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QualityDataRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出质量检测数据 Excel")
    @PreAuthorize("@ss.hasPermission('coal:quality-data:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQualityDataExcel(@Valid QualityDataPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QualityDataDO> list = qualityDataService.getQualityDataPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "质量检测数据.xls", "数据", QualityDataRespVO.class,
                        BeanUtils.toBean(list, QualityDataRespVO.class));
    }

    @GetMapping("/statistics")
    @Operation(summary = "获得质量检测数据统计信息，用于首页煤质管理业务流程组件")
    @PreAuthorize("@ss.hasPermission('coal:quality-data:query')")
    public CommonResult<QualityDataStatisticsRespVO> getQualityDataStatistics() {
        QualityDataStatisticsRespVO statistics = qualityDataService.getQualityDataStatistics();
        return success(statistics);
    }

}