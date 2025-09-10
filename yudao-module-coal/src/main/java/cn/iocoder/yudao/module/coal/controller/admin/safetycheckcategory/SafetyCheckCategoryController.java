package cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory;

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

import cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckcategory.SafetyCheckCategoryDO;
import cn.iocoder.yudao.module.coal.service.safetycheckcategory.SafetyCheckCategoryService;

@Tag(name = "管理后台 - 安全检查分类")
@RestController
@RequestMapping("/coal/safety-check-category")
@Validated
public class SafetyCheckCategoryController {

    @Resource
    private SafetyCheckCategoryService safetyCheckCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建安全检查分类")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-category:create')")
    public CommonResult<Long> createSafetyCheckCategory(@Valid @RequestBody SafetyCheckCategorySaveReqVO createReqVO) {
        return success(safetyCheckCategoryService.createSafetyCheckCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新安全检查分类")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-category:update')")
    public CommonResult<Boolean> updateSafetyCheckCategory(@Valid @RequestBody SafetyCheckCategorySaveReqVO updateReqVO) {
        safetyCheckCategoryService.updateSafetyCheckCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除安全检查分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:safety-check-category:delete')")
    public CommonResult<Boolean> deleteSafetyCheckCategory(@RequestParam("id") Long id) {
        safetyCheckCategoryService.deleteSafetyCheckCategory(id);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得安全检查分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-category:query')")
    public CommonResult<SafetyCheckCategoryRespVO> getSafetyCheckCategory(@RequestParam("id") Long id) {
        SafetyCheckCategoryDO safetyCheckCategory = safetyCheckCategoryService.getSafetyCheckCategory(id);
        return success(BeanUtils.toBean(safetyCheckCategory, SafetyCheckCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得安全检查分类列表")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-category:query')")
    public CommonResult<List<SafetyCheckCategoryRespVO>> getSafetyCheckCategoryList(@Valid SafetyCheckCategoryListReqVO listReqVO) {
        List<SafetyCheckCategoryDO> list = safetyCheckCategoryService.getSafetyCheckCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, SafetyCheckCategoryRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得安全检查分类简单列表，用于下拉选择")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-category:query')")
    public CommonResult<List<SafetyCheckCategoryRespVO>> getSimpleSafetyCheckCategoryList() {
        List<SafetyCheckCategoryDO> list = safetyCheckCategoryService.getSimpleSafetyCheckCategoryList();
        return success(BeanUtils.toBean(list, SafetyCheckCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出安全检查分类 Excel")
    @PreAuthorize("@ss.hasPermission('coal:safety-check-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSafetyCheckCategoryExcel(@Valid SafetyCheckCategoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<SafetyCheckCategoryDO> list = safetyCheckCategoryService.getSafetyCheckCategoryList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "安全检查分类.xls", "数据", SafetyCheckCategoryRespVO.class,
                        BeanUtils.toBean(list, SafetyCheckCategoryRespVO.class));
    }

}