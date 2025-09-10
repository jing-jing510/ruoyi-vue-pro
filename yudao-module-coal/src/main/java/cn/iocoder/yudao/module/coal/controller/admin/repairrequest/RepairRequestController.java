package cn.iocoder.yudao.module.coal.controller.admin.repairrequest;

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

import cn.iocoder.yudao.module.coal.controller.admin.repairrequest.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.repairrequest.RepairRequestDO;
import cn.iocoder.yudao.module.coal.service.repairrequest.RepairRequestService;

@Tag(name = "管理后台 - 报修单")
@RestController
@RequestMapping("/coal/repair-request")
@Validated
public class RepairRequestController {

    @Resource
    private RepairRequestService repairRequestService;

    @PostMapping("/create")
    @Operation(summary = "创建报修单")
    @PreAuthorize("@ss.hasPermission('coal:repair-request:create')")
    public CommonResult<Long> createRepairRequest(@Valid @RequestBody RepairRequestSaveReqVO createReqVO) {
        return success(repairRequestService.createRepairRequest(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报修单")
    @PreAuthorize("@ss.hasPermission('coal:repair-request:update')")
    public CommonResult<Boolean> updateRepairRequest(@Valid @RequestBody RepairRequestSaveReqVO updateReqVO) {
        repairRequestService.updateRepairRequest(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报修单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:repair-request:delete')")
    public CommonResult<Boolean> deleteRepairRequest(@RequestParam("id") Long id) {
        repairRequestService.deleteRepairRequest(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除报修单")
                @PreAuthorize("@ss.hasPermission('coal:repair-request:delete')")
    public CommonResult<Boolean> deleteRepairRequestList(@RequestParam("ids") List<Long> ids) {
        repairRequestService.deleteRepairRequestListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报修单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:repair-request:query')")
    public CommonResult<RepairRequestRespVO> getRepairRequest(@RequestParam("id") Long id) {
        RepairRequestDO repairRequest = repairRequestService.getRepairRequest(id);
        return success(BeanUtils.toBean(repairRequest, RepairRequestRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报修单分页")
    @PreAuthorize("@ss.hasPermission('coal:repair-request:query')")
    public CommonResult<PageResult<RepairRequestRespVO>> getRepairRequestPage(@Valid RepairRequestPageReqVO pageReqVO) {
        PageResult<RepairRequestDO> pageResult = repairRequestService.getRepairRequestPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RepairRequestRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报修单 Excel")
    @PreAuthorize("@ss.hasPermission('coal:repair-request:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRepairRequestExcel(@Valid RepairRequestPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RepairRequestDO> list = repairRequestService.getRepairRequestPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "报修单.xls", "数据", RepairRequestRespVO.class,
                        BeanUtils.toBean(list, RepairRequestRespVO.class));
    }

}