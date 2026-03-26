package cn.iocoder.yudao.module.opcua.service.config;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigPageReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.config.vo.OpcuaConfigSaveReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.tag.vo.OpcuaNodeVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.config.OpcuaConfigDO;
import cn.iocoder.yudao.module.opcua.dal.mysql.config.OpcuaConfigMapper;
import cn.iocoder.yudao.module.opcua.service.opcua.OpcuaClientService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.opcua.enums.ErrorCodeConstants.*;

/**
 * OPC UA 配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OpcuaConfigServiceImpl implements OpcuaConfigService {

    @Resource
    private OpcuaConfigMapper opcuaConfigMapper;

    @Resource
    private OpcuaClientService opcuaClientService;

    @Override
    public Long createConfig(OpcuaConfigSaveReqVO createReqVO) {
        // 校验名称唯一
        validateConfigNameUnique(null, createReqVO.getName());

        // 插入
        OpcuaConfigDO config = BeanUtils.toBean(createReqVO, OpcuaConfigDO.class);
        opcuaConfigMapper.insert(config);
        return config.getId();
    }

    @Override
    public void updateConfig(OpcuaConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateConfigExists(updateReqVO.getId());
        // 校验名称唯一
        validateConfigNameUnique(updateReqVO.getId(), updateReqVO.getName());

        // 更新
        OpcuaConfigDO updateObj = BeanUtils.toBean(updateReqVO, OpcuaConfigDO.class);
        opcuaConfigMapper.updateById(updateObj);

        // 断开旧连接
        opcuaClientService.disconnect(updateReqVO.getId());
    }

    @Override
    public void deleteConfig(Long id) {
        // 校验存在
        validateConfigExists(id);
        // 删除
        opcuaConfigMapper.deleteById(id);
        // 断开连接
        opcuaClientService.disconnect(id);
    }

    @Override
    public OpcuaConfigDO getConfig(Long id) {
        return opcuaConfigMapper.selectById(id);
    }

    @Override
    public PageResult<OpcuaConfigDO> getConfigPage(OpcuaConfigPageReqVO pageReqVO) {
        return opcuaConfigMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OpcuaConfigDO> getConfigList() {
        return opcuaConfigMapper.selectList();
    }

    @Override
    public boolean testConnection(Long id) {
        // 校验存在
        validateConfigExists(id);
        // 测试连接
        return opcuaClientService.testConnection(id);
    }

    @Override
    public List<OpcuaNodeVO> browseNodes(Long id, String parentNodeId) {
        // 校验存在
        validateConfigExists(id);
        // 浏览节点
        return opcuaClientService.browseNodes(id, parentNodeId);
    }

    @Override
    public List<OpcuaNodeVO> browseFolders(Long id, String parentNodeId) {
        // 校验存在
        validateConfigExists(id);
        // 浏览文件夹
        return opcuaClientService.browseFolders(id, parentNodeId);
    }

    private void validateConfigExists(Long id) {
        if (opcuaConfigMapper.selectById(id) == null) {
            throw exception(OPCUA_CONFIG_NOT_EXISTS);
        }
    }

    private void validateConfigNameUnique(Long id, String name) {
        OpcuaConfigDO config = opcuaConfigMapper.selectByName(name);
        if (config == null) {
            return;
        }
        if (id == null || !id.equals(config.getId())) {
            throw exception(OPCUA_CONFIG_NAME_DUPLICATE);
        }
    }

}
