package cn.iocoder.yudao.module.coal.controller.admin.sparepartequipment;

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

import cn.iocoder.yudao.module.coal.controller.admin.sparepartequipment.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartequipment.SparePartEquipmentDO;
import cn.iocoder.yudao.module.coal.service.sparepartequipment.SparePartEquipmentService;

@Tag(name = "管理后台 - 备件设备关联")
@RestController
@RequestMapping("/coal/spare-part-equipment")
@Validated
public class SparePartEquipmentController {

    @Resource
    private SparePartEquipmentService sparePartEquipmentService;

    @PostMapping("/create")
    @Operation(summary = "创建备件设备关联")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:create')")
    public CommonResult<Long> createSparePartEquipment(@Valid @RequestBody SparePartEquipmentSaveReqVO createReqVO) {
        return success(sparePartEquipmentService.createSparePartEquipment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新备件设备关联")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:update')")
    public CommonResult<Boolean> updateSparePartEquipment(@Valid @RequestBody SparePartEquipmentSaveReqVO updateReqVO) {
        sparePartEquipmentService.updateSparePartEquipment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除备件设备关联")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:delete')")
    public CommonResult<Boolean> deleteSparePartEquipment(@RequestParam("id") Long id) {
        sparePartEquipmentService.deleteSparePartEquipment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得备件设备关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:query')")
    public CommonResult<SparePartEquipmentRespVO> getSparePartEquipment(@RequestParam("id") Long id) {
        SparePartEquipmentDO sparePartEquipment = sparePartEquipmentService.getSparePartEquipment(id);
        return success(BeanUtils.toBean(sparePartEquipment, SparePartEquipmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得备件设备关联分页")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:query')")
    public CommonResult<PageResult<SparePartEquipmentRespVO>> getSparePartEquipmentPage(@Valid SparePartEquipmentPageReqVO pageReqVO) {
        PageResult<SparePartEquipmentDO> pageResult = sparePartEquipmentService.getSparePartEquipmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SparePartEquipmentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出备件设备关联 Excel")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSparePartEquipmentExcel(@Valid SparePartEquipmentPageReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SparePartEquipmentDO> list = sparePartEquipmentService.getSparePartEquipmentList(exportReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "备件设备关联.xls", "数据", SparePartEquipmentRespVO.class,
                        BeanUtils.toBean(list, SparePartEquipmentRespVO.class));
    }

    @GetMapping("/list-by-spare-part")
    @Operation(summary = "根据备件ID获取关联的设备列表")
    @Parameter(name = "sparePartId", description = "备件ID", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:query')")
    public CommonResult<List<SparePartEquipmentRespVO>> getSparePartEquipmentListBySparePartId(@RequestParam("sparePartId") Long sparePartId) {
        List<SparePartEquipmentDO> list = sparePartEquipmentService.getSparePartEquipmentListBySparePartId(sparePartId);
        return success(BeanUtils.toBean(list, SparePartEquipmentRespVO.class));
    }

    @GetMapping("/list-by-equipment")
    @Operation(summary = "根据设备ID获取关联的备件列表")
    @Parameter(name = "equipmentId", description = "设备ID", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:query')")
    public CommonResult<List<SparePartEquipmentRespVO>> getSparePartEquipmentListByEquipmentId(@RequestParam("equipmentId") Long equipmentId) {
        List<SparePartEquipmentDO> list = sparePartEquipmentService.getSparePartEquipmentListByEquipmentId(equipmentId);
        return success(BeanUtils.toBean(list, SparePartEquipmentRespVO.class));
    }

}
