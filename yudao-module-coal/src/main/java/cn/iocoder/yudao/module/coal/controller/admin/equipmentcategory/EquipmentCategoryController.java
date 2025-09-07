package cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo.EquipmentCategoryListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo.EquipmentCategoryRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo.EquipmentCategorySaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.equipmentcategory.EquipmentCategoryDO;
import cn.iocoder.yudao.module.coal.service.equipmentcategory.EquipmentCategoryService;
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

@Tag(name = "管理后台 - 设备分类表 (树表)")
@RestController
@RequestMapping("/coal/equipment-category")
@Validated
public class EquipmentCategoryController {

    @Resource
    private EquipmentCategoryService equipmentCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建设备分类表 (树表)")
    @PreAuthorize("@ss.hasPermission('coal:equipment-category:create')")
    public CommonResult<Long> createEquipmentCategory(@Valid @RequestBody EquipmentCategorySaveReqVO createReqVO) {
        return success(equipmentCategoryService.createEquipmentCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备分类表 (树表)")
    @PreAuthorize("@ss.hasPermission('coal:equipment-category:update')")
    public CommonResult<Boolean> updateEquipmentCategory(@Valid @RequestBody EquipmentCategorySaveReqVO updateReqVO) {
        equipmentCategoryService.updateEquipmentCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备分类表 (树表)")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:equipment-category:delete')")
    public CommonResult<Boolean> deleteEquipmentCategory(@RequestParam("id") Long id) {
        equipmentCategoryService.deleteEquipmentCategory(id);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得设备分类表 (树表)")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:equipment-category:query')")
    public CommonResult<EquipmentCategoryRespVO> getEquipmentCategory(@RequestParam("id") Long id) {
        EquipmentCategoryDO equipmentCategory = equipmentCategoryService.getEquipmentCategory(id);
        return success(BeanUtils.toBean(equipmentCategory, EquipmentCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备分类表 (树表)列表")
    @PreAuthorize("@ss.hasPermission('coal:equipment-category:query')")
    public CommonResult<List<EquipmentCategoryRespVO>> getEquipmentCategoryList(@Valid EquipmentCategoryListReqVO listReqVO) {
        List<EquipmentCategoryDO> list = equipmentCategoryService.getEquipmentCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, EquipmentCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备分类表 (树表) Excel")
    @PreAuthorize("@ss.hasPermission('coal:equipment-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquipmentCategoryExcel(@Valid EquipmentCategoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<EquipmentCategoryDO> list = equipmentCategoryService.getEquipmentCategoryList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "设备分类表 (树表).xls", "数据", EquipmentCategoryRespVO.class,
                        BeanUtils.toBean(list, EquipmentCategoryRespVO.class));
    }

}