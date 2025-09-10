package cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartInfoPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartInfoRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartInfoSaveReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.SparePartStockStatisticsRespVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinfo.SparePartInfoDO;
import cn.iocoder.yudao.module.coal.service.sparepartinfo.SparePartInfoService;
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

@Tag(name = "管理后台 - 备件基础信息")
@RestController
@RequestMapping("/coal/spare-part-info")
@Validated
public class SparePartInfoController {

    @Resource
    private SparePartInfoService sparePartInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建备件基础信息")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:create')")
    public CommonResult<Long> createSparePartInfo(@Valid @RequestBody SparePartInfoSaveReqVO createReqVO) {
        return success(sparePartInfoService.createSparePartInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新备件基础信息")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:update')")
    public CommonResult<Boolean> updateSparePartInfo(@Valid @RequestBody SparePartInfoSaveReqVO updateReqVO) {
        sparePartInfoService.updateSparePartInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除备件基础信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:delete')")
    public CommonResult<Boolean> deleteSparePartInfo(@RequestParam("id") Long id) {
        sparePartInfoService.deleteSparePartInfo(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除备件基础信息")
                @PreAuthorize("@ss.hasPermission('coal:spare-part-info:delete')")
    public CommonResult<Boolean> deleteSparePartInfoList(@RequestParam("ids") List<Long> ids) {
        sparePartInfoService.deleteSparePartInfoListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得备件基础信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:query')")
    public CommonResult<SparePartInfoRespVO> getSparePartInfo(@RequestParam("id") Long id) {
        SparePartInfoDO sparePartInfo = sparePartInfoService.getSparePartInfo(id);
        return success(BeanUtils.toBean(sparePartInfo, SparePartInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得备件基础信息分页")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:query')")
    public CommonResult<PageResult<SparePartInfoRespVO>> getSparePartInfoPage(@Valid SparePartInfoPageReqVO pageReqVO) {
        PageResult<SparePartInfoDO> pageResult = sparePartInfoService.getSparePartInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SparePartInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出备件基础信息 Excel")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSparePartInfoExcel(@Valid SparePartInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SparePartInfoDO> list = sparePartInfoService.getSparePartInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "备件基础信息.xls", "数据", SparePartInfoRespVO.class,
                        BeanUtils.toBean(list, SparePartInfoRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得备件基础信息简单列表，用于下拉选择")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:query')")
    public CommonResult<List<SparePartInfoRespVO>> getSimpleSparePartList() {
        List<SparePartInfoDO> list = sparePartInfoService.getSimpleSparePartList();
        return success(BeanUtils.toBean(list, SparePartInfoRespVO.class));
    }

    @GetMapping("/stock-statistics")
    @Operation(summary = "获得备件库存统计信息，用于echart")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:query')")
    public CommonResult<SparePartStockStatisticsRespVO> getStockStatistics() {
        SparePartStockStatisticsRespVO statistics = sparePartInfoService.getStockStatistics();
        return success(statistics);
    }

    @GetMapping("/usage-frequency-analysis")
    @Operation(summary = "获得备件使用频率分析")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-info:query')")
    public CommonResult<SparePartStockStatisticsRespVO.UsageFrequencyStatistics> getUsageFrequencyAnalysis() {
        SparePartStockStatisticsRespVO.UsageFrequencyStatistics analysis = sparePartInfoService.getUsageFrequencyAnalysis();
        return success(analysis);
    }

}