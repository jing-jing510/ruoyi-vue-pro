package cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo.MarketWashingReportPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo.MarketWashingReportRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo.MarketWashingReportSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.marketwashingreport.MarketWashingReportDO;
import cn.iocoder.yudao.module.coal.service.marketwashingreport.MarketWashingReportService;
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

@Tag(name = "管理后台 - 洗选分析报告")
@RestController
@RequestMapping("/coal/market-washing-report")
@Validated
public class MarketWashingReportController {

    @Resource
    private MarketWashingReportService marketWashingReportService;

    @PostMapping("/create")
    @Operation(summary = "创建洗选分析报告")
    @PreAuthorize("@ss.hasPermission('coal:market-washing-report:create')")
    public CommonResult<Long> createMarketWashingReport(@Valid @RequestBody MarketWashingReportSaveReqVO createReqVO) {
        return success(marketWashingReportService.createMarketWashingReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新洗选分析报告")
    @PreAuthorize("@ss.hasPermission('coal:market-washing-report:update')")
    public CommonResult<Boolean> updateMarketWashingReport(@Valid @RequestBody MarketWashingReportSaveReqVO updateReqVO) {
        marketWashingReportService.updateMarketWashingReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除洗选分析报告")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:market-washing-report:delete')")
    public CommonResult<Boolean> deleteMarketWashingReport(@RequestParam("id") Long id) {
        marketWashingReportService.deleteMarketWashingReport(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除洗选分析报告")
                @PreAuthorize("@ss.hasPermission('coal:market-washing-report:delete')")
    public CommonResult<Boolean> deleteMarketWashingReportList(@RequestParam("ids") List<Long> ids) {
        marketWashingReportService.deleteMarketWashingReportListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得洗选分析报告")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:market-washing-report:query')")
    public CommonResult<MarketWashingReportRespVO> getMarketWashingReport(@RequestParam("id") Long id) {
        MarketWashingReportDO marketWashingReport = marketWashingReportService.getMarketWashingReport(id);
        return success(BeanUtils.toBean(marketWashingReport, MarketWashingReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得洗选分析报告分页")
    @PreAuthorize("@ss.hasPermission('coal:market-washing-report:query')")
    public CommonResult<PageResult<MarketWashingReportRespVO>> getMarketWashingReportPage(@Valid MarketWashingReportPageReqVO pageReqVO) {
        PageResult<MarketWashingReportDO> pageResult = marketWashingReportService.getMarketWashingReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MarketWashingReportRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出洗选分析报告 Excel")
    @PreAuthorize("@ss.hasPermission('coal:market-washing-report:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMarketWashingReportExcel(@Valid MarketWashingReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MarketWashingReportDO> list = marketWashingReportService.getMarketWashingReportPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "洗选分析报告.xls", "数据", MarketWashingReportRespVO.class,
                        BeanUtils.toBean(list, MarketWashingReportRespVO.class));
    }

}