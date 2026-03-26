package cn.iocoder.yudao.module.opcua.controller.admin.alarm;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo.*;
import cn.iocoder.yudao.module.opcua.dal.dataobject.alarm.AlarmEventDO;
import cn.iocoder.yudao.module.opcua.service.alarm.AlarmEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 报警事件 Controller
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 报警事件")
@RestController
@RequestMapping("/opcua/alarm-event")
@Validated
public class AlarmEventController {

    @Resource
    private AlarmEventService alarmEventService;

    @PostMapping("/create")
    @Operation(summary = "创建报警事件")
    @PreAuthorize("@ss.hasPermission('opcua:alarm:create')")
    public CommonResult<Long> createAlarmEvent(@Valid @RequestBody AlarmEventSaveReqVO createReqVO) {
        return success(alarmEventService.createAlarmEvent(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报警事件")
    @PreAuthorize("@ss.hasPermission('opcua:alarm:update')")
    public CommonResult<Boolean> updateAlarmEvent(@Valid @RequestBody AlarmEventSaveReqVO updateReqVO) {
        alarmEventService.updateAlarmEvent(updateReqVO);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得报警事件分页")
    @PreAuthorize("@ss.hasPermission('opcua:alarm:query')")
    public CommonResult<PageResult<AlarmEventRespVO>> getAlarmEventPage(@Valid AlarmEventPageReqVO pageReqVO) {
        PageResult<AlarmEventDO> pageResult = alarmEventService.getAlarmEventPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AlarmEventRespVO.class));
    }

    @GetMapping("/get")
    @Operation(summary = "获得报警事件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('opcua:alarm:query')")
    public CommonResult<AlarmEventRespVO> getAlarmEvent(@RequestParam("id") Long id) {
        AlarmEventDO alarmEvent = alarmEventService.getAlarmEvent(id);
        return success(BeanUtils.toBean(alarmEvent, AlarmEventRespVO.class));
    }

    @PutMapping("/handle")
    @Operation(summary = "处理报警事件")
    @PreAuthorize("@ss.hasPermission('opcua:alarm:handle')")
    public CommonResult<Boolean> handleAlarmEvent(@Valid @RequestBody AlarmEventHandleReqVO reqVO) {
        alarmEventService.handleAlarmEvent(reqVO);
        return success(true);
    }

    @GetMapping("/statistics")
    @Operation(summary = "获取报警统计")
    @PreAuthorize("@ss.hasPermission('opcua:alarm:statistics')")
    public CommonResult<AlarmStatisticsRespVO> getAlarmStatistics(@Valid AlarmStatisticsReqVO reqVO) {
        return success(alarmEventService.getAlarmStatistics(reqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报警事件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('opcua:alarm:delete')")
    public CommonResult<Boolean> deleteAlarmEvent(@RequestParam("id") Long id) {
        alarmEventService.deleteAlarmEvent(id);
        return success(true);
    }

}
