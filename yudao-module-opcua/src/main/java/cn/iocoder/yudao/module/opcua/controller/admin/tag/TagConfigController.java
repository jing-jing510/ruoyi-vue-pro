package cn.iocoder.yudao.module.opcua.controller.admin.tag;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.TagConfigPageReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.TagConfigRespVO;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.TagConfigSaveReqVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.tag.TagConfigDO;
import cn.iocoder.yudao.module.opcua.service.tag.TagConfigService;
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
 * 点位配置 Controller
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - 点位配置")
@RestController
@RequestMapping("/opcua/tag")
@Validated
public class TagConfigController {

    @Resource
    private TagConfigService tagConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建点位配置")
    @PreAuthorize("@ss.hasPermission('opcua:tag:create')")
    public CommonResult<Long> createTag(@Valid @RequestBody TagConfigSaveReqVO createReqVO) {
        return success(tagConfigService.createTag(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新点位配置")
    @PreAuthorize("@ss.hasPermission('opcua:tag:update')")
    public CommonResult<Boolean> updateTag(@Valid @RequestBody TagConfigSaveReqVO updateReqVO) {
        tagConfigService.updateTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除点位配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('opcua:tag:delete')")
    public CommonResult<Boolean> deleteTag(@RequestParam("id") Long id) {
        tagConfigService.deleteTag(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得点位配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('opcua:tag:query')")
    public CommonResult<TagConfigRespVO> getTag(@RequestParam("id") Long id) {
        TagConfigDO tag = tagConfigService.getTag(id);
        return success(BeanUtils.toBean(tag, TagConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得点位配置分页")
    @PreAuthorize("@ss.hasPermission('opcua:tag:query')")
    public CommonResult<PageResult<TagConfigRespVO>> getTagPage(@Valid TagConfigPageReqVO pageReqVO) {
        PageResult<TagConfigDO> pageResult = tagConfigService.getTagPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagConfigRespVO.class));
    }

    @PostMapping("/batch-import")
    @Operation(summary = "批量导入点位配置")
    @PreAuthorize("@ss.hasPermission('opcua:tag:create')")
    public CommonResult<Boolean> batchImportTags(@Valid @RequestBody List<TagConfigSaveReqVO> list) {
        tagConfigService.batchImportTags(list);
        return success(true);
    }

}
