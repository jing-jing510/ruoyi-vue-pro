package cn.iocoder.yudao.module.coal.dal.dataobject.safetyattachment;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 安全附件 DO
 *
 * @author 芋道源码
 */
@TableName("coal_safety_attachment")
@KeySequence("coal_safety_attachment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SafetyAttachmentDO extends BaseDO {

    /**
     * 附件ID
     */
    @TableId
    private Long id;
    /**
     * 业务类型
     */
    private Integer businessType;
    /**
     * 业务ID
     */
    private Long businessId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小(字节)
     */
    private Long fileSize;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件扩展名
     */
    private String fileExtension;
    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;
    /**
     * 上传人ID
     */
    private Long uploaderId;
    /**
     * 上传人姓名
     */
    private String uploaderName;
    /**
     * 备注
     */
    private String remark;


}
