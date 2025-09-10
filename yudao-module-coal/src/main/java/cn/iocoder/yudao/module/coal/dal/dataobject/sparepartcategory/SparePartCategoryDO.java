package cn.iocoder.yudao.module.coal.dal.dataobject.sparepartcategory;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 备件分类表 (树表) DO
 *
 * @author 京京
 */
@TableName("coal_spare_part_category")
@KeySequence("coal_spare_part_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartCategoryDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * 分类ID
     */
    @TableId
    private Long id;
    /**
     * 父分类ID (0=根分类)
     */
    private Long parentId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 分类编码
     */
    private String categoryCode;
    /**
     * 分类层级：1大类 2中类 3小类
     */
    private Integer categoryLevel;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态：0禁用 1启用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;


}
