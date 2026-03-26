package cn.iocoder.yudao.module.opcua.service.opcua;

import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.OpcuaNodeVO;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.List;

/**
 * OPC UA 客户端 Service 接口
 *
 * @author 芋道源码
 */
public interface OpcuaClientService {

    /**
     * 获取 OPC UA 客户端
     *
     * @param configId 配置ID
     * @return OPC UA 客户端
     */
    OpcUaClient getClient(Long configId);

    /**
     * 连接 OPC UA 服务器
     *
     * @param configId 配置ID
     */
    void connect(Long configId);

    /**
     * 断开 OPC UA 服务器连接
     *
     * @param configId 配置ID
     */
    void disconnect(Long configId);

    /**
     * 读取点位值
     *
     * @param configId 配置ID
     * @param nodeId   节点ID
     * @return 点位值
     */
    Object readValue(Long configId, String nodeId);

    /**
     * 测试连接
     *
     * @param configId 配置ID
     * @return 是否连接成功
     */
    boolean testConnection(Long configId);

    /**
     * 浏览 OPC UA 节点（获取所有变量）
     *
     * @param configId 配置ID
     * @param parentNodeId 父节点ID，为空则从根节点开始
     * @return 节点列表
     */
    List<OpcuaNodeVO> browseNodes(Long configId, String parentNodeId);

    /**
     * 浏览 OPC UA 文件夹（只获取文件夹，不递归）
     *
     * @param configId 配置ID
     * @param parentNodeId 父节点ID，为空则从根节点开始
     * @return 文件夹列表
     */
    List<OpcuaNodeVO> browseFolders(Long configId, String parentNodeId);

}
