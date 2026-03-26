import request from '@/config/axios'

/** 安全附件信息 */
export interface SafetyAttachment {
  id: number
  businessType: number
  businessId: number
  filePath: string
  uploadTime: Date
  uploaderId: number
  uploaderName: string
  remark: string
}

// 安全附件 API
export const SafetyAttachmentApi = {
  // 查询安全附件分页
  getSafetyAttachmentPage: async (params: any) => {
    return await request.get({ url: `/coal/safety-attachment/page`, params })
  },

  // 查询安全附件详情
  getSafetyAttachment: async (id: number) => {
    return await request.get({ url: `/coal/safety-attachment/get?id=` + id })
  },

  // 新增安全附件
  createSafetyAttachment: async (data: SafetyAttachment) => {
    return await request.post({ url: `/coal/safety-attachment/create`, data })
  },

  // 修改安全附件
  updateSafetyAttachment: async (data: SafetyAttachment) => {
    return await request.put({ url: `/coal/safety-attachment/update`, data })
  },

  // 删除安全附件
  deleteSafetyAttachment: async (id: number) => {
    return await request.delete({ url: `/coal/safety-attachment/delete?id=` + id })
  },

  /** 批量删除安全附件 */
  deleteSafetyAttachmentList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/safety-attachment/delete-list?ids=${ids.join(',')}` })
  },

  // 导出安全附件 Excel
  exportSafetyAttachment: async (params) => {
    return await request.download({ url: `/coal/safety-attachment/export-excel`, params })
  },

  // 根据业务类型和业务ID查询附件列表
  getSafetyAttachmentListByBusiness: async (businessType: number, businessId: number) => {
    return await request.get({ url: `/coal/safety-attachment/list-by-business?businessType=${businessType}&businessId=${businessId}` })
  },

  // 获取安全附件统计数据
  getSafetyAttachmentStatistics: async () => {
    return await request.get({ url: `/coal/safety-attachment/statistics` })
  }
}