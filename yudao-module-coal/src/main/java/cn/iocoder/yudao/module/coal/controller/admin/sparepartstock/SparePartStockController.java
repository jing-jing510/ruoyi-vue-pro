package cn.iocoder.yudao.module.coal.controller.admin.sparepartstock;

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

import cn.iocoder.yudao.module.coal.controller.admin.sparepartstock.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartstock.SparePartStockDO;
import cn.iocoder.yudao.module.coal.service.sparepartstock.SparePartStockService;

@Tag(name = "管理后台 - 备件库存记录")
@RestController
@RequestMapping("/coal/spare-part-stock")
@Validated
public class SparePartStockController {

    @Resource
    private SparePartStockService sparePartStockService;

    @PostMapping("/create")
    @Operation(summary = "创建备件库存记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-stock:create')")
    public CommonResult<Long> createSparePartStock(@Valid @RequestBody SparePartStockSaveReqVO createReqVO) {
        return success(sparePartStockService.createSparePartStock(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新备件库存记录")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-stock:update')")
    public CommonResult<Boolean> updateSparePartStock(@Valid @RequestBody SparePartStockSaveReqVO updateReqVO) {
        sparePartStockService.updateSparePartStock(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除备件库存记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-stock:delete')")
    public CommonResult<Boolean> deleteSparePartStock(@RequestParam("id") Long id) {
        sparePartStockService.deleteSparePartStock(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除备件库存记录")
                @PreAuthorize("@ss.hasPermission('coal:spare-part-stock:delete')")
    public CommonResult<Boolean> deleteSparePartStockList(@RequestParam("ids") List<Long> ids) {
        sparePartStockService.deleteSparePartStockListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得备件库存记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-stock:query')")
    public CommonResult<SparePartStockRespVO> getSparePartStock(@RequestParam("id") Long id) {
        SparePartStockDO sparePartStock = sparePartStockService.getSparePartStock(id);
        return success(BeanUtils.toBean(sparePartStock, SparePartStockRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得备件库存记录分页")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-stock:query')")
    public CommonResult<PageResult<SparePartStockRespVO>> getSparePartStockPage(@Valid SparePartStockPageReqVO pageReqVO) {
        PageResult<SparePartStockDO> pageResult = sparePartStockService.getSparePartStockPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SparePartStockRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出备件库存记录 Excel")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-stock:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSparePartStockExcel(@Valid SparePartStockPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SparePartStockDO> list = sparePartStockService.getSparePartStockPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "备件库存记录.xls", "数据", SparePartStockRespVO.class,
                        BeanUtils.toBean(list, SparePartStockRespVO.class));
    }

}