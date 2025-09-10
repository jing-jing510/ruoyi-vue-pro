package cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo.EquipmentInfoListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo.EquipmentInfoRespVO;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo.EquipmentInfoSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.equipmentinfo.EquipmentInfoDO;
import cn.iocoder.yudao.module.coal.service.equipmentinfo.EquipmentInfoService;
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

@Tag(name = "管理后台 - 设备档案")
@RestController
@RequestMapping("/coal/equipment-info")
@Validated
public class EquipmentInfoController {

    @Resource
    private EquipmentInfoService equipmentInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建设备档案")
    @PreAuthorize("@ss.hasPermission('coal:equipment-info:create')")
    public CommonResult<Long> createEquipmentInfo(@Valid @RequestBody EquipmentInfoSaveReqVO createReqVO) {
        return success(equipmentInfoService.createEquipmentInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备档案")
    @PreAuthorize("@ss.hasPermission('coal:equipment-info:update')")
    public CommonResult<Boolean> updateEquipmentInfo(@Valid @RequestBody EquipmentInfoSaveReqVO updateReqVO) {
        equipmentInfoService.updateEquipmentInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备档案")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('coal:equipment-info:delete')")
    public CommonResult<Boolean> deleteEquipmentInfo(@RequestParam("id") Long id) {
        equipmentInfoService.deleteEquipmentInfo(id);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得设备档案")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('coal:equipment-info:query')")
    public CommonResult<EquipmentInfoRespVO> getEquipmentInfo(@RequestParam("id") Long id) {
        EquipmentInfoDO equipmentInfo = equipmentInfoService.getEquipmentInfo(id);
        return success(BeanUtils.toBean(equipmentInfo, EquipmentInfoRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备档案列表")
    @PreAuthorize("@ss.hasPermission('coal:equipment-info:query')")
    public CommonResult<List<EquipmentInfoRespVO>> getEquipmentInfoList(@Valid EquipmentInfoListReqVO listReqVO) {
        List<EquipmentInfoDO> list = equipmentInfoService.getEquipmentInfoList(listReqVO);
        return success(BeanUtils.toBean(list, EquipmentInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备档案 Excel")
    @PreAuthorize("@ss.hasPermission('coal:equipment-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquipmentInfoExcel(@Valid EquipmentInfoListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<EquipmentInfoDO> list = equipmentInfoService.getEquipmentInfoList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "设备档案.xls", "数据", EquipmentInfoRespVO.class,
                        BeanUtils.toBean(list, EquipmentInfoRespVO.class));
    }

    @GetMapping("/get-names")
    @Operation(summary = "根据设备IDs获取设备名称映射")
    @PreAuthorize("@ss.hasPermission('coal:equipment-info:query')")
    public CommonResult<Map<Long, String>> getEquipmentNames(@RequestParam("ids") List<Long> ids) {
        Map<Long, String> equipmentNameMap = equipmentInfoService.getEquipmentNameMap(ids);
        return success(equipmentNameMap);
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得设备档案简单列表，用于下拉选择")
    @PreAuthorize("@ss.hasPermission('coal:equipment-info:query')")
    public CommonResult<List<EquipmentInfoRespVO>> getSimpleEquipmentList() {
        List<EquipmentInfoDO> list = equipmentInfoService.getSimpleEquipmentList();
        return success(BeanUtils.toBean(list, EquipmentInfoRespVO.class));
    }

}