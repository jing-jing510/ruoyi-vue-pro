package cn.iocoder.yudao.module.coal.controller.admin.safetyattachment;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo.SafetyAttachmentPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo.SafetyAttachmentRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo.SafetyAttachmentSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetyattachment.SafetyAttachmentDO;
import cn.iocoder.yudao.module.coal.service.safetyattachment.SafetyAttachmentService;
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
import java.util.Map;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 安全附件")
@RestController
@RequestMapping("/coal/safety-attachment")
@Validated
public class SafetyAttachmentController {

    @Resource
    private SafetyAttachmentService safetyAttachmentService;

    @PostMapping("/create")
    @Operation(summary = "创建安全附件")
    @PreAuthorize("@ss.hasPermission('coal:safety-attachment:create')")
    public CommonResult<Long> createSafetyAttachment(@Valid @RequestBody SafetyAttachmentSaveReqVO createReqVO) {
        return success(safetyAttachmentService.createSafetyAttachment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新安全附件")
    @PreAuthorize("@ss.hasPermission('coal:safety-attachment:update')")
    public CommonResult<Boolean> updateSafetyAttachment(@Valid @RequestBody SafetyAttachmentSaveReqVO updateReqVO) {
        safetyAttachmentService.updateSafetyAttachment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除安全附件")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:safety-attachment:delete')")
    public CommonResult<Boolean> deleteSafetyAttachment(@RequestParam("id") Long id) {
        safetyAttachmentService.deleteSafetyAttachment(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除安全附件")
                @PreAuthorize("@ss.hasPermission('coal:safety-attachment:delete')")
    public CommonResult<Boolean> deleteSafetyAttachmentList(@RequestParam("ids") List<Long> ids) {
        safetyAttachmentService.deleteSafetyAttachmentListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得安全附件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:safety-attachment:query')")
    public CommonResult<SafetyAttachmentRespVO> getSafetyAttachment(@RequestParam("id") Long id) {
        SafetyAttachmentDO safetyAttachment = safetyAttachmentService.getSafetyAttachment(id);
        return success(BeanUtils.toBean(safetyAttachment, SafetyAttachmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得安全附件分页")
    @PreAuthorize("@ss.hasPermission('coal:safety-attachment:query')")
    public CommonResult<PageResult<SafetyAttachmentRespVO>> getSafetyAttachmentPage(@Valid SafetyAttachmentPageReqVO pageReqVO) {
        PageResult<SafetyAttachmentDO> pageResult = safetyAttachmentService.getSafetyAttachmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SafetyAttachmentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出安全附件 Excel")
    @PreAuthorize("@ss.hasPermission('coal:safety-attachment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSafetyAttachmentExcel(@Valid SafetyAttachmentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SafetyAttachmentDO> list = safetyAttachmentService.getSafetyAttachmentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "安全附件.xls", "数据", SafetyAttachmentRespVO.class,
                        BeanUtils.toBean(list, SafetyAttachmentRespVO.class));
    }

    @GetMapping("/statistics")
    @Operation(summary = "获得安全附件统计数据")
    @PreAuthorize("@ss.hasPermission('coal:safety-attachment:query')")
    public CommonResult<Map<String, Object>> getSafetyAttachmentStatistics() {
        return success(safetyAttachmentService.getSafetyAttachmentStatistics());
    }

}