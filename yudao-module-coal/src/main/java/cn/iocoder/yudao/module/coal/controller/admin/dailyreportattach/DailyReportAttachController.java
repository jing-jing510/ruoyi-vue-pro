package cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo.DailyReportAttachPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo.DailyReportAttachRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo.DailyReportAttachSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.dailyreportattach.DailyReportAttachDO;
import cn.iocoder.yudao.module.coal.service.dailyreportattach.DailyReportAttachService;
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

@Tag(name = "管理后台 - 生产日报附件上传")
@RestController
@RequestMapping("/coal/daily-report-attach")
@Validated
public class DailyReportAttachController {

    @Resource
    private DailyReportAttachService dailyReportAttachService;

    @PostMapping("/create")
    @Operation(summary = "创建生产日报附件上传")
    @PreAuthorize("@ss.hasPermission('coal:daily-report-attach:create')")
    public CommonResult<Long> createDailyReportAttach(@Valid @RequestBody DailyReportAttachSaveReqVO createReqVO) {
        return success(dailyReportAttachService.createDailyReportAttach(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新生产日报附件上传")
    @PreAuthorize("@ss.hasPermission('coal:daily-report-attach:update')")
    public CommonResult<Boolean> updateDailyReportAttach(@Valid @RequestBody DailyReportAttachSaveReqVO updateReqVO) {
        dailyReportAttachService.updateDailyReportAttach(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生产日报附件上传")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:daily-report-attach:delete')")
    public CommonResult<Boolean> deleteDailyReportAttach(@RequestParam("id") Long id) {
        dailyReportAttachService.deleteDailyReportAttach(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除生产日报附件上传")
                @PreAuthorize("@ss.hasPermission('coal:daily-report-attach:delete')")
    public CommonResult<Boolean> deleteDailyReportAttachList(@RequestParam("ids") List<Long> ids) {
        dailyReportAttachService.deleteDailyReportAttachListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得生产日报附件上传")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:daily-report-attach:query')")
    public CommonResult<DailyReportAttachRespVO> getDailyReportAttach(@RequestParam("id") Long id) {
        DailyReportAttachDO dailyReportAttach = dailyReportAttachService.getDailyReportAttach(id);
        return success(BeanUtils.toBean(dailyReportAttach, DailyReportAttachRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得生产日报附件上传分页")
    @PreAuthorize("@ss.hasPermission('coal:daily-report-attach:query')")
    public CommonResult<PageResult<DailyReportAttachRespVO>> getDailyReportAttachPage(@Valid DailyReportAttachPageReqVO pageReqVO) {
        PageResult<DailyReportAttachDO> pageResult = dailyReportAttachService.getDailyReportAttachPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DailyReportAttachRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出生产日报附件上传 Excel")
    @PreAuthorize("@ss.hasPermission('coal:daily-report-attach:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDailyReportAttachExcel(@Valid DailyReportAttachPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DailyReportAttachDO> list = dailyReportAttachService.getDailyReportAttachPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "生产日报附件上传.xls", "数据", DailyReportAttachRespVO.class,
                        BeanUtils.toBean(list, DailyReportAttachRespVO.class));
    }

}