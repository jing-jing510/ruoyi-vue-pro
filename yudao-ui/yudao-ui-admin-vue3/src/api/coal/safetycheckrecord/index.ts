import request from '@/config/axios'

/** 安全检查记录信息 */
export interface SafetyCheckRecord {
  id: number
  recordCode: string
  recordName: string
  categoryId: number
  categoryName: string
  checkType: number
  checkTime: Date
  checkerId: number
  checkerName: string
  totalItems: number
  qualifiedItems: number
  unqualifiedItems: number
  checkResult: number
  riskLevel: number
  checkDescription: string
  auditorId: number
  auditorName: string
  auditTime: Date
  auditRemark: string
  recordStatus: number
  remark: string
  creator: string
  createTime: Date
  updater: string
  updateTime: Date
  deleted: boolean
  tenantId: number
}

/** 安全检查项目信息 */
export interface SafetyCheckItem {
  id: number
  recordId: number
  itemName: string
  itemDescription: string
  checkResult: number
  checkRemark: string
  rectificationPersonId: number
  rectificationPersonName: string
  rectificationDeadline: Date
  rectificationStatus: number
  rectificationDescription: string
  verifierId: number
  verifierName: string
  verificationTime: Date
  verificationResult: number
  verificationRemark: string
  remark: string
  creator: string
  createTime: Date
  updater: string
  updateTime: Date
  deleted: boolean
  tenantId: number
}

// 安全检查记录 API
export const SafetyCheckRecordApi = {
  // 查询安全检查记录分页
  getSafetyCheckRecordPage: async (params: any) => {
    return await request.get({ url: `/coal/safety-check-record/page`, params })
  },

  // 查询安全检查记录详情
  getSafetyCheckRecord: async (id: number) => {
    return await request.get({ url: `/coal/safety-check-record/get?id=` + id })
  },

  // 新增安全检查记录
  createSafetyCheckRecord: async (data: SafetyCheckRecord) => {
    return await request.post({ url: `/coal/safety-check-record/create`, data })
  },

  // 修改安全检查记录
  updateSafetyCheckRecord: async (data: SafetyCheckRecord) => {
    return await request.put({ url: `/coal/safety-check-record/update`, data })
  },

  // 删除安全检查记录
  deleteSafetyCheckRecord: async (id: number) => {
    return await request.delete({ url: `/coal/safety-check-record/delete?id=` + id })
  },

  /** 批量删除安全检查记录 */
  deleteSafetyCheckRecordList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/safety-check-record/delete-list?ids=${ids.join(',')}` })
  },

  // 导出安全检查记录 Excel
  exportSafetyCheckRecord: async (params) => {
    return await request.download({ url: `/coal/safety-check-record/export-excel`, params })
  },

  // 获取安全检查记录统计数据
  getSafetyCheckRecordStatistics: async () => {
    return await request.get({ url: `/coal/safety-check-record/statistics` })
  },

  // 获取安全检查项目统计数据
  getSafetyCheckItemStatistics: async () => {
    return await request.get({ url: `/coal/safety-check-record/safety-check-item/statistics` })
  }
}

// 安全检查项目 API
export const SafetyCheckItemApi = {
  // 查询安全检查项目分页
  getSafetyCheckItemPage: async (params: any) => {
    return await request.get({ url: `/coal/safety-check-record/safety-check-item/page`, params })
  },

  // 查询安全检查项目详情
  getSafetyCheckItem: async (id: number) => {
    return await request.get({ url: `/coal/safety-check-record/safety-check-item/get?id=` + id })
  },

  // 新增安全检查项目
  createSafetyCheckItem: async (data: SafetyCheckItem) => {
    return await request.post({ url: `/coal/safety-check-record/safety-check-item/create`, data })
  },

  // 修改安全检查项目
  updateSafetyCheckItem: async (data: SafetyCheckItem) => {
    return await request.put({ url: `/coal/safety-check-record/safety-check-item/update`, data })
  },

  // 删除安全检查项目
  deleteSafetyCheckItem: async (id: number) => {
    return await request.delete({ url: `/coal/safety-check-record/safety-check-item/delete?id=` + id })
  },

  /** 批量删除安全检查项目 */
  deleteSafetyCheckItemList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/safety-check-record/safety-check-item/delete-list?ids=${ids.join(',')}` })
  },

  // 根据记录ID查询检查项目列表
  getSafetyCheckItemListByRecordId: async (recordId: number) => {
    return await request.get({ url: `/coal/safety-check-record/safety-check-item/list-by-record-id?recordId=` + recordId })
  }
}