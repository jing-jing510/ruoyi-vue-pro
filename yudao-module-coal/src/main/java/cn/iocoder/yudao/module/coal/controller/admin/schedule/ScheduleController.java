package cn.iocoder.yudao.module.coal.controller.admin.schedule;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.SchedulePageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleStaffDO;
import cn.iocoder.yudao.module.coal.service.schedule.ScheduleService;
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

@Tag(name = "管理后台 - 排班管理 (主表)")
@RestController
@RequestMapping("/coal/schedule")
@Validated
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    @PostMapping("/create")
    @Operation(summary = "创建排班管理 (主表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule:create')")
    public CommonResult<Long> createSchedule(@Valid @RequestBody ScheduleSaveReqVO createReqVO) {
        return success(scheduleService.createSchedule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新排班管理 (主表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule:update')")
    public CommonResult<Boolean> updateSchedule(@Valid @RequestBody ScheduleSaveReqVO updateReqVO) {
        scheduleService.updateSchedule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除排班管理 (主表)")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:schedule:delete')")
    public CommonResult<Boolean> deleteSchedule(@RequestParam("id") Long id) {
        scheduleService.deleteSchedule(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除排班管理 (主表)")
                @PreAuthorize("@ss.hasPermission('coal:schedule:delete')")
    public CommonResult<Boolean> deleteScheduleList(@RequestParam("ids") List<Long> ids) {
        scheduleService.deleteScheduleListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得排班管理 (主表)")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:schedule:query')")
    public CommonResult<ScheduleRespVO> getSchedule(@RequestParam("id") Long id) {
        ScheduleDO schedule = scheduleService.getSchedule(id);
        return success(BeanUtils.toBean(schedule, ScheduleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得排班管理 (主表)分页")
    @PreAuthorize("@ss.hasPermission('coal:schedule:query')")
    public CommonResult<PageResult<ScheduleRespVO>> getSchedulePage(@Valid SchedulePageReqVO pageReqVO) {
        PageResult<ScheduleDO> pageResult = scheduleService.getSchedulePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ScheduleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出排班管理 (主表) Excel")
    @PreAuthorize("@ss.hasPermission('coal:schedule:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportScheduleExcel(@Valid SchedulePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ScheduleDO> list = scheduleService.getSchedulePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "排班管理 (主表).xls", "数据", ScheduleRespVO.class,
                        BeanUtils.toBean(list, ScheduleRespVO.class));
    }

    // ==================== 子表（人员安排 (子表)） ====================

    @GetMapping("/schedule-staff/page")
    @Operation(summary = "获得人员安排 (子表)分页")
    @Parameter(name = "scheduleId", description = "排班ID (关联主表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule:query')")
    public CommonResult<PageResult<ScheduleStaffDO>> getScheduleStaffPage(PageParam pageReqVO,
                                                                                        @RequestParam("scheduleId") Long scheduleId) {
        return success(scheduleService.getScheduleStaffPage(pageReqVO, scheduleId));
    }

    @PostMapping("/schedule-staff/create")
    @Operation(summary = "创建人员安排 (子表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule:create')")
    public CommonResult<Long> createScheduleStaff(@Valid @RequestBody ScheduleStaffDO scheduleStaff) {
        return success(scheduleService.createScheduleStaff(scheduleStaff));
    }

    @PutMapping("/schedule-staff/update")
    @Operation(summary = "更新人员安排 (子表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule:update')")
    public CommonResult<Boolean> updateScheduleStaff(@Valid @RequestBody ScheduleStaffDO scheduleStaff) {
        scheduleService.updateScheduleStaff(scheduleStaff);
        return success(true);
    }

    @DeleteMapping("/schedule-staff/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除人员安排 (子表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule:delete')")
    public CommonResult<Boolean> deleteScheduleStaff(@RequestParam("id") Long id) {
        scheduleService.deleteScheduleStaff(id);
        return success(true);
    }

    @DeleteMapping("/schedule-staff/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除人员安排 (子表)")
    @PreAuthorize("@ss.hasPermission('coal:schedule:delete')")
    public CommonResult<Boolean> deleteScheduleStaffList(@RequestParam("ids") List<Long> ids) {
        scheduleService.deleteScheduleStaffListByIds(ids);
        return success(true);
    }

	@GetMapping("/schedule-staff/get")
	@Operation(summary = "获得人员安排 (子表)")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:schedule:query')")
	public CommonResult<ScheduleStaffDO> getScheduleStaff(@RequestParam("id") Long id) {
	    return success(scheduleService.getScheduleStaff(id));
	}

}