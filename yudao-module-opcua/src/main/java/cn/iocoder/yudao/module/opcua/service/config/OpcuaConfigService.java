package cn.iocoder.yudao.module.opcua.service.config;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigPageReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigSaveReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.OpcuaNodeVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.config.OpcuaConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * OPC UA 配置 Service 接口
 *
 * @author 芋道源码
 */
public interface OpcuaConfigService {

    /**
     * 创建 OPC UA 配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createConfig(@Valid OpcuaConfigSaveReqVO createReqVO);

    /**
     * 更新 OPC UA 配置
     *
     * @param updateReqVO 更新信息
     */
    void updateConfig(@Valid OpcuaConfigSaveReqVO updateReqVO);

    /**
     * 删除 OPC UA 配置
     *
     * @param id 编号
     */
    void deleteConfig(Long id);

    /**
     * 获得 OPC UA 配置
     *
     * @param id 编号
     * @return OPC UA 配置
     */
    OpcuaConfigDO getConfig(Long id);

    /**
     * 获得 OPC UA 配置分页
     *
     * @param pageReqVO 分页查询
     * @return OPC UA 配置分页
     */
    PageResult<OpcuaConfigDO> getConfigPage(OpcuaConfigPageReqVO pageReqVO);

    /**
     * 获得 OPC UA 配置列表
     *
     * @return OPC UA 配置列表
     */
    List<OpcuaConfigDO> getConfigList();

    /**
     * 测试连接
     *
     * @param id 编号
     * @return 是否连接成功
     */
    boolean testConnection(Long id);

    /**
     * 浏览 OPC UA 节点
     *
     * @param id 配置ID
     * @param parentNodeId 父节点ID
     * @return 节点列表
     */
    List<OpcuaNodeVO> browseNodes(Long id, String parentNodeId);

    /**
     * 浏览 OPC UA 文件夹
     *
     * @param id 配置ID
     * @param parentNodeId 父节点ID
     * @return 文件夹列表
     */
    List<OpcuaNodeVO> browseFolders(Long id, String parentNodeId);

}
