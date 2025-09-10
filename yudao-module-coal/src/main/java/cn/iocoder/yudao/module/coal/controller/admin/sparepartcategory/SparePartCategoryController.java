package cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo.SparePartCategoryListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo.SparePartCategoryRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo.SparePartCategorySaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartcategory.SparePartCategoryDO;
import cn.iocoder.yudao.module.coal.service.sparepartcategory.SparePartCategoryService;
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

@Tag(name = "管理后台 - 备件分类表 (树表)")
@RestController
@RequestMapping("/coal/spare-part-category")
@Validated
public class SparePartCategoryController {

    @Resource
    private SparePartCategoryService sparePartCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建备件分类表 (树表)")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-category:create')")
    public CommonResult<Long> createSparePartCategory(@Valid @RequestBody SparePartCategorySaveReqVO createReqVO) {
        return success(sparePartCategoryService.createSparePartCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新备件分类表 (树表)")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-category:update')")
    public CommonResult<Boolean> updateSparePartCategory(@Valid @RequestBody SparePartCategorySaveReqVO updateReqVO) {
        sparePartCategoryService.updateSparePartCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除备件分类表 (树表)")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:spare-part-category:delete')")
    public CommonResult<Boolean> deleteSparePartCategory(@RequestParam("id") Long id) {
        sparePartCategoryService.deleteSparePartCategory(id);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得备件分类表 (树表)")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-category:query')")
    public CommonResult<SparePartCategoryRespVO> getSparePartCategory(@RequestParam("id") Long id) {
        SparePartCategoryDO sparePartCategory = sparePartCategoryService.getSparePartCategory(id);
        return success(BeanUtils.toBean(sparePartCategory, SparePartCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得备件分类表 (树表)列表")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-category:query')")
    public CommonResult<List<SparePartCategoryRespVO>> getSparePartCategoryList(@Valid SparePartCategoryListReqVO listReqVO) {
        List<SparePartCategoryDO> list = sparePartCategoryService.getSparePartCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, SparePartCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出备件分类表 (树表) Excel")
    @PreAuthorize("@ss.hasPermission('coal:spare-part-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSparePartCategoryExcel(@Valid SparePartCategoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<SparePartCategoryDO> list = sparePartCategoryService.getSparePartCategoryList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "备件分类表 (树表).xls", "数据", SparePartCategoryRespVO.class,
                        BeanUtils.toBean(list, SparePartCategoryRespVO.class));
    }

}