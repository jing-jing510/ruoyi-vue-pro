package cn.iocoder.yudao.module.coal.controller.admin.qualityitem;

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

import cn.iocoder.yudao.module.coal.controller.admin.qualityitem.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityitem.QualityItemDO;
import cn.iocoder.yudao.module.coal.service.qualityitem.QualityItemService;

@Tag(name = "管理后台 - 质量检测项目")
@RestController
@RequestMapping("/coal/quality-item")
@Validated
public class QualityItemController {

    @Resource
    private QualityItemService qualityItemService;

    @PostMapping("/create")
    @Operation(summary = "创建质量检测项目")
    @PreAuthorize("@ss.hasPermission('coal:quality-item:create')")
    public CommonResult<Long> createQualityItem(@Valid @RequestBody QualityItemSaveReqVO createReqVO) {
        return success(qualityItemService.createQualityItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新质量检测项目")
    @PreAuthorize("@ss.hasPermission('coal:quality-item:update')")
    public CommonResult<Boolean> updateQualityItem(@Valid @RequestBody QualityItemSaveReqVO updateReqVO) {
        qualityItemService.updateQualityItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除质量检测项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:quality-item:delete')")
    public CommonResult<Boolean> deleteQualityItem(@RequestParam("id") Long id) {
        qualityItemService.deleteQualityItem(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除质量检测项目")
                @PreAuthorize("@ss.hasPermission('coal:quality-item:delete')")
    public CommonResult<Boolean> deleteQualityItemList(@RequestParam("ids") List<Long> ids) {
        qualityItemService.deleteQualityItemListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得质量检测项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:quality-item:query')")
    public CommonResult<QualityItemRespVO> getQualityItem(@RequestParam("id") Long id) {
        QualityItemDO qualityItem = qualityItemService.getQualityItem(id);
        return success(BeanUtils.toBean(qualityItem, QualityItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得质量检测项目分页")
    @PreAuthorize("@ss.hasPermission('coal:quality-item:query')")
    public CommonResult<PageResult<QualityItemRespVO>> getQualityItemPage(@Valid QualityItemPageReqVO pageReqVO) {
        PageResult<QualityItemDO> pageResult = qualityItemService.getQualityItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QualityItemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出质量检测项目 Excel")
    @PreAuthorize("@ss.hasPermission('coal:quality-item:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQualityItemExcel(@Valid QualityItemPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QualityItemDO> list = qualityItemService.getQualityItemPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "质量检测项目.xls", "数据", QualityItemRespVO.class,
                        BeanUtils.toBean(list, QualityItemRespVO.class));
    }

}