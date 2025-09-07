import request from '@/config/axios'

// 备件预警记录 VO
export interface SparePartAlertVO {
  id?: number // 预警ID
  sparePartId: number // 备件ID
  sparePartName?: string // 备件名称
  warehouseId: number // 仓库ID
  warehouseName?: string // 仓库名称
  alertType: number // 预警类型 1-库存不足 2-到期更换 3-超期未更换
  currentStock: number // 当前库存
  thresholdValue: number // 阈值
  alertLevel: number // 预警级别 1-低 2-中 3-高
  alertStatus: number // 预警状态 1-待处理 2-处理中 3-已处理 4-已忽略
  alertMessage: string // 预警信息
  handlerId?: number // 处理人ID
  handlerName?: string // 处理人名称
  handleTime?: Date // 处理时间
  handleRemark?: string // 处理备注
  createTime?: Date // 创建时间
}

// 备件预警记录查询参数
export interface SparePartAlertPageReqVO {
  pageNo: number
  pageSize: number
  sparePartId?: number
  warehouseId?: number
  alertType?: number
  alertLevel?: number
  alertStatus?: number
  createTime?: Date[]
}

// 预警处理请求参数
export interface AlertHandleReqVO {
  alertId: number
  handlerId: number
  handleRemark: string
}

// 备件预警记录 API
export const SparePartAlertApi = {
  // 查询备件预警记录分页
  getPage: async (params: SparePartAlertPageReqVO) => {
    return await request.get({ url: `/coal/spare-part-alert/page`, params })
  },

  // 查询备件预警记录详情
  get: async (id: number) => {
    return await request.get({ url: `/coal/spare-part-alert/get?id=` + id })
  },

  // 新增备件预警记录
  create: async (data: SparePartAlertVO) => {
    return await request.post({ url: `/coal/spare-part-alert/create`, data })
  },

  // 删除备件预警记录
  delete: async (id: number) => {
    return await request.delete({ url: `/coal/spare-part-alert/delete?id=` + id })
  },

  // 处理预警
  handle: async (alertId: number, handlerId: number, handleRemark: string) => {
    return await request.put({ 
      url: `/coal/spare-part-alert/handle`, 
      params: { alertId, handlerId, handleRemark }
    })
  },

  // 忽略预警
  ignore: async (alertId: number, handlerId: number, handleRemark: string) => {
    return await request.put({ 
      url: `/coal/spare-part-alert/ignore`, 
      params: { alertId, handlerId, handleRemark }
    })
  },

  // 获取待处理预警列表
  getPendingAlerts: async () => {
    return await request.get({ url: `/coal/spare-part-alert/pending` })
  },

  // 批量处理预警
  handleBatch: async (alertIds: number[], handlerId: number, handleRemark: string) => {
    return await request.put({ 
      url: `/coal/spare-part-alert/handle-batch`, 
      data: { alertIds, handlerId, handleRemark }
    })
  },

  // 根据备件ID查询预警记录
  getAlertsBySparePartId: async (sparePartId: number) => {
    return await request.get({ url: `/coal/spare-part-alert/list-by-spare-part?sparePartId=` + sparePartId })
  },

  // 根据仓库ID查询预警记录
  getAlertsByWarehouseId: async (warehouseId: number) => {
    return await request.get({ url: `/coal/spare-part-alert/list-by-warehouse?warehouseId=` + warehouseId })
  },

  // 获取预警统计信息
  getAlertStatistics: async () => {
    return await request.get({ url: `/coal/spare-part-alert/statistics` })
  },

  // 导出预警记录 Excel
  export: async (params: SparePartAlertPageReqVO) => {
    return await request.download({ url: `/coal/spare-part-alert/export-excel`, params })
  }
}
