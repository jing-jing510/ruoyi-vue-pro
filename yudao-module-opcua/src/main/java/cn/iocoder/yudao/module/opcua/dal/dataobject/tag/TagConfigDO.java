package cn.iocoder.yudao.module.opcua.dal.dataobject.tag;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 点位配置 DO
 *
 * @author 芋道源码
 */
@TableName("opcua_tag_config")
@KeySequence("opcua_tag_config_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagConfigDO extends BaseDO {

    /**
     * 点位ID
     */
    @TableId
    private Long id;

    /**
     * OPC UA 配置ID
     */
    private Long configId;

    /**
     * 设备名称（手动填写）
     */
    private String deviceName;

    /**
     * 点位名称
     */
    private String name;

    /**
     * OPC UA NodeId
     */
    private String nodeId;

    /**
     * 数据类型
     * Boolean, Int16, Int32, Int64, Float, Double, String
     */
    private String dataType;

    /**
     * 是否为报警点位
     */
    private Boolean isAlarm;

    /**
     * 报警级别
     * 1-提示 2-警告 3-错误 4-严重
     */
    private Integer alarmLevel;

    /**
     * 报警内容
     */
    private String alarmContent;

    /**
     * 上次采集值（用于判断0→1）
     * 注意：last_value 是 MySQL 8.0+ 的保留关键字（窗口函数），需要用反引号转义
     */
    @TableField("`last_value`")
    private String lastValue;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 备注
     */
    private String remark;

}
