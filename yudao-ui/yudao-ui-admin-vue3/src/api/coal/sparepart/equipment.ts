import request from '@/config/axios'

// 备件设备关联 VO
export interface SparePartEquipmentVO {
  id?: number // 关联ID
  sparePartId: number // 备件ID
  sparePartName?: string // 备件名称
  equipmentId: number // 设备ID
  equipmentName?: string // 设备名称
  isRequired: number // 是否必需 1-是 0-否
  quantity: number // 用量
  unit: string // 单位
  installPosition?: string // 安装位置
  replacementDifficulty?: string // 更换难度
  remark?: string // 备注
  createTime?: Date // 创建时间
}

// 备件设备关联查询参数
export interface SparePartEquipmentPageReqVO {
  pageNo: number
  pageSize: number
  sparePartId?: number
  equipmentId?: number
  isRequired?: number
}

// 备件设备关联 API
export const SparePartEquipmentApi = {
  // 查询备件设备关联分页
  getPage: async (params: SparePartEquipmentPageReqVO) => {
    return await request.get({ url: `/coal/spare-part-equipment/page`, params })
  },

  // 查询备件设备关联详情
  get: async (id: number) => {
    return await request.get({ url: `/coal/spare-part-equipment/get?id=` + id })
  },

  // 新增备件设备关联
  create: async (data: SparePartEquipmentVO) => {
    return await request.post({ url: `/coal/spare-part-equipment/create`, data })
  },

  // 修改备件设备关联
  update: async (data: SparePartEquipmentVO) => {
    return await request.put({ url: `/coal/spare-part-equipment/update`, data })
  },

  // 删除备件设备关联
  delete: async (id: number) => {
    return await request.delete({ url: `/coal/spare-part-equipment/delete?id=` + id })
  },

  // 根据备件ID查询关联的设备列表
  getEquipmentsBySparePartId: async (sparePartId: number) => {
    return await request.get({ url: `/coal/spare-part-equipment/list-by-spare-part?sparePartId=` + sparePartId })
  },

  // 根据设备ID查询关联的备件列表
  getSparePartsByEquipmentId: async (equipmentId: number) => {
    return await request.get({ url: `/coal/spare-part-equipment/list-by-equipment?equipmentId=` + equipmentId })
  },

  // 批量创建备件设备关联
  createBatch: async (data: SparePartEquipmentVO[]) => {
    return await request.post({ url: `/coal/spare-part-equipment/create-batch`, data })
  },

  // 导出备件设备关联 Excel
  export: async (params: SparePartEquipmentPageReqVO) => {
    return await request.download({ url: `/coal/spare-part-equipment/export-excel`, params })
  }
}
