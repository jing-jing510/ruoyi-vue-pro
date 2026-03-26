import request from '@/config/axios'

/** 安全检查分类信息 */
export interface SafetyCheckCategory {
  id: number
  parentId: number
  categoryName: string
  categoryCode: string
  categoryType: number
  sort: number
  status: number
  remark: string
  creator: string
  createTime: Date
  updater: string
  updateTime: Date
  deleted: boolean
  tenantId: number
}

// 安全检查分类 API
export const SafetyCheckCategoryApi = {
  // 查询安全检查分类分页
  getSafetyCheckCategoryPage: async (params: any) => {
    return await request.get({ url: `/coal/safety-check-category/page`, params })
  },

  // 查询安全检查分类详情
  getSafetyCheckCategory: async (id: number) => {
    return await request.get({ url: `/coal/safety-check-category/get?id=` + id })
  },

  // 新增安全检查分类
  createSafetyCheckCategory: async (data: SafetyCheckCategory) => {
    return await request.post({ url: `/coal/safety-check-category/create`, data })
  },

  // 修改安全检查分类
  updateSafetyCheckCategory: async (data: SafetyCheckCategory) => {
    return await request.put({ url: `/coal/safety-check-category/update`, data })
  },

  // 删除安全检查分类
  deleteSafetyCheckCategory: async (id: number) => {
    return await request.delete({ url: `/coal/safety-check-category/delete?id=` + id })
  },

  /** 批量删除安全检查分类 */
  deleteSafetyCheckCategoryList: async (ids: number[]) => {
    return await request.delete({ url: `/coal/safety-check-category/delete-list?ids=${ids.join(',')}` })
  },

  // 导出安全检查分类 Excel
  exportSafetyCheckCategory: async (params) => {
    return await request.download({ url: `/coal/safety-check-category/export-excel`, params })
  },

  // 获得安全检查分类精简信息列表
  getSimpleSafetyCheckCategoryList: async () => {
    return await request.get({ url: `/coal/safety-check-category/simple-list` })
  },

  // 获得安全检查分类列表
  getSafetyCheckCategoryList: async () => {
    return await request.get({ url: `/coal/safety-check-category/list` })
  }
}