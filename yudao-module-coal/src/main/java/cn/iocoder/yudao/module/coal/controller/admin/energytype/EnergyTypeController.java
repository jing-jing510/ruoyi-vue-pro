package cn.iocoder.yudao.module.coal.controller.admin.energytype;

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

import cn.iocoder.yudao.module.coal.controller.admin.energytype.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.energytype.EnergyTypeDO;
import cn.iocoder.yudao.module.coal.service.energytype.EnergyTypeService;

@Tag(name = "管理后台 - 能源类型配置")
@RestController
@RequestMapping("/coal/energy-type")
@Validated
public class EnergyTypeController {

    @Resource
    private EnergyTypeService energyTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建能源类型配置")
    @PreAuthorize("@ss.hasPermission('coal:energy-type:create')")
    public CommonResult<Long> createEnergyType(@Valid @RequestBody EnergyTypeSaveReqVO createReqVO) {
        return success(energyTypeService.createEnergyType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新能源类型配置")
    @PreAuthorize("@ss.hasPermission('coal:energy-type:update')")
    public CommonResult<Boolean> updateEnergyType(@Valid @RequestBody EnergyTypeSaveReqVO updateReqVO) {
        energyTypeService.updateEnergyType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除能源类型配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:energy-type:delete')")
    public CommonResult<Boolean> deleteEnergyType(@RequestParam("id") Long id) {
        energyTypeService.deleteEnergyType(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除能源类型配置")
                @PreAuthorize("@ss.hasPermission('coal:energy-type:delete')")
    public CommonResult<Boolean> deleteEnergyTypeList(@RequestParam("ids") List<Long> ids) {
        energyTypeService.deleteEnergyTypeListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得能源类型配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:energy-type:query')")
    public CommonResult<EnergyTypeRespVO> getEnergyType(@RequestParam("id") Long id) {
        EnergyTypeDO energyType = energyTypeService.getEnergyType(id);
        return success(BeanUtils.toBean(energyType, EnergyTypeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得能源类型配置分页")
    @PreAuthorize("@ss.hasPermission('coal:energy-type:query')")
    public CommonResult<PageResult<EnergyTypeRespVO>> getEnergyTypePage(@Valid EnergyTypePageReqVO pageReqVO) {
        PageResult<EnergyTypeDO> pageResult = energyTypeService.getEnergyTypePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, EnergyTypeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出能源类型配置 Excel")
    @PreAuthorize("@ss.hasPermission('coal:energy-type:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEnergyTypeExcel(@Valid EnergyTypePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<EnergyTypeDO> list = energyTypeService.getEnergyTypePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "能源类型配置.xls", "数据", EnergyTypeRespVO.class,
                        BeanUtils.toBean(list, EnergyTypeRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得能源类型配置简单列表，用于下拉选择")
    @PreAuthorize("@ss.hasPermission('coal:energy-type:query')")
    public CommonResult<List<EnergyTypeRespVO>> getSimpleEnergyTypeList() {
        List<EnergyTypeDO> list = energyTypeService.getSimpleEnergyTypeList();
        return success(BeanUtils.toBean(list, EnergyTypeRespVO.class));
    }

}