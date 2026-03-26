import request from '@/config/axios'

export interface SparePartEquipmentVO {
  id: number
  sparePartId: number
  equipmentId: number
  usageCount: number
  installPosition: string
  isRequired: number
  replacementDifficulty: number
  replacementTime: number
  toolsRequired: string
  safetyRequirements: string
  remark: string
  createTime: Date
  // 扩展字段
  sparePartName?: string
  equipmentName?: string
}

// 查询备件设备关联分页
export const getSparePartEquipmentPage = async (params: any) => {
  return await request.get({ url: `/coal/spare-part-equipment/page`, params })
}

// 查询备件设备关联详情
export const getSparePartEquipment = async (id: number) => {
  return await request.get({ url: `/coal/spare-part-equipment/get?id=` + id })
}

// 新增备件设备关联
export const createSparePartEquipment = async (data: SparePartEquipmentVO) => {
  return await request.post({ url: `/coal/spare-part-equipment/create`, data })
}

// 修改备件设备关联
export const updateSparePartEquipment = async (data: SparePartEquipmentVO) => {
  return await request.put({ url: `/coal/spare-part-equipment/update`, data })
}

// 删除备件设备关联
export const deleteSparePartEquipment = async (id: number) => {
  return await request.delete({ url: `/coal/spare-part-equipment/delete?id=` + id })
}

// 导出备件设备关联 Excel
export const exportSparePartEquipment = async (params) => {
  return await request.download({ url: `/coal/spare-part-equipment/export-excel`, params })
}

// 根据备件ID获取关联的设备列表
export const getSparePartEquipmentListBySparePartId = async (sparePartId: number) => {
  return await request.get({ url: `/coal/spare-part-equipment/list-by-spare-part?sparePartId=` + sparePartId })
}

// 根据设备ID获取关联的备件列表
export const getSparePartEquipmentListByEquipmentId = async (equipmentId: number) => {
  return await request.get({ url: `/coal/spare-part-equipment/list-by-equipment?equipmentId=` + equipmentId })
}

// ========== 扩展API：用于下拉选择 ==========

// 获取设备简单列表
export const getSimpleEquipmentList = async (): Promise<any[]> => {
  return await request.get({ url: `/coal/equipment-info/simple-list` })
}

// 获取备件简单列表
export const getSimpleSparePartList = async (): Promise<any[]> => {
  return await request.get({ url: `/coal/spare-part-info/simple-list` })
}

// 获取备件分类树
export const getSparePartCategoryTree = async (): Promise<any[]> => {
  return await request.get({ url: `/coal/spare-part-category/tree` })
}
