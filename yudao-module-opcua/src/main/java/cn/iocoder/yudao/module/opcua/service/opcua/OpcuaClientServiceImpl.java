package cn.iocoder.yudao.module.opcua.service.opcua;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.OpcuaNodeVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.config.OpcuaConfigDO;
import cn.iocoder.yudao.module.opcua.dal.mysql.config.OpcuaConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseDirection;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseResultMask;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseResult;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static cn.iocoder.yudao.module.opcua.enums.ErrorCodeConstants.OPCUA_CONFIG_CONNECTION_FAILED;

/**
 * OPC UA 客户端 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class OpcuaClientServiceImpl implements OpcuaClientService {

    @Resource
    private OpcuaConfigMapper opcuaConfigMapper;

    /**
     * OPC UA 客户端缓存
     * key: configId, value: OpcUaClient
     */
    private final Map<Long, OpcUaClient> clientCache = new ConcurrentHashMap<>();

    @Override
    public OpcUaClient getClient(Long configId) {
        try {
            // 先从缓存获取
            OpcUaClient client = clientCache.get(configId);
            
            // 检查客户端是否有效
            if (client != null) {
                try {
                    // 检查会话是否有效 - getSession() 返回 CompletableFuture
                    CompletableFuture<? extends org.eclipse.milo.opcua.sdk.client.api.UaSession> sessionFuture = client.getSession();
                    if (sessionFuture != null && sessionFuture.isDone() && !sessionFuture.isCompletedExceptionally()) {
                        // 会话存在且有效，直接返回
                        log.debug("[OPC UA] 使用缓存的客户端，configId: {}", configId);
                        return client;
                    }
                    // 会话无效，尝试重新连接
                    log.info("[OPC UA] 会话已失效，尝试重新连接，configId: {}", configId);
                    client.connect().get();
                    return client;
                } catch (Exception e) {
                    // 连接失败，移除缓存
                    log.warn("[OPC UA] 重新连接失败，移除缓存，configId: {}", configId);
                    clientCache.remove(configId);
                    try {
                        client.disconnect().get();
                    } catch (Exception ex) {
                        // 忽略断开连接的异常
                    }
                }
            }

            // 缓存中没有或连接已断开，创建新的客户端
            synchronized (this) {
                client = clientCache.get(configId);
                if (client != null) {
                    try {
                        CompletableFuture<? extends org.eclipse.milo.opcua.sdk          .client.api.UaSession> sessionFuture = client.getSession();
                        if (sessionFuture != null && sessionFuture.isDone() && !sessionFuture.isCompletedExceptionally()) {
                            return client;
                        }
                    } catch (Exception e) {
                        clientCache.remove(configId);
                    }
                }

                // 创建新的客户端并连接
                client = createClient(configId);
                if (client != null) {
                    try {
                        client.connect().get();
                        clientCache.put(configId, client);
                        log.info("[OPC UA] 创建并连接新客户端成功，configId: {}", configId);
                    } catch (Exception e) {
                        log.error("[OPC UA] 连接客户端失败，configId: {}", configId, e);
                        return null;
                    }
                }
                return client;
            }
        } catch (Exception e) {
            log.error("[OPC UA] 获取客户端失败，configId: {}", configId, e);
            return null;
        }
    }

    @Override
    public void connect(Long configId) {
        try {
            OpcUaClient client = getClient(configId);
            if (client != null) {
                client.connect().get();
                log.info("[OPC UA] 连接成功，configId: {}", configId);
            }
        } catch (Exception e) {
            log.error("[OPC UA] 连接失败，configId: {}", configId, e);
            throw new RuntimeException(OPCUA_CONFIG_CONNECTION_FAILED.getMsg());
        }
    }

    @Override
    public void disconnect(Long configId) {
        try {
            OpcUaClient client = clientCache.remove(configId);
            if (client != null) {
                client.disconnect().get();
                log.info("[OPC UA] 断开连接，configId: {}", configId);
            }
        } catch (Exception e) {
            log.error("[OPC UA] 断开连接失败，configId: {}", configId, e);
        }
    }

    @Override
    public Object readValue(Long configId, String nodeId) {
        try {
            OpcUaClient client = getClient(configId);
            if (client == null) {
                log.error("[OPC UA] 客户端不存在，configId: {}", configId);
                return null;
            }

            // 解析 NodeId
            NodeId node = NodeId.parse(nodeId);

            // 读取节点值
            CompletableFuture<DataValue> future = client.readValue(0, TimestampsToReturn.Both, node);
            DataValue dataValue = future.get();

            // 返回值
            Object value = dataValue.getValue().getValue();
            log.debug("[OPC UA] 读取点位值成功，nodeId: {}, value: {}", nodeId, value);
            return value;

        } catch (Exception e) {
            log.error("[OPC UA] 读取点位值失败，configId: {}, nodeId: {}", configId, nodeId, e);
            return null;
        }
    }

    @Override
    public boolean testConnection(Long configId) {
        OpcUaClient testClient = null;
        try {
            // 创建临时客户端用于测试（不使用缓存）
            testClient = createClient(configId);
            if (testClient == null) {
                return false;
            }

            // 尝试连接
            testClient.connect().get();
            
            log.info("[OPC UA] 测试连接成功，configId: {}", configId);
            return true;

        } catch (Exception e) {
            log.error("[OPC UA] 测试连接失败，configId: {}", configId, e);
            return false;
        } finally {
            // 测试完成后断开连接
            if (testClient != null) {
                try {
                    testClient.disconnect().get();
                } catch (Exception e) {
                    log.warn("[OPC UA] 断开测试连接失败", e);
                }
            }
        }
    }

    /**
     * 创建 OPC UA 客户端
     */
    private OpcUaClient createClient(Long configId) {
        try {
            // 查询配置
            OpcuaConfigDO config = opcuaConfigMapper.selectById(configId);
            if (config == null) {
                log.error("[OPC UA] 配置不存在，configId: {}", configId);
                return null;
            }

            if (!config.getEnabled()) {
                log.error("[OPC UA] 配置未启用，configId: {}", configId);
                return null;
            }

            // 创建客户端 - 使用 None 安全策略，不需要证书
            OpcUaClient client = OpcUaClient.create(
                    config.getServerUrl(),
                    endpoints -> endpoints.stream()
                            .filter(e -> e.getSecurityPolicyUri().equals("http://opcfoundation.org/UA/SecurityPolicy#None"))
                            .findFirst(),
                    configBuilder -> {
                        configBuilder
                                .setApplicationName(LocalizedText.english(config.getName()))
                                .setApplicationUri("urn:yudao:opcua:client")
                                .setRequestTimeout(UInteger.valueOf(config.getTimeout()));
                        
                        // 设置认证方式
                        if (config.getUsername() != null && !config.getUsername().isEmpty()) {
                            // 用户名密码认证
                            configBuilder.setIdentityProvider(new UsernameProvider(config.getUsername(), config.getPassword()));
                        } else {
                            // 匿名认证
                            configBuilder.setIdentityProvider(new AnonymousProvider());
                        }
                        
                        return configBuilder.build();
                    }
            );

            log.info("[OPC UA] 创建客户端成功，configId: {}, serverUrl: {}, 认证方式: {}", 
                    configId, config.getServerUrl(), 
                    config.getUsername() != null ? "用户名密码" : "匿名");
            return client;

        } catch (Exception e) {
            log.error("[OPC UA] 创建客户端失败，configId: {}", configId, e);
            return null;
        }
    }

    @Override
    public List<OpcuaNodeVO> browseNodes(Long configId, String parentNodeId) {
        List<OpcuaNodeVO> nodes = new ArrayList<>();
        
        try {
            OpcUaClient client = getClient(configId);
            if (client == null) {
                log.error("[OPC UA] 客户端不存在，configId: {}", configId);
                return nodes;
            }

            // 确保已连接
            CompletableFuture<? extends org.eclipse.milo.opcua.sdk.client.api.UaSession> sessionFuture = client.getSession();
            if (sessionFuture == null || !sessionFuture.isDone() || sessionFuture.isCompletedExceptionally()) {
                log.info("[OPC UA] 客户端未连接，开始连接，configId: {}", configId);
                client.connect().get();
            }

            // 确定起始节点
            NodeId startNode;
            if (StrUtil.isNotBlank(parentNodeId)) {
                startNode = NodeId.parse(parentNodeId);
                log.info("[OPC UA] 从指定节点开始浏览变量，parentNodeId: {}", parentNodeId);
            } else {
                // 从 Objects 节点开始浏览
                startNode = Identifiers.ObjectsFolder;
                log.info("[OPC UA] 从 Objects 节点开始浏览变量");
            }

            // 只浏览当前层级的变量节点（不递归）
            nodes = browseCurrentLevelVariables(client, startNode);

            log.info("[OPC UA] 浏览变量节点完成，configId: {}, 找到 {} 个变量节点", configId, nodes.size());

        } catch (Exception e) {
            log.error("[OPC UA] 浏览变量节点失败，configId: {}", configId, e);
        }

        return nodes;
    }

    @Override
    public List<OpcuaNodeVO> browseFolders(Long configId, String parentNodeId) {
        List<OpcuaNodeVO> folders = new ArrayList<>();
        
        try {
            OpcUaClient client = getClient(configId);
            if (client == null) {
                log.error("[OPC UA] 客户端不存在，configId: {}", configId);
                return folders;
            }

            // 确保已连接
            CompletableFuture<? extends org.eclipse.milo.opcua.sdk.client.api.UaSession> sessionFuture = client.getSession();
            if (sessionFuture == null || !sessionFuture.isDone() || sessionFuture.isCompletedExceptionally()) {
                log.info("[OPC UA] 客户端未连接，开始连接，configId: {}", configId);
                client.connect().get();
            }

            // 确定起始节点
            NodeId startNode;
            if (StrUtil.isNotBlank(parentNodeId)) {
                startNode = NodeId.parse(parentNodeId);
                log.info("[OPC UA] 从指定节点开始浏览文件夹，parentNodeId: {}", parentNodeId);
            } else {
                // 从 Objects 节点开始浏览
                startNode = Identifiers.ObjectsFolder;
                log.info("[OPC UA] 从 Objects 节点开始浏览文件夹");
            }

            // 只浏览当前层级的文件夹节点（不递归）
            folders = browseCurrentLevelFolders(client, startNode);

            log.info("[OPC UA] 浏览文件夹完成，configId: {}, 找到 {} 个文件夹", configId, folders.size());

        } catch (Exception e) {
            log.error("[OPC UA] 浏览文件夹失败，configId: {}", configId, e);
        }

        return folders;
    }

    /**
     * 浏览当前层级的变量节点（不递归）
     */
    private List<OpcuaNodeVO> browseCurrentLevelVariables(OpcUaClient client, NodeId nodeId) {
        List<OpcuaNodeVO> nodes = new ArrayList<>();
        
        try {
            // 浏览节点
            BrowseDescription browse = new BrowseDescription(
                    nodeId,
                    BrowseDirection.Forward,
                    Identifiers.References,
                    true,
                    UInteger.valueOf(NodeClass.Variable.getValue()),
                    UInteger.valueOf(BrowseResultMask.All.getValue())
            );

            BrowseResult browseResult = client.browse(browse).get();
            ReferenceDescription[] references = browseResult.getReferences();
            
            log.debug("[OPC UA] 浏览节点 {} 找到 {} 个变量", nodeId, references != null ? references.length : 0);
            
            if (references == null || references.length == 0) {
                return nodes;
            }
            
            // 处理浏览结果
            for (ReferenceDescription ref : references) {
                try {
                    NodeId childNodeId = ref.getNodeId().toNodeId(client.getNamespaceTable()).orElse(null);
                    if (childNodeId == null) {
                        continue;
                    }
                    
                    String displayName = ref.getDisplayName().getText();
                    
                    // 过滤掉报警相关的系统属性节点
                    if (isSystemProperty(displayName)) {
                        log.debug("[OPC UA] 跳过系统属性节点: {}", displayName);
                        continue;
                    }
                    
                    OpcuaNodeVO node = new OpcuaNodeVO();
                    node.setNodeId(childNodeId.toParseableString());
                    node.setDisplayName(displayName);
                    
                    try {
                        // 读取当前值
                        DataValue dataValue = client.readValue(0, TimestampsToReturn.Both, childNodeId).get();
                        if (dataValue.getValue() != null) {
                            Object value = dataValue.getValue().getValue();
                            node.setValue(value != null ? value.toString() : "");
                            node.setDataType(value != null ? value.getClass().getSimpleName() : "Unknown");
                        } else {
                            node.setValue("");
                            node.setDataType("Unknown");
                        }
                    } catch (Exception e) {
                        log.debug("[OPC UA] 读取节点值失败，nodeId: {}, error: {}", childNodeId, e.getMessage());
                        node.setValue("");
                        node.setDataType("Unknown");
                    }
                    
                    node.setReadable(true);
                    node.setWritable(false);
                    nodes.add(node);
                    
                    log.debug("[OPC UA] 添加变量节点: {}, 值: {}", node.getDisplayName(), node.getValue());
                } catch (Exception e) {
                    log.warn("[OPC UA] 处理节点失败: {}, error: {}", ref.getDisplayName().getText(), e.getMessage());
                }
            }
            
        } catch (Exception e) {
            log.error("[OPC UA] 浏览当前层级变量失败，nodeId: {}", nodeId, e);
        }
        
        return nodes;
    }

    /**
     * 浏览当前层级的文件夹节点（不递归）
     */
    private List<OpcuaNodeVO> browseCurrentLevelFolders(OpcUaClient client, NodeId nodeId) {
        List<OpcuaNodeVO> folders = new ArrayList<>();
        
        try {
            // 浏览节点
            BrowseDescription browse = new BrowseDescription(
                    nodeId,
                    BrowseDirection.Forward,
                    Identifiers.References,
                    true,
                    UInteger.valueOf(NodeClass.Object.getValue()),
                    UInteger.valueOf(BrowseResultMask.All.getValue())
            );

            BrowseResult browseResult = client.browse(browse).get();
            ReferenceDescription[] references = browseResult.getReferences();
            
            log.debug("[OPC UA] 浏览节点 {} 找到 {} 个文件夹", nodeId, references != null ? references.length : 0);
            
            if (references == null || references.length == 0) {
                return folders;
            }
            
            // 处理浏览结果
            for (ReferenceDescription ref : references) {
                try {
                    NodeId childNodeId = ref.getNodeId().toNodeId(client.getNamespaceTable()).orElse(null);
                    if (childNodeId == null) {
                        continue;
                    }
                    
                    OpcuaNodeVO folder = new OpcuaNodeVO();
                    folder.setNodeId(childNodeId.toParseableString());
                    folder.setDisplayName(ref.getDisplayName().getText());
                    folder.setDataType("Folder");
                    folder.setValue("");
                    folder.setReadable(false);
                    folder.setWritable(false);
                    folders.add(folder);
                    
                    log.debug("[OPC UA] 添加文件夹节点: {}", folder.getDisplayName());
                } catch (Exception e) {
                    log.warn("[OPC UA] 处理文件夹失败: {}, error: {}", ref.getDisplayName().getText(), e.getMessage());
                }
            }
            
        } catch (Exception e) {
            log.error("[OPC UA] 浏览当前层级文件夹失败，nodeId: {}", nodeId, e);
        }
        
        return folders;
    }

    /**
     * 判断是否为系统属性节点（需要过滤）
     */
    private boolean isSystemProperty(String displayName) {
        // OPC UA 报警和事件相关的系统属性
        String[] systemProperties = {
            "EventId", "EventType", "SourceNode", "SourceName", "Time", "ReceiveTime",
            "LocalTime", "Message", "Severity", "ConditionClassId", "ConditionClassName",
            "ConditionName", "BranchId", "Retain", "EnabledState", "Quality", "LastSeverity",
            "Comment", "ClientUserId", "AddComment", "ConditionRefresh", "AckedState",
            "ConfirmedState", "Acknowledge", "Confirm", "ActiveState", "InputNode",
            "SuppressedState", "OutOfServiceState", "ShelvingState", "SuppressedOrShelved",
            "MaxTimeShelved", "LowLimit", "HighLimit", "LowLowLimit", "HighHighLimit",
            "LimitState", "Deadband", "BaseHighHighLimit", "BaseHighLimit", "BaseLowLimit",
            "BaseLowLowLimit", "ConditionSubClassId", "ConditionSubClassName"
        };
        
        for (String prop : systemProperties) {
            if (displayName.equals(prop)) {
                return true;
            }
        }
        
        return false;
    }

}
