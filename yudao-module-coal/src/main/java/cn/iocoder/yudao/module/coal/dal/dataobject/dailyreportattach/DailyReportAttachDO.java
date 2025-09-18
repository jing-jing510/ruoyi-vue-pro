package cn.iocoder.yudao.module.coal.dal.dataobject.dailyreportattach;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

/**
 * 生产日报附件上传 DO
 *
 * @author 京京
 */
@TableName("coal_daily_report_attach")
@KeySequence("coal_daily_report_attach_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportAttachDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 日报日期
     */
    private LocalDate reportDate;
    /**
     * 日报文件URL（单文件）
     */
    private String reportFileUrl;
    /**
     * 图片URL，多个用英文逗号分隔
     */
    private String imageUrls;
    /**
     * 备注
     */
    private String remark;


}
