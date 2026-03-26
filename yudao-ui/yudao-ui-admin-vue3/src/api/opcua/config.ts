import request from '@/config/axios'

export interface OpcuaConfigVO {
  id?: number
  name: string
  serverUrl: string
  username?: string
  password?: string
  securityPolicy: string
  securityMode: string
  timeout: number
  enabled: boolean
  remark?: string
  createTime?: Date
}

export interface OpcuaNodeVO {
  nodeId: string
  displayName: string
  dataType: string
  value?: any
}

// 查询 OPC UA 配置分页
export const getConfigPage = (params: any) => {
  return request.get({ url: '/opcua/config/page', params })
}

// 查询 OPC UA 配置列表（不分页）
export const getOpcuaConfigList = () => {
  return request.get({ url: '/opcua/config/list' })
}

// 查询 OPC UA 配置详情
export const getConfig = (id: number) => {
  return request.get({ url: '/opcua/config/get?id=' + id })
}

// 新增 OPC UA 配置
export const createConfig = (data: OpcuaConfigVO) => {
  return request.post({ url: '/opcua/config/create', data })
}

// 修改 OPC UA 配置
export const updateConfig = (data: OpcuaConfigVO) => {
  return request.put({ url: '/opcua/config/update', data })
}

// 删除 OPC UA 配置
export const deleteConfig = (id: number) => {
  return request.delete({ url: '/opcua/config/delete?id=' + id })
}

// 测试连接
export const testConnection = (id: number) => {
  return request.get({ url: '/opcua/config/test-connection?id=' + id })
}

// 浏览 OPC UA 节点
export const browseNodes = (id: number, parentNodeId?: string) => {
  return request.get({ url: '/opcua/config/browse-nodes', params: { id, parentNodeId } })
}

// 浏览 OPC UA 文件夹
export const browseFolders = (id: number, parentNodeId?: string) => {
  return request.get({ url: '/opcua/config/browse-folders', params: { id, parentNodeId } })
}
