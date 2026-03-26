package cn.iocoder.yudao.module.coal.dal.dataobject.marketanalysis;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

/**
 * 市场分析 DO
 *
 * @author 京京
 */
@TableName("coal_market_analysis")
@KeySequence("coal_market_analysis_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketAnalysisDO extends BaseDO {

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
     * 市场分析文件URL（单文件）
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
