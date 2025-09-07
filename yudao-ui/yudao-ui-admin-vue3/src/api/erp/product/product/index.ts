import request from '@/config/axios'

// 备件管理查询参数
export interface SparePartQueryParams {
  pageNo?: number
  pageSize?: number
  name?: string
  sparePartType?: number
  equipmentId?: number
  categoryId?: number
  status?: number
  hasSparePartType?: boolean
}

// ERP 产品 VO
export interface ProductVO {
  id: number // 产品编号
  name: string // 产品名称
  barCode: string // 产品条码
  categoryId: number // 产品类型编号
  unitId: number // 单位编号
  unitName?: string // 单位名字
  status: number // 产品状态
  standard: string // 产品规格
  remark: string // 产品备注
  expiryDay: number // 保质期天数
  weight: number // 重量（kg）
  purchasePrice: number // 采购价格，单位：元
  salePrice: number // 销售价格，单位：元
  minPrice: number // 最低价格，单位：元
  
  // 备件管理扩展字段
  equipmentId?: number // 关联设备ID
  equipmentName?: string // 关联设备名称
  sparePartType?: number // 备件类型
  minStock?: number // 最小库存
  maxStock?: number // 最大库存
  safetyStock?: number // 安全库存
  supplierName?: string // 供应商名称
  replacementCycle?: number // 更换周期（天）
  lastReplacementDate?: string // 上次更换日期
  nextReplacementDate?: string // 下次更换日期
  currentStock?: number // 当前库存
}

// ERP 产品 API
export const ProductApi = {
  // 查询产品分页
  getProductPage: async (params: any) => {
    return await request.get({ url: `/erp/product/page`, params })
  },

  // 查询产品精简列表
  getProductSimpleList: async () => {
    return await request.get({ url: `/erp/product/simple-list` })
  },

  // 查询产品详情
  getProduct: async (id: number) => {
    return await request.get({ url: `/erp/product/get?id=` + id })
  },

  // 新增产品
  createProduct: async (data: ProductVO) => {
    return await request.post({ url: `/erp/product/create`, data })
  },

  // 修改产品
  updateProduct: async (data: ProductVO) => {
    return await request.put({ url: `/erp/product/update`, data })
  },

  // 删除产品
  deleteProduct: async (id: number) => {
    return await request.delete({ url: `/erp/product/delete?id=` + id })
  },

  // 导出产品 Excel
  exportProduct: async (params) => {
    return await request.download({ url: `/erp/product/export-excel`, params })
  },

  // 查询备件分页（专用于备件管理）
  getSparePartPage: async (params: SparePartQueryParams) => {
    return await request.get({ url: `/erp/product/page`, params })
  },

  // 查询备件精简列表（仅包含有备件类型的产品）
  getSparePartSimpleList: async () => {
    return await request.get({ url: `/erp/product/simple-list`, params: { hasSparePartType: true } })
  }
}
