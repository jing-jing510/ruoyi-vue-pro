package cn.iocoder.yudao.module.coal.controller.admin.marketanalysis;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo.MarketAnalysisPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo.MarketAnalysisRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo.MarketAnalysisSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketanalysis.MarketAnalysisDO;
import cn.iocoder.yudao.module.coal.service.marketanalysis.MarketAnalysisService;
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

@Tag(name = "管理后台 - 市场分析")
@RestController
@RequestMapping("/coal/market-analysis")
@Validated
public class MarketAnalysisController {

    @Resource
    private MarketAnalysisService marketAnalysisService;

    @PostMapping("/create")
    @Operation(summary = "创建市场分析")
    @PreAuthorize("@ss.hasPermission('coal:market-analysis:create')")
    public CommonResult<Long> createMarketAnalysis(@Valid @RequestBody MarketAnalysisSaveReqVO createReqVO) {
        return success(marketAnalysisService.createMarketAnalysis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新市场分析")
    @PreAuthorize("@ss.hasPermission('coal:market-analysis:update')")
    public CommonResult<Boolean> updateMarketAnalysis(@Valid @RequestBody MarketAnalysisSaveReqVO updateReqVO) {
        marketAnalysisService.updateMarketAnalysis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除市场分析")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:market-analysis:delete')")
    public CommonResult<Boolean> deleteMarketAnalysis(@RequestParam("id") Long id) {
        marketAnalysisService.deleteMarketAnalysis(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除市场分析")
                @PreAuthorize("@ss.hasPermission('coal:market-analysis:delete')")
    public CommonResult<Boolean> deleteMarketAnalysisList(@RequestParam("ids") List<Long> ids) {
        marketAnalysisService.deleteMarketAnalysisListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得市场分析")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:market-analysis:query')")
    public CommonResult<MarketAnalysisRespVO> getMarketAnalysis(@RequestParam("id") Long id) {
        MarketAnalysisDO marketAnalysis = marketAnalysisService.getMarketAnalysis(id);
        return success(BeanUtils.toBean(marketAnalysis, MarketAnalysisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得市场分析分页")
    @PreAuthorize("@ss.hasPermission('coal:market-analysis:query')")
    public CommonResult<PageResult<MarketAnalysisRespVO>> getMarketAnalysisPage(@Valid MarketAnalysisPageReqVO pageReqVO) {
        PageResult<MarketAnalysisDO> pageResult = marketAnalysisService.getMarketAnalysisPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MarketAnalysisRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出市场分析 Excel")
    @PreAuthorize("@ss.hasPermission('coal:market-analysis:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMarketAnalysisExcel(@Valid MarketAnalysisPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MarketAnalysisDO> list = marketAnalysisService.getMarketAnalysisPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "市场分析.xls", "数据", MarketAnalysisRespVO.class,
                        BeanUtils.toBean(list, MarketAnalysisRespVO.class));
    }

}