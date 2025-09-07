package cn.iocoder.yudao.module.coal.controller.admin.sparepart;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartEquipmentPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartEquipmentRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartEquipmentSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepart.CoalSparePartEquipmentDO;
import cn.iocoder.yudao.module.coal.service.sparepart.CoalSparePartEquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 备件设备关联")
@RestController
@RequestMapping("/coal/spare-part-equipment")
@Validated
public class CoalSparePartEquipmentController {

    @Resource
    private CoalSparePartEquipmentService sparePartEquipmentService;

    @PostMapping("/create")
    @Operation(summary = "创建备件设备关联")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:create')")
    public CommonResult<Long> createSparePartEquipment(@Valid @RequestBody CoalSparePartEquipmentSaveReqVO createReqVO) {
        return success(sparePartEquipmentService.createSparePartEquipment(
                createReqVO.getSparePartId(), createReqVO.getEquipmentId(), 
                createReqVO.getUsageCount(), createReqVO.getIsRequired(), createReqVO.getRemark()));
    }

    @PutMapping("/update")
    @Operation(summary = "更新备件设备关联")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:update')")
    public CommonResult<Boolean> updateSparePartEquipment(@Valid @RequestBody CoalSparePartEquipmentSaveReqVO updateReqVO) {
        sparePartEquipmentService.updateSparePartEquipment(updateReqVO.getId(), 
                updateReqVO.getUsageCount(), updateReqVO.getIsRequired(), updateReqVO.getRemark());
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
    public CommonResult<CoalSparePartEquipmentRespVO> getSparePartEquipment(@RequestParam("id") Long id) {
        CoalSparePartEquipmentDO sparePartEquipment = sparePartEquipmentService.getSparePartEquipment(id);
        return success(BeanUtils.toBean(sparePartEquipment, CoalSparePartEquipmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得备件设备关联分页")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:query')")
    public CommonResult<PageResult<CoalSparePartEquipmentRespVO>> getSparePartEquipmentPage(@Valid CoalSparePartEquipmentPageReqVO pageReqVO) {
        PageResult<CoalSparePartEquipmentDO> pageResult = sparePartEquipmentService.getSparePartEquipmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CoalSparePartEquipmentRespVO.class));
    }

    @GetMapping("/list-by-spare-part")
    @Operation(summary = "根据备件ID获取设备关联列表")
    @Parameter(name = "sparePartId", description = "备件ID", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:query')")
    public CommonResult<List<CoalSparePartEquipmentRespVO>> getSparePartEquipments(@RequestParam("sparePartId") Long sparePartId) {
        List<CoalSparePartEquipmentDO> list = sparePartEquipmentService.getSparePartEquipments(sparePartId);
        return success(BeanUtils.toBean(list, CoalSparePartEquipmentRespVO.class));
    }

    @GetMapping("/list-by-equipment")
    @Operation(summary = "根据设备ID获取备件关联列表")
    @Parameter(name = "equipmentId", description = "设备ID", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-equipment:query')")
    public CommonResult<List<CoalSparePartEquipmentRespVO>> getEquipmentSpareParts(@RequestParam("equipmentId") Long equipmentId) {
        List<CoalSparePartEquipmentDO> list = sparePartEquipmentService.getEquipmentSpareParts(equipmentId);
        return success(BeanUtils.toBean(list, CoalSparePartEquipmentRespVO.class));
    }

}
