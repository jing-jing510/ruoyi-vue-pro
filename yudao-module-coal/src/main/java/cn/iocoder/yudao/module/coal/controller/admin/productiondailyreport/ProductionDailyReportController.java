package cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo.ProductionDailyReportPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo.ProductionDailyReportRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo.ProductionDailyReportSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.productiondailyreport.ProductionDailyReportDO;
import cn.iocoder.yudao.module.coal.service.productiondailyreport.ProductionDailyReportService;
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

@Tag(name = "管理后台 - 现场生产日报")
@RestController
@RequestMapping("/coal/production-daily-report")
@Validated
public class ProductionDailyReportController {

    @Resource
    private ProductionDailyReportService productionDailyReportService;

    @PostMapping("/create")
    @Operation(summary = "创建现场生产日报")
    @PreAuthorize("@ss.hasPermission('coal:production-daily-report:create')")
    public CommonResult<Long> createProductionDailyReport(@Valid @RequestBody ProductionDailyReportSaveReqVO createReqVO) {
        return success(productionDailyReportService.createProductionDailyReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新现场生产日报")
    @PreAuthorize("@ss.hasPermission('coal:production-daily-report:update')")
    public CommonResult<Boolean> updateProductionDailyReport(@Valid @RequestBody ProductionDailyReportSaveReqVO updateReqVO) {
        productionDailyReportService.updateProductionDailyReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除现场生产日报")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:production-daily-report:delete')")
    public CommonResult<Boolean> deleteProductionDailyReport(@RequestParam("id") Long id) {
        productionDailyReportService.deleteProductionDailyReport(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除现场生产日报")
                @PreAuthorize("@ss.hasPermission('coal:production-daily-report:delete')")
    public CommonResult<Boolean> deleteProductionDailyReportList(@RequestParam("ids") List<Long> ids) {
        productionDailyReportService.deleteProductionDailyReportListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得现场生产日报")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:production-daily-report:query')")
    public CommonResult<ProductionDailyReportRespVO> getProductionDailyReport(@RequestParam("id") Long id) {
        ProductionDailyReportDO productionDailyReport = productionDailyReportService.getProductionDailyReport(id);
        return success(BeanUtils.toBean(productionDailyReport, ProductionDailyReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得现场生产日报分页")
    @PreAuthorize("@ss.hasPermission('coal:production-daily-report:query')")
    public CommonResult<PageResult<ProductionDailyReportRespVO>> getProductionDailyReportPage(@Valid ProductionDailyReportPageReqVO pageReqVO) {
        PageResult<ProductionDailyReportDO> pageResult = productionDailyReportService.getProductionDailyReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProductionDailyReportRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出现场生产日报 Excel")
    @PreAuthorize("@ss.hasPermission('coal:production-daily-report:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductionDailyReportExcel(@Valid ProductionDailyReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProductionDailyReportDO> list = productionDailyReportService.getProductionDailyReportPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "现场生产日报.xls", "数据", ProductionDailyReportRespVO.class,
                        BeanUtils.toBean(list, ProductionDailyReportRespVO.class));
    }

}