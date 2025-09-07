package cn.iocoder.yudao.module.coal.controller.admin.schedule;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleStaffPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleStaffRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleStaffSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleStaffDO;
import cn.iocoder.yudao.module.coal.service.schedule.ScheduleStaffService;
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

@Tag(name = "管理后台 - 人员安排 (子表)")
@RestController
@RequestMapping("/coal/schedule-staff")
@Validated
public class ScheduleStaffController {

    @Resource
    private ScheduleStaffService scheduleStaffService;

    @PostMapping("/create")
    @Operation(summary = "创建人员安排 (子表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule-staff:create')")
    public CommonResult<Long> createScheduleStaff(@Valid @RequestBody ScheduleStaffSaveReqVO createReqVO) {
        return success(scheduleStaffService.createScheduleStaff(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新人员安排 (子表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule-staff:update')")
    public CommonResult<Boolean> updateScheduleStaff(@Valid @RequestBody ScheduleStaffSaveReqVO updateReqVO) {
        scheduleStaffService.updateScheduleStaff(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除人员安排 (子表)")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:schedule-staff:delete')")
    public CommonResult<Boolean> deleteScheduleStaff(@RequestParam("id") Long id) {
        scheduleStaffService.deleteScheduleStaff(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除人员安排 (子表)")
                @PreAuthorize("@ss.hasPermission('coal:schedule-staff:delete')")
    public CommonResult<Boolean> deleteScheduleStaffList(@RequestParam("ids") List<Long> ids) {
        scheduleStaffService.deleteScheduleStaffListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得人员安排 (子表)")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:schedule-staff:query')")
    public CommonResult<ScheduleStaffRespVO> getScheduleStaff(@RequestParam("id") Long id) {
        ScheduleStaffDO scheduleStaff = scheduleStaffService.getScheduleStaff(id);
        return success(BeanUtils.toBean(scheduleStaff, ScheduleStaffRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得人员安排 (子表)分页")
    @PreAuthorize("@ss.hasPermission('coal:schedule-staff:query')")
    public CommonResult<PageResult<ScheduleStaffRespVO>> getScheduleStaffPage(@Valid ScheduleStaffPageReqVO pageReqVO) {
        PageResult<ScheduleStaffDO> pageResult = scheduleStaffService.getScheduleStaffPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ScheduleStaffRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出人员安排 (子表) Excel")
    @PreAuthorize("@ss.hasPermission('coal:schedule-staff:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportScheduleStaffExcel(@Valid ScheduleStaffPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ScheduleStaffDO> list = scheduleStaffService.getScheduleStaffPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "人员安排 (子表).xls", "数据", ScheduleStaffRespVO.class,
                        BeanUtils.toBean(list, ScheduleStaffRespVO.class));
    }

}