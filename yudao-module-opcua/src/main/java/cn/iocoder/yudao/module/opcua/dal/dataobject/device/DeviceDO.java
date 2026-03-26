package cn.iocoder.yudao.module.opcua.dal.dataobject.device;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 设备信息 DO
 *
 * @author 芋道源码
 */
@TableName("opcua_device")
@KeySequence("opcua_device_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDO extends BaseDO {

    /**
     * 设备ID
     */
    @TableId
    private Long id;

    /**
     * 设备编码
     */
    private String code;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备类型
     */
    private String type;

    /**
     * 设备位置
     */
    private String location;

    /**
     * 负责人ID
     */
    private Long responsibleUserId;

    /**
     * 负责人姓名
     */
    private String responsibleUserName;

    /**
     * OPC UA 配置ID
     */
    private Long configId;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 备注
     */
    private String remark;

}
