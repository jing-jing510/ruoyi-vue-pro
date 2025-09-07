package cn.iocoder.yudao.module.coal.dal.dataobject.shiftsystem;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.time.LocalTime;

/**
 * 班制与班次设置 (树表) DO
 *
 * @author 芋道源码
 */
@TableName("coal_shift_system")
@KeySequence("coal_shift_system_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftSystemDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 父ID
     */
    private Long parentId;
    /**
     * 名称 
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 是否生产班制
     *
     * 枚举 {@link TODO is_production 对应的类}
     */
    private Integer isProduction;
    /**
     * 开始时间 
     */
    private LocalTime startTime;
    /**
     * 结束时间 
     */
    private LocalTime endTime;
    /**
     * 班次类型
     *
     * 枚举 {@link TODO shift_type 对应的类}
     */
    private Integer shiftType;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态：0禁用 1启用
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Integer status;


}
