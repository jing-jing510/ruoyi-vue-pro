package cn.iocoder.yudao.module.coal.controller.admin.shiftsystem;

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

import cn.iocoder.yudao.module.coal.controller.admin.shiftsystem.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.shiftsystem.ShiftSystemDO;
import cn.iocoder.yudao.module.coal.service.shiftsystem.ShiftSystemService;

@Tag(name = "管理后台 - 班制与班次设置 (树表)")
@RestController
@RequestMapping("/coal/shift-system")
@Validated
public class ShiftSystemController {

    @Resource
    private ShiftSystemService shiftSystemService;

    @PostMapping("/create")
    @Operation(summary = "创建班制与班次设置 (树表)")
    @PreAuthorize("@ss.hasPermission('coal:shift-system:create')")
    public CommonResult<Long> createShiftSystem(@Valid @RequestBody ShiftSystemSaveReqVO createReqVO) {
        return success(shiftSystemService.createShiftSystem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新班制与班次设置 (树表)")
    @PreAuthorize("@ss.hasPermission('coal:shift-system:update')")
    public CommonResult<Boolean> updateShiftSystem(@Valid @RequestBody ShiftSystemSaveReqVO updateReqVO) {
        shiftSystemService.updateShiftSystem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除班制与班次设置 (树表)")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:shift-system:delete')")
    public CommonResult<Boolean> deleteShiftSystem(@RequestParam("id") Long id) {
        shiftSystemService.deleteShiftSystem(id);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得班制与班次设置 (树表)")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:shift-system:query')")
    public CommonResult<ShiftSystemRespVO> getShiftSystem(@RequestParam("id") Long id) {
        ShiftSystemDO shiftSystem = shiftSystemService.getShiftSystem(id);
        return success(BeanUtils.toBean(shiftSystem, ShiftSystemRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得班制与班次设置 (树表)列表")
    @PreAuthorize("@ss.hasPermission('coal:shift-system:query')")
    public CommonResult<List<ShiftSystemRespVO>> getShiftSystemList(@Valid ShiftSystemListReqVO listReqVO) {
        List<ShiftSystemDO> list = shiftSystemService.getShiftSystemList(listReqVO);
        return success(BeanUtils.toBean(list, ShiftSystemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出班制与班次设置 (树表) Excel")
    @PreAuthorize("@ss.hasPermission('coal:shift-system:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportShiftSystemExcel(@Valid ShiftSystemListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<ShiftSystemDO> list = shiftSystemService.getShiftSystemList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "班制与班次设置 (树表).xls", "数据", ShiftSystemRespVO.class,
                        BeanUtils.toBean(list, ShiftSystemRespVO.class));
    }

}