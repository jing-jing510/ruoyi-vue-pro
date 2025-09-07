package cn.iocoder.yudao.module.erp.controller.admin.product.vo.product;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - ERP 产品分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErpProductPageReqVO extends PageParam {

    @Schema(description = "产品名称", example = "李四")
    private String name;

    @Schema(description = "产品分类编号", example = "11161")
    private Long categoryId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    // ========== 备件管理扩展字段 ==========
    
    @Schema(description = "备件类型", example = "1")
    private Integer sparePartType;

    @Schema(description = "关联设备ID", example = "1")
    private Long equipmentId;

    @Schema(description = "是否只查询有备件类型的产品", example = "true")
    private Boolean hasSparePartType;

}