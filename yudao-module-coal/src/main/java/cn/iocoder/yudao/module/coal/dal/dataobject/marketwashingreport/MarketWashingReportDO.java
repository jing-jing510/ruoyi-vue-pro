package cn.iocoder.yudao.module.coal.dal.dataobject.marketwashingreport;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

/**
 * 洗选分析报告 DO
 *
 * @author jingjing
 */
@TableName("coal_market_washing_report")
@KeySequence("coal_market_washing_report_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketWashingReportDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 报告日期
     */
    private LocalDate reportDate;
    /**
     * 洗选报告文件URL（单文件）
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
