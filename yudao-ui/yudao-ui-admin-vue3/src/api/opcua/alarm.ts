import request from '@/config/axios'

export interface AlarmEventVO {
  id?: number
  configId: number
  deviceName?: string
  tagId: number
  tagName: string
  nodeId: string
  alarmLevel: number
  alarmContent: string
  alarmTime: Date
  status: number
  handleUserId?: number
  handleUserName?: string
  handleTime?: Date
  handleRemark?: string
  createTime?: Date
}

export interface AlarmStatisticsVO {
  totalCount: number
  statusStatistics: Array<{ status: number; count: number }>
  levelStatistics: Array<{ alarmLevel: number; count: number }>
  deviceStatistics: Array<{ deviceName: string; count: number }>
}

// 查询报警事件分页
export const getAlarmEventPage = (params: any) => {
  return request.get({ url: '/opcua/alarm-event/page', params })
}

// 查询报警事件详情
export const getAlarmEvent = (id: number) => {
  return request.get({ url: '/opcua/alarm-event/get?id=' + id })
}

// 新增报警事件
export const createAlarmEvent = (data: AlarmEventVO) => {
  return request.post({ url: '/opcua/alarm-event/create', data })
}

// 修改报警事件
export const updateAlarmEvent = (data: AlarmEventVO) => {
  return request.put({ url: '/opcua/alarm-event/update', data })
}

// 处理报警事件
export const handleAlarmEvent = (data: any) => {
  return request.put({ url: '/opcua/alarm-event/handle', data })
}

// 删除报警事件
export const deleteAlarmEvent = (id: number) => {
  return request.delete({ url: '/opcua/alarm-event/delete?id=' + id })
}

// 获取报警统计
export const getAlarmStatistics = (params: any) => {
  return request.get({ url: '/opcua/alarm-event/statistics', params })
}
