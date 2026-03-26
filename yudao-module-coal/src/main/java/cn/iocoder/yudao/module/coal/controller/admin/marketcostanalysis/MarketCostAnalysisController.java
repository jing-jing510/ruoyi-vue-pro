package cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis.vo.MarketCostAnalysisPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis.vo.MarketCostAnalysisRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis.vo.MarketCostAnalysisSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketcostanalysis.MarketCostAnalysisDO;
import cn.iocoder.yudao.module.coal.service.marketcostanalysis.MarketCostAnalysisService;
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

@Tag(name = "管理后台 - 综合成本核算")
@RestController
@RequestMapping("/coal/market-cost-analysis")
@Validated
public class MarketCostAnalysisController {

    @Resource
    private MarketCostAnalysisService marketCostAnalysisService;

    @PostMapping("/create")
    @Operation(summary = "创建综合成本核算")
    @PreAuthorize("@ss.hasPermission('coal:market-cost-analysis:create')")
    public CommonResult<Long> createMarketCostAnalysis(@Valid @RequestBody MarketCostAnalysisSaveReqVO createReqVO) {
        return success(marketCostAnalysisService.createMarketCostAnalysis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新综合成本核算")
    @PreAuthorize("@ss.hasPermission('coal:market-cost-analysis:update')")
    public CommonResult<Boolean> updateMarketCostAnalysis(@Valid @RequestBody MarketCostAnalysisSaveReqVO updateReqVO) {
        marketCostAnalysisService.updateMarketCostAnalysis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除综合成本核算")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:market-cost-analysis:delete')")
    public CommonResult<Boolean> deleteMarketCostAnalysis(@RequestParam("id") Long id) {
        marketCostAnalysisService.deleteMarketCostAnalysis(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除综合成本核算")
                @PreAuthorize("@ss.hasPermission('coal:market-cost-analysis:delete')")
    public CommonResult<Boolean> deleteMarketCostAnalysisList(@RequestParam("ids") List<Long> ids) {
        marketCostAnalysisService.deleteMarketCostAnalysisListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得综合成本核算")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:market-cost-analysis:query')")
    public CommonResult<MarketCostAnalysisRespVO> getMarketCostAnalysis(@RequestParam("id") Long id) {
        MarketCostAnalysisDO marketCostAnalysis = marketCostAnalysisService.getMarketCostAnalysis(id);
        return success(BeanUtils.toBean(marketCostAnalysis, MarketCostAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得综合成本核算分页")
    @PreAuthorize("@ss.hasPermission('coal:market-cost-analysis:query')")
    public CommonResult<PageResult<MarketCostAnalysisRespVO>> getMarketCostAnalysisPage(@Valid MarketCostAnalysisPageReqVO pageReqVO) {
        PageResult<MarketCostAnalysisDO> pageResult = marketCostAnalysisService.getMarketCostAnalysisPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MarketCostAnalysisRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出综合成本核算 Excel")
    @PreAuthorize("@ss.hasPermission('coal:market-cost-analysis:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMarketCostAnalysisExcel(@Valid MarketCostAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MarketCostAnalysisDO> list = marketCostAnalysisService.getMarketCostAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "综合成本核算.xls", "数据", MarketCostAnalysisRespVO.class,
                        BeanUtils.toBean(list, MarketCostAnalysisRespVO.class));
    }

}