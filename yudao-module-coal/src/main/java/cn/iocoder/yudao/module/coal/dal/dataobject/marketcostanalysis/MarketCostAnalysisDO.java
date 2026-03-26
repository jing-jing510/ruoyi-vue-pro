package cn.iocoder.yudao.module.coal.dal.dataobject.marketcostanalysis;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

/**
 * 综合成本核算 DO
 *
 * @author jingjing
 */
@TableName("coal_market_cost_analysis")
@KeySequence("coal_market_cost_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCostAnalysisDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 分析日期
     */
    private LocalDate analysisDate;
    /**
     * 成本核算文件URL（单文件）
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
