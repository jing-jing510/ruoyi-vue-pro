package cn.iocoder.yudao.module.opcua.dal.dataobject.config;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * OPC UA 配置 DO
 *
 * @author 芋道源码
 */
@TableName("opcua_config")
@KeySequence("opcua_config_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpcuaConfigDO extends BaseDO {

    /**
     * 配置ID
     */
    @TableId
    private Long id;

    /**
     * 配置名称
     */
    private String name;

    /**
     * OPC UA 服务器地址
     * 例如：opc.tcp://localhost:4840
     */
    private String serverUrl;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 安全策略
     * None, Basic128Rsa15, Basic256, Basic256Sha256
     */
    private String securityPolicy;

    /**
     * 安全模式
     * None, Sign, SignAndEncrypt
     */
    private String securityMode;

    /**
     * 连接超时时间（毫秒）
     */
    private Integer timeout;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 备注
     */
    private String remark;

}
