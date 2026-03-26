package cn.iocoder.yudao.module.opcua.controller.admin.config;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigPageReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigRespVO;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigSaveReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.OpcuaNodeVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.config.OpcuaConfigDO;
import cn.iocoder.yudao.module.opcua.service.config.OpcuaConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * OPC UA 配置 Controller
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - OPC UA 配置")
@RestController
@RequestMapping("/opcua/config")
@Validated
public class OpcuaConfigController {

    @Resource
    private OpcuaConfigService opcuaConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建 OPC UA 配置")
    @PreAuthorize("@ss.hasPermission('opcua:config:create')")
    public CommonResult<Long> createConfig(@Valid @RequestBody OpcuaConfigSaveReqVO createReqVO) {
        return success(opcuaConfigService.createConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新 OPC UA 配置")
    @PreAuthorize("@ss.hasPermission('opcua:config:update')")
    public CommonResult<Boolean> updateConfig(@Valid @RequestBody OpcuaConfigSaveReqVO updateReqVO) {
        opcuaConfigService.updateConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除 OPC UA 配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('opcua:config:delete')")
    public CommonResult<Boolean> deleteConfig(@RequestParam("id") Long id) {
        opcuaConfigService.deleteConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得 OPC UA 配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('opcua:config:query')")
    public CommonResult<OpcuaConfigRespVO> getConfig(@RequestParam("id") Long id) {
        OpcuaConfigDO config = opcuaConfigService.getConfig(id);
        return success(BeanUtils.toBean(config, OpcuaConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得 OPC UA 配置分页")
    @PreAuthorize("@ss.hasPermission('opcua:config:query')")
    public CommonResult<PageResult<OpcuaConfigRespVO>> getConfigPage(@Valid OpcuaConfigPageReqVO pageReqVO) {
        PageResult<OpcuaConfigDO> pageResult = opcuaConfigService.getConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpcuaConfigRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得 OPC UA 配置列表")
    @PreAuthorize("@ss.hasPermission('opcua:config:query')")
    public CommonResult<List<OpcuaConfigRespVO>> getConfigList() {
        List<OpcuaConfigDO> list = opcuaConfigService.getConfigList();
        return success(BeanUtils.toBean(list, OpcuaConfigRespVO.class));
    }

    @GetMapping("/test-connection")
    @Operation(summary = "测试连接")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('opcua:config:query')")
    public CommonResult<Boolean> testConnection(@RequestParam("id") Long id) {
        return success(opcuaConfigService.testConnection(id));
    }

    @GetMapping("/browse-nodes")
    @Operation(summary = "浏览 OPC UA 节点")
    @Parameter(name = "id", description = "配置ID", required = true, example = "1024")
    @Parameter(name = "parentNodeId", description = "父节点ID", required = false)
    @PreAuthorize("@ss.hasPermission('opcua:config:query')")
    public CommonResult<List<OpcuaNodeVO>> browseNodes(
            @RequestParam("id") Long id,
            @RequestParam(value = "parentNodeId", required = false) String parentNodeId) {
        return success(opcuaConfigService.browseNodes(id, parentNodeId));
    }

    @GetMapping("/browse-folders")
    @Operation(summary = "浏览 OPC UA 文件夹")
    @Parameter(name = "id", description = "配置ID", required = true, example = "1024")
    @Parameter(name = "parentNodeId", description = "父节点ID", required = false)
    @PreAuthorize("@ss.hasPermission('opcua:config:query')")
    public CommonResult<List<OpcuaNodeVO>> browseFolders(
            @RequestParam("id") Long id,
            @RequestParam(value = "parentNodeId", required = false) String parentNodeId) {
        return success(opcuaConfigService.browseFolders(id, parentNodeId));
    }

}
