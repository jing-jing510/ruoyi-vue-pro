import request from '@/config/axios'

export interface TagConfigVO {
  id?: number
  configId: number
  deviceName?: string
  name: string
  nodeId: string
  dataType: string
  isAlarm: boolean
  alarmLevel?: number
  alarmContent?: string
  lastValue?: string
  enabled: boolean
  remark?: string
  createTime?: Date
}

// 查询点位配置分页
export const getTagPage = (params: any) => {
  return request.get({ url: '/opcua/tag/page', params })
}

// 查询点位配置详情
export const getTag = (id: number) => {
  return request.get({ url: '/opcua/tag/get?id=' + id })
}

// 新增点位配置
export const createTag = (data: TagConfigVO) => {
  return request.post({ url: '/opcua/tag/create', data })
}

// 修改点位配置
export const updateTag = (data: TagConfigVO) => {
  return request.put({ url: '/opcua/tag/update', data })
}

// 删除点位配置
export const deleteTag = (id: number) => {
  return request.delete({ url: '/opcua/tag/delete?id=' + id })
}

// 批量导入点位
export const batchImportTags = (data: TagConfigVO[]) => {
  return request.post({ url: '/opcua/tag/batch-import', data })
}
